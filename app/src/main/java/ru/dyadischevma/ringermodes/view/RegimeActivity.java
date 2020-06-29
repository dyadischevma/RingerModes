package ru.dyadischevma.ringermodes.view;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dpro.widgets.WeekdaysPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.adapters.RecyclerViewConditionsAdapter;
import ru.dyadischevma.ringermodes.adapters.SwipeToDeleteTimeConditionHelperCallback;
import ru.dyadischevma.ringermodes.model.entity.RingerMode;
import ru.dyadischevma.ringermodes.model.entity.RingerModeItem;
import ru.dyadischevma.ringermodes.model.entity.RingerModeTimeCondition;
import ru.dyadischevma.ringermodes.presenter.RegimePresenter;

public class RegimeActivity extends AppCompatActivity {
    private RecyclerViewConditionsAdapter mRecyclerViewConditionsAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText editTextName;

    RadioGroup radioGroup;

    RadioButton radioButtonNormal;
    RadioButton radioButtonVibrate;
    RadioButton radioButtonSilent;

    private SeekBar mSeekBar;

    RegimePresenter presenter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regime);

        editTextName = findViewById(R.id.editTextTextName);
        editTextName.requestFocus();

        radioGroup = findViewById(R.id.radioGroup);
        radioButtonNormal = findViewById(R.id.radioButtonNormal);
        radioButtonNormal.setOnClickListener(radioButtonClickListener);

        radioButtonVibrate = findViewById(R.id.radioButtonVibrate);
        radioButtonVibrate.setOnClickListener(radioButtonClickListener);

        radioButtonSilent = findViewById(R.id.radioButtonSilent);
        radioButtonSilent.setOnClickListener(radioButtonClickListener);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setMin(0);
        if (audioManager != null) {
            mSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
        }

        long ringerModeId = getIntent().getLongExtra("ringerModeId", -1);
        presenter = new RegimePresenter(getApplication(), ringerModeId);

        FloatingActionButton floatingActionButtonAddTime = findViewById(R.id.floatingActionButtonAddTime);
        floatingActionButtonAddTime.setOnClickListener(v ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Name");

            View customLayout = getLayoutInflater().inflate(R.layout.choose_time_item, null);
            builder.setView(customLayout);

            TimePicker timePicker = customLayout.findViewById(R.id.timePicker);
            timePicker.setIs24HourView(true);

            WeekdaysPicker weekdaysPicker = customLayout.findViewById(R.id.dialogWeekdaysPicker);

            builder.setPositiveButton("OK", (dialog, which) -> {
                List<Integer> mDaysList = weekdaysPicker.getSelectedDays();
                StringBuilder days = new StringBuilder();
                for (int day : mDaysList) {
                    days.append(day);
                }
                presenter.addToRingerModeTimeConditionsList(
                        new RingerModeTimeCondition(timePicker.getHour(), timePicker.getMinute(), days.toString()));
                mRecyclerViewConditionsAdapter.setTimesListData(presenter.getRingerModeTimeConditionsList());
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        RecyclerView recyclerViewTimes = findViewById(R.id.recyclerViewTimes);
        recyclerViewTimes.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewConditionsAdapter = new RecyclerViewConditionsAdapter();
        recyclerViewTimes.setAdapter(mRecyclerViewConditionsAdapter);

        SwipeToDeleteTimeConditionHelperCallback swipeToDeleteTimeConditionHelperCallback = new SwipeToDeleteTimeConditionHelperCallback(mRecyclerViewConditionsAdapter, presenter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteTimeConditionHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewTimes);

        FloatingActionButton floatingActionButtonSave = findViewById(R.id.floatingActionButtonSave);
        floatingActionButtonSave.setOnClickListener(v -> presenter.saveRegime());

        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            switch (rb.getId()) {
                case R.id.radioButtonNormal:
                    mSeekBar.setEnabled(true);
                    mSeekBar.setProgress(presenter.getVolume());
                    break;
                case R.id.radioButtonVibrate:
                case R.id.radioButtonSilent:
                    if (mSeekBar.isEnabled()) {
                        presenter.setVolume(mSeekBar.getProgress());
                    }
                    mSeekBar.setEnabled(false);
                    mSeekBar.setProgress(0);
                    break;
            }
        }
    };

    public void setRingerMode(RingerModeItem ringerMode) {
        editTextName.setText(ringerMode.getName());
        editTextName.setSelection(editTextName.getText().toString().length());

        switch (ringerMode.getRingerMode()) {
            case NORMAL:
                radioButtonNormal.setChecked(true);
                mSeekBar.setEnabled(true);
                mSeekBar.setProgress(ringerMode.getRingerModeVolume());
                break;
            case VIBRATE:
                radioButtonVibrate.setChecked(true);
                mSeekBar.setEnabled(false);
                mSeekBar.setProgress(ringerMode.getRingerModeVolume());
                break;
            case SILENT:
                radioButtonSilent.setChecked(true);
                mSeekBar.setEnabled(false);
                mSeekBar.setProgress(ringerMode.getRingerModeVolume());
                break;
        }
    }

    public RingerMode getRingerMode() {
        int checkedButton = radioGroup.getCheckedRadioButtonId();
        switch (checkedButton) {
            case R.id.radioButtonNormal:
                return RingerMode.NORMAL;
            case R.id.radioButtonVibrate:
                return RingerMode.VIBRATE;
            case R.id.radioButtonSilent:
                return RingerMode.SILENT;
        }
        return RingerMode.NORMAL;
    }

    public RingerModeItem getRingerModeItem() {
        RingerModeItem ringerModeItem = new RingerModeItem();
        ringerModeItem.setName(editTextName.getText().toString());
        ringerModeItem.setRingerMode(getRingerMode());
        ringerModeItem.setRingerModeVolume(mSeekBar.getProgress());
        return ringerModeItem;
    }

    public void setConditionsListData(List<RingerModeTimeCondition> ringerModeTimeConditionsList) {
        mRecyclerViewConditionsAdapter.setTimesListData(ringerModeTimeConditionsList);
    }
}