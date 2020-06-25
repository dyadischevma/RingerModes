package ru.dyadischevma.ringermodes.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

import ru.dyadischevma.ringermodes.model.RingerMode;

public class ChangeRingerMode extends Service {
    public ChangeRingerMode() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RingerMode ringerMode = RingerMode.fromInt(intent.getIntExtra("RINGER_MODE", 0));
        int volume = intent.getIntExtra("RINGER_MODE_VOLUME", -1);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setRingerMode(ringerMode.value);
            if (volume > -1) {
                audioManager.setStreamVolume(AudioManager.STREAM_RING, volume, 0);
            }
            Log.d("RINGER_MODES", "Set mode " + ringerMode);
        }
        startService(new Intent(this, SetAlarm.class));
        return super.onStartCommand(intent, flags, startId);
    }
}