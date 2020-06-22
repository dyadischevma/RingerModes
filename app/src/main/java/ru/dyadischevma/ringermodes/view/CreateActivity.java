package ru.dyadischevma.ringermodes.view;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.DataViewModel;
import ru.dyadischevma.ringermodes.model.RingerMode;
import ru.dyadischevma.ringermodes.model.RingerModeItem;

public class CreateActivity extends AppCompatActivity {

    private RingerMode ringerMode = RingerMode.NORMAL;
    private SeekBar seekBar;
    private int volume = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        final DataViewModel viewModel = new ViewModelProvider(this).get(DataViewModel.class);

        final EditText editTextName = findViewById(R.id.editTextTextName);

        RadioButton radioButtonNormal = findViewById(R.id.radioButtonNormal);
        radioButtonNormal.setOnClickListener(radioButtonClickListener);

        RadioButton radioButtonVibrate = findViewById(R.id.radioButtonVibrate);
        radioButtonVibrate.setOnClickListener(radioButtonClickListener);

        RadioButton radioButtonSilent = findViewById(R.id.radioButtonSilent);
        radioButtonSilent.setOnClickListener(radioButtonClickListener);


        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMin(0);
        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));

        Button button = findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RingerModeItem ringerModeItem = new RingerModeItem(
                        editTextName.getText().toString(),
                        ringerMode,
                        volume);

                volume = seekBar.getProgress();
                ringerModeItem.setId(viewModel.insertItem(ringerModeItem));
                finish();
            }
        });
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            switch (rb.getId()) {
                case R.id.radioButtonNormal:
                    ringerMode = RingerMode.NORMAL;
                    seekBar.setEnabled(true);
                    seekBar.setProgress(volume);
                    break;
                case R.id.radioButtonVibrate:
                    ringerMode = RingerMode.VIBRATE;
                    if (seekBar.isEnabled()) {
                        volume = seekBar.getProgress();
                    }
                    seekBar.setEnabled(false);
                    seekBar.setProgress(0);
                    break;
                case R.id.radioButtonSilent:
                    ringerMode = RingerMode.SILENT;
                    if (seekBar.isEnabled()) {
                        volume = seekBar.getProgress();
                    }
                    seekBar.setEnabled(false);
                    seekBar.setProgress(0);
                    break;
                default:
                    break;
            }
        }
    };
}