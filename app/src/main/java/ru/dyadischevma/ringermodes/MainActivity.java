package ru.dyadischevma.ringermodes;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ru.dyadischevma.ringermodes.model.DataViewModel;
import ru.dyadischevma.ringermodes.model.RingerModeItem;
import ru.dyadischevma.ringermodes.services.SetAlarm;
import ru.dyadischevma.ringermodes.view.RecyclerViewAdapter;
import ru.dyadischevma.ringermodes.view.RegimeActivity;
import ru.dyadischevma.ringermodes.view.SwipeToDeleteHelperCallback;

public class MainActivity extends AppCompatActivity {

    private DataViewModel viewModel;
    private List<RingerModeItem> mRingerModeItemList;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent =
                    new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivityForResult(intent, 0);
        }

        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
        viewModel.getAllRingerModeItems().observe(this, dataItems -> {
            if (dataItems != null) {
                setListData(dataItems);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new RecyclerViewAdapter();

        if (mRingerModeItemList != null) {
            mRecyclerViewAdapter.setListData(mRingerModeItemList);
        }
        recyclerView.setAdapter(mRecyclerViewAdapter);

        SwipeToDeleteHelperCallback swipeToDeleteHelperCallback = new SwipeToDeleteHelperCallback(mRecyclerViewAdapter, this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegimeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        startService(new Intent(this, SetAlarm.class));
        super.onResume();
    }

    private void setListData(List<RingerModeItem> dataItemList) {
        if (mRingerModeItemList == null) {
            mRingerModeItemList = new ArrayList<>();
        }
        mRingerModeItemList.clear();
        mRingerModeItemList.addAll(dataItemList);

        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.setListData(dataItemList);
        }
    }

    public void deleteItem(RingerModeItem ringerModeItem) {
        viewModel.deleteItem(ringerModeItem);
    }
}