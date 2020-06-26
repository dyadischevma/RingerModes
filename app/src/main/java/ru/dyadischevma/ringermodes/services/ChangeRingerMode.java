package ru.dyadischevma.ringermodes.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.RingerMode;
import ru.dyadischevma.ringermodes.model.RingerModeRepository;
import ru.dyadischevma.ringermodes.model.RingerModeTimeCondition;

public class ChangeRingerMode extends JobIntentService {
    private static final int JOB_ID = 649;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public static final String CUSTOM_INTENT = "ru.dyadischevma.intent.action.ALARM";


    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, ChangeRingerMode.class, JOB_ID, intent);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName) {
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public IBinder onBind(Intent intent) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, createNotificationChannel("my_service", "My Background Service"))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Title")
                        .setContentText("Notification text");

        Notification notification = builder.build();

        startForeground(1, notification);

        RingerMode ringerMode = RingerMode.SILENT;
        int volume = -1;

        SharedPreferences sharedPreferences = getSharedPreferences("RINGER_MODES", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("RINGER_MODE")) {
            ringerMode = RingerMode.fromInt(sharedPreferences.getInt("RINGER_MODE", 0));
        }
        if (sharedPreferences.contains("RINGER_MODE_VOLUME")) {
            volume = sharedPreferences.getInt("RINGER_MODE_VOLUME", -1);
        }

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null && ringerMode != null) {
            audioManager.setRingerMode(ringerMode.value);
            if (ringerMode.equals(RingerMode.NORMAL) && volume > -1) {
                audioManager.setStreamVolume(AudioManager.STREAM_RING, volume, 0);
            }
            Log.d("RINGER_MODES", "Set mode " + ringerMode);
        }

        return super.onBind(intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        RingerModeRepository ringerModeRepository = new RingerModeRepository(getApplication());
        Disposable disposable = ringerModeRepository.getAllTimeConditions()
                .subscribe(s -> {
                    RingerModeTimeCondition ringerModeTimeCondition = getNearestCondition(currentHour, currentMinute, currentDay, s);
                    if (ringerModeTimeCondition != null) {
                        Disposable ringerModeDisposable = ringerModeRepository.getRingerMode(ringerModeTimeCondition.getRingerModeId()).subscribe(ringerMode -> {
                            Log.d("RINGER_MODES_SET_ALARM", ringerMode.toString());

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            int nearestDay = ringerModeTimeCondition.getNearestWeekDay(currentDay);
                            if (ringerModeTimeCondition.getHour() < currentHour ||
                                    (ringerModeTimeCondition.getHour() == currentHour && ringerModeTimeCondition.getMinute() <= currentMinute)) {
                                int addDay = nearestDay - currentDay;
                                if (addDay < 0) {
                                    addDay = addDay + 7;
                                }
                                calendar.add(Calendar.DATE, addDay == 0 ? 7 : addDay);
                            }
                            calendar.set(Calendar.HOUR_OF_DAY, ringerModeTimeCondition.getHour());
                            calendar.set(Calendar.MINUTE, ringerModeTimeCondition.getMinute());
                            calendar.set(Calendar.SECOND, 0);

                            SharedPreferences sharedPreferences = getSharedPreferences("RINGER_MODES", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("RINGER_MODE", ringerMode.getRingerMode().value);
                            if (ringerMode.getRingerMode().equals(RingerMode.NORMAL)) {
                                editor.putInt("RINGER_MODE_VOLUME", ringerMode.getRingerModeVolume());
                            }
                            editor.apply();

                            Log.d("RINGER_MODES_SET_ALARM", "Planed date: " + calendar.getTime().toString() + " Planed mode: " + ringerMode.getRingerMode());

                            Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                            alarmIntent.setAction(CUSTOM_INTENT);
                            PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            if (alarmManager != null) {
                                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
                            }
                        });
                        compositeDisposable.add(ringerModeDisposable);
                        Log.d("RINGER_MODES_SET_ALARM", "Planned regime: " + ringerModeTimeCondition.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    private RingerModeTimeCondition getNearestCondition(int currentHour, int currentMinute, int currentDay, List<RingerModeTimeCondition> ringerModeTimeConditionArrayList) {
        ArrayList<RingerModeTimeCondition> resultList = new ArrayList<>();
        for (RingerModeTimeCondition rmc : ringerModeTimeConditionArrayList) {
            if (rmc.getDays().contains(String.valueOf(currentDay)) &&
                    rmc.getHour() > currentHour || rmc.getHour() == currentHour && rmc.getMinute() > currentMinute) {
                resultList.add(rmc);
            }
        }
        if (!resultList.isEmpty()) {
            Collections.sort(resultList);
            return resultList.get(0);
        } else currentDay++;

        for (int i = 0; i < 7; i++) {
            for (RingerModeTimeCondition rmc : ringerModeTimeConditionArrayList) {
                if (rmc.getDays().contains(String.valueOf(currentDay))) {
                    resultList.add(rmc);
                }
            }
            if (!resultList.isEmpty()) {
                Collections.sort(resultList);
                return resultList.get(0);
            } else currentDay++;
            if (currentDay == 8) {
                currentDay = 1;
            }
        }
        Log.d("RINGER_MODES", "null");
        return null;
    }
}