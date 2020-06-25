package ru.dyadischevma.ringermodes.view;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dpro.widgets.WeekdaysPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.DataViewModel;
import ru.dyadischevma.ringermodes.model.RingerMode;
import ru.dyadischevma.ringermodes.model.RingerModeTimeCondition;
import ru.dyadischevma.ringermodes.model.RingerModeItem;

public class RegimeActivity extends AppCompatActivity {
    private RingerMode mRingerMode = RingerMode.NORMAL;
    private SeekBar mSeekBar;
    private int mVolume = 0;
    List<Integer> mDaysList = new ArrayList<>();

    private RingerModeItem mRingerModeItem;
    private DataViewModel viewModel;
    private RecyclerViewConditionsAdapter mRecyclerViewConditionsAdapter;
    private List<RingerModeTimeCondition> mRingerModeTimeConditionsList = new ArrayList<>();

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regime);

        final EditText editTextName = findViewById(R.id.editTextTextName);
        editTextName.requestFocus();

        RadioButton radioButtonNormal = findViewById(R.id.radioButtonNormal);
        radioButtonNormal.setOnClickListener(radioButtonClickListener);

        RadioButton radioButtonVibrate = findViewById(R.id.radioButtonVibrate);
        radioButtonVibrate.setOnClickListener(radioButtonClickListener);

        RadioButton radioButtonSilent = findViewById(R.id.radioButtonSilent);
        radioButtonSilent.setOnClickListener(radioButtonClickListener);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setMin(0);
        if (audioManager != null) {
            mSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
        }

        long ringerModeId = getIntent().getLongExtra("ringerModeId", -1);

        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
        if (ringerModeId == -1) {
            mRingerModeItem = new RingerModeItem();
        } else {
            viewModel.getTimeConditions(ringerModeId).observe(this, ringerModeConditionsList -> {
                if (ringerModeConditionsList != null) {
                    setConditionsListData(ringerModeConditionsList);
                }
            });

            Disposable disposable = viewModel.getRingerMode(ringerModeId)
                    .subscribe(ringerModeItem -> {
                                mRingerModeItem = ringerModeItem;
                                mRingerMode = ringerModeItem.getRingerMode();
                                editTextName.setText(mRingerModeItem.getName());
                                editTextName.setSelection(editTextName.getText().toString().length());

                                switch (mRingerModeItem.getRingerMode()) {
                                    case NORMAL:
                                        radioButtonNormal.setChecked(true);
                                        mSeekBar.setEnabled(true);
                                        mSeekBar.setProgress(mRingerModeItem.getRingerModeValue());
                                        break;
                                    case VIBRATE:
                                        radioButtonVibrate.setChecked(true);
                                        mSeekBar.setEnabled(false);
                                        mSeekBar.setProgress(mRingerModeItem.getRingerModeValue());
                                        break;
                                    case SILENT:
                                        radioButtonSilent.setChecked(true);
                                        mSeekBar.setEnabled(false);
                                        mSeekBar.setProgress(mRingerModeItem.getRingerModeValue());
                                        break;
                                }
                            }
                    );
            compositeDisposable.add(disposable);
        }

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
                mDaysList = weekdaysPicker.getSelectedDays();
                StringBuilder days = new StringBuilder();
                for (int day : mDaysList) {
                    days.append(day);
                }
                mRingerModeTimeConditionsList.add(new RingerModeTimeCondition(timePicker.getHour(), timePicker.getMinute(), days.toString()));
                mRecyclerViewConditionsAdapter.setTimesListData(mRingerModeTimeConditionsList);
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        RecyclerView recyclerViewTimes = findViewById(R.id.recyclerViewTimes);
        recyclerViewTimes.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewConditionsAdapter = new RecyclerViewConditionsAdapter();

        if (mRingerModeTimeConditionsList != null) {
            mRecyclerViewConditionsAdapter.setTimesListData(mRingerModeTimeConditionsList);
        }
        recyclerViewTimes.setAdapter(mRecyclerViewConditionsAdapter);

        SwipeToDeleteTimeConditionHelperCallback swipeToDeleteTimeConditionHelperCallback = new SwipeToDeleteTimeConditionHelperCallback(mRecyclerViewConditionsAdapter, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteTimeConditionHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewTimes);

        FloatingActionButton floatingActionButtonSave = findViewById(R.id.floatingActionButtonSave);
        floatingActionButtonSave.setOnClickListener(v ->

        {
            mVolume = mSeekBar.getProgress();
            mRingerModeItem.setName(editTextName.getText().toString());
            mRingerModeItem.setRingerMode(mRingerMode);
            mRingerModeItem.setRingerModeValue(mVolume);

            Disposable insertRingerMode = viewModel.insertRingerMode(mRingerModeItem)
                    .subscribe(l -> {
                        for (RingerModeTimeCondition rmc : mRingerModeTimeConditionsList) {
                            rmc.setRingerModeId(l);
                        }
                        viewModel.insertRingerModeTimeConditions(mRingerModeTimeConditionsList);
                    });
            finish();
            compositeDisposable.add(insertRingerMode);
        });
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
                    mRingerMode = RingerMode.NORMAL;
                    mSeekBar.setEnabled(true);
                    mSeekBar.setProgress(mVolume);
                    break;
                case R.id.radioButtonVibrate:
                    mRingerMode = RingerMode.VIBRATE;
                    if (mSeekBar.isEnabled()) {
                        mVolume = mSeekBar.getProgress();
                    }
                    mSeekBar.setEnabled(false);
                    mSeekBar.setProgress(0);
                    break;
                case R.id.radioButtonSilent:
                    mRingerMode = RingerMode.SILENT;
                    if (mSeekBar.isEnabled()) {
                        mVolume = mSeekBar.getProgress();
                    }
                    mSeekBar.setEnabled(false);
                    mSeekBar.setProgress(0);
                    break;
                default:
                    break;
            }
        }
    };

    public void deleteRingerModeConditionItem(RingerModeTimeCondition ringerModeTimeCondition) {
        viewModel.deleteRingerModeTimeCondition(ringerModeTimeCondition);
    }

    private void setConditionsListData(List<RingerModeTimeCondition> ringerModeTimeConditionsList) {
        if (mRingerModeTimeConditionsList == null) {
            mRingerModeTimeConditionsList = new ArrayList<>();
        }
        mRingerModeTimeConditionsList.clear();
        mRingerModeTimeConditionsList.addAll(ringerModeTimeConditionsList);

        if (mRecyclerViewConditionsAdapter != null) {
            mRecyclerViewConditionsAdapter.setTimesListData(ringerModeTimeConditionsList);
        }
    }
}