package ru.dyadischevma.ringermodes.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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

import io.reactivex.disposables.CompositeDisposable;
import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.helpers.Helper;
import ru.dyadischevma.ringermodes.model.RingerMode;
import ru.dyadischevma.ringermodes.model.RingerModeRepository;

public class ChangeRingerMode extends JobIntentService {
    private static final int JOB_ID = 649;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


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
                new NotificationCompat.Builder(this, createNotificationChannel("ringer_mode_change", "Ringer Mode"))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Ringer mode")
                        .setContentText("Changing ringer mode");

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
            Log.d("RINGER_MODES", "Current mode " +  RingerMode.fromInt(audioManager.getRingerMode()));
            Log.d("RINGER_MODES", "Set mode " + ringerMode);
        }
        Helper.setAlarm(new RingerModeRepository(getApplication()), getApplicationContext(), compositeDisposable);
        stopForeground(true);
        return super.onBind(intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}