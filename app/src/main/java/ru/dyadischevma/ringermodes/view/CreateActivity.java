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
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dpro.widgets.WeekdaysPicker;

import java.util.ArrayList;
import java.util.List;

import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.DataViewModel;
import ru.dyadischevma.ringermodes.model.RingerMode;
import ru.dyadischevma.ringermodes.model.RingerModeCondition;
import ru.dyadischevma.ringermodes.model.RingerModeItem;

public class CreateActivity extends AppCompatActivity {

    private RingerMode ringerMode = RingerMode.NORMAL;
    private SeekBar seekBar;
    private int volume = 0;
    List<Integer> daysList = new ArrayList<>();

    private RecyclerViewConditionsAdapter mRecyclerViewConditionsAdapter;
    private List<RingerModeCondition> mRingerModeConditionsList = new ArrayList<>();

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

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(v -> {
            volume = seekBar.getProgress();
            RingerModeItem ringerModeItem = new RingerModeItem(
                    editTextName.getText().toString(),
                    ringerMode,
                    volume);

            viewModel.insertItem(ringerModeItem)
                    .subscribe(l -> {
                        for (RingerModeCondition rmc : mRingerModeConditionsList) {
                            rmc.setRingerModeId(l);
                        }
                        viewModel.insertRingerModeConditionsItems(mRingerModeConditionsList);
                    });
            finish();
        });

        Button buttonCreateAddTime = findViewById(R.id.buttonCreateAddTime);
        buttonCreateAddTime.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Name");

            View customLayout = getLayoutInflater().inflate(R.layout.choose_time_item, null);
            builder.setView(customLayout);

            TimePicker timePicker = customLayout.findViewById(R.id.timePicker);
            timePicker.setIs24HourView(true);

            WeekdaysPicker weekdaysPicker = customLayout.findViewById(R.id.dialogWeekdaysPicker);
            weekdaysPicker.setOnWeekdaysChangeListener((view, clickedDayOfWeek, selectedDays) -> {
                daysList = selectedDays;
            });

            builder.setPositiveButton("OK", (dialog, which) -> {
                StringBuilder days = new StringBuilder();
                for (int day : daysList) {
                    days.append(day);
                }
                mRingerModeConditionsList.add(new RingerModeCondition(timePicker.getHour(), timePicker.getMinute(), days.toString()));
                mRecyclerViewConditionsAdapter.setListData(mRingerModeConditionsList);
            });

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        RecyclerView recyclerViewTimes = findViewById(R.id.recyclerViewTimes);
        recyclerViewTimes.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewConditionsAdapter = new RecyclerViewConditionsAdapter();

        if (mRingerModeConditionsList != null) {
            mRecyclerViewConditionsAdapter.setListData(mRingerModeConditionsList);
        }
        recyclerViewTimes.setAdapter(mRecyclerViewConditionsAdapter);
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