package ru.dyadischevma.ringermodes.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.dyadischevma.ringermodes.model.RingerModeTimeCondition;
import ru.dyadischevma.ringermodes.model.RingerModeRepository;

public class SetAlarm extends Service {
    public SetAlarm() {
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
                            Log.d("RINGER_MODES_SET_ALARM", "Planed date: " + calendar.getTime().toString());

                            Intent alarmIntent = new Intent(getApplicationContext(), ChangeRingerMode.class);
                            alarmIntent.putExtra("RINGER_MODE", ringerMode.getRingerMode().value);
                            PendingIntent pIntent = PendingIntent.getService(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

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
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
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