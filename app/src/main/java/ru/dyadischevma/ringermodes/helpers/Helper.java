package ru.dyadischevma.ringermodes.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.dyadischevma.ringermodes.model.RingerMode;
import ru.dyadischevma.ringermodes.model.RingerModeRepository;
import ru.dyadischevma.ringermodes.model.RingerModeTimeCondition;
import ru.dyadischevma.ringermodes.services.AlarmReceiver;

public class Helper {
    public static final String CUSTOM_INTENT = "ru.dyadischevma.intent.action.ALARM";

    private static RingerModeTimeCondition getNearestCondition(int currentHour, int currentMinute, int currentDay, List<RingerModeTimeCondition> ringerModeTimeConditionArrayList) {
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

    public static void setAlarm(RingerModeRepository ringerModeRepository, Context context, CompositeDisposable compositeDisposable) {
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        Disposable disposable = ringerModeRepository.getAllTimeConditions()
                .subscribe(s -> {
                    RingerModeTimeCondition ringerModeTimeCondition = Helper.getNearestCondition(currentHour, currentMinute, currentDay, s);
                    if (ringerModeTimeCondition != null) {
                        Disposable ringerModeDisposable = ringerModeRepository.getRingerMode(ringerModeTimeCondition.getRingerModeId())
                                .subscribe(ringerMode -> {

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTimeInMillis(System.currentTimeMillis());
                                    int nearestDay = ringerModeTimeCondition.getNearestWeekDay(currentDay);
                                    if (ringerModeTimeCondition.getHour() < currentHour ||
                                            (ringerModeTimeCondition.getHour() == currentHour &&
                                                    ringerModeTimeCondition.getMinute() <= currentMinute)
                                    ) {
                                        int addDay = nearestDay - currentDay;
                                        if (addDay < 0) {
                                            addDay = addDay + 7;
                                        }
                                        calendar.add(Calendar.DATE, addDay == 0 ? 7 : addDay);
                                    }
                                    calendar.set(Calendar.HOUR_OF_DAY, ringerModeTimeCondition.getHour());
                                    calendar.set(Calendar.MINUTE, ringerModeTimeCondition.getMinute());
                                    calendar.set(Calendar.SECOND, 0);
                                    Log.d("RINGER_MODES_SET_ALARM",
                                            "Planed date: " + calendar.getTime().toString() +
                                                    " Planed mode: " + ringerMode.getRingerMode());

                                    SharedPreferences sharedPreferences = context.getSharedPreferences(
                                            "RINGER_MODES",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("RINGER_MODE", ringerMode.getRingerMode().value);
                                    if (ringerMode.getRingerMode().equals(RingerMode.NORMAL)) {
                                        editor.putInt("RINGER_MODE_VOLUME", ringerMode.getRingerModeVolume());
                                    }
                                    editor.apply();

                                    Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                                    alarmIntent.setAction(CUSTOM_INTENT);
                                    PendingIntent pIntent = PendingIntent.getBroadcast(context, 0,
                                            alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
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
}