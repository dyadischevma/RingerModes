package ru.dyadischevma.ringermodes.view;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.DataViewModel;
import ru.dyadischevma.ringermodes.model.RingerModeCondition;
import ru.dyadischevma.ringermodes.model.RingerModeItem;

public class RegimeActivity extends AppCompatActivity {
    private RingerModeItem mRingerModeItem;
    private DataViewModel viewModel;
    private RecyclerViewConditionsAdapter mRecyclerViewConditionsAdapter;
    private List<RingerModeCondition> mRingerModeConditionsList;
    private SeekBar seekBar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regime);

        final EditText editTextName = findViewById(R.id.editTextTextName);
        RadioButton radioButtonNormal = findViewById(R.id.radioButtonNormal);
        RadioButton radioButtonVibrate = findViewById(R.id.radioButtonVibrate);
        RadioButton radioButtonSilent = findViewById(R.id.radioButtonSilent);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMin(0);
        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));

        long ringerModeId = getIntent().getLongExtra("ringerModeId", 0);

        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
        viewModel.getConditions(ringerModeId).observe(this, ringerModeConditionsList -> {
            if (ringerModeConditionsList != null) {
                setListData(ringerModeConditionsList);
            }
        });

        Disposable result = viewModel.getRingerModeItem(ringerModeId).subscribe(
                ringerModeItem -> {
                    mRingerModeItem = ringerModeItem;
                    editTextName.setText(mRingerModeItem.getName());

                    switch (mRingerModeItem.getRingerMode()) {
                        case NORMAL:
                            radioButtonNormal.setChecked(true);
                            seekBar.setEnabled(true);
                            seekBar.setProgress(mRingerModeItem.getRingerModeValue());
                            break;
                        case VIBRATE:
                            radioButtonVibrate.setChecked(true);
                            seekBar.setEnabled(false);
                            seekBar.setProgress(mRingerModeItem.getRingerModeValue());
                            break;
                        case SILENT:
                            radioButtonSilent.setChecked(true);
                            seekBar.setEnabled(false);
                            seekBar.setProgress(mRingerModeItem.getRingerModeValue());
                            break;
                    }
                }
        );

        RecyclerView recyclerViewTimes = findViewById(R.id.recyclerViewTimes);
        recyclerViewTimes.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewConditionsAdapter = new RecyclerViewConditionsAdapter();

        if (mRingerModeConditionsList != null) {
            mRecyclerViewConditionsAdapter.setListData(mRingerModeConditionsList);
        }
        recyclerViewTimes.setAdapter(mRecyclerViewConditionsAdapter);
    }

    private void setListData(List<RingerModeCondition> ringerModeConditionsList) {
        if (mRingerModeConditionsList == null) {
            mRingerModeConditionsList = new ArrayList<>();
        }
        mRingerModeConditionsList.clear();
        mRingerModeConditionsList.addAll(ringerModeConditionsList);

        if (mRecyclerViewConditionsAdapter != null) {
            mRecyclerViewConditionsAdapter.setListData(ringerModeConditionsList);
        }
    }
}