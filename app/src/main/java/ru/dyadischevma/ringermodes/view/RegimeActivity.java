package ru.dyadischevma.ringermodes.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.DataViewModel;
import ru.dyadischevma.ringermodes.model.RingerModeConditions;
import ru.dyadischevma.ringermodes.model.RingerModeItem;

public class RegimeActivity extends AppCompatActivity {
    private RingerModeItem mRingerModeItem;
    private DataViewModel viewModel;
    private RecyclerViewConditionsAdapter mRecyclerViewConditionsAdapter;
    private List<RingerModeConditions> mRingerModeConditionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regime);

        final EditText editTextName = findViewById(R.id.editTextTextName);
        RadioButton radioButtonNormal = findViewById(R.id.radioButtonNormal);
        RadioButton radioButtonVibrate = findViewById(R.id.radioButtonVibrate);
        RadioButton radioButtonSilent = findViewById(R.id.radioButtonSilent);


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
                        case VIBRATE:
                            radioButtonVibrate.setChecked(true);
                        case SILENT:
                            radioButtonSilent.setChecked(true);
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

    private void setListData(List<RingerModeConditions> ringerModeConditionsList) {
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