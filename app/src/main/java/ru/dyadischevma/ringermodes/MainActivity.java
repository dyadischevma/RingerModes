package ru.dyadischevma.ringermodes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ru.dyadischevma.ringermodes.model.DataViewModel;
import ru.dyadischevma.ringermodes.model.RingerModeItem;
import ru.dyadischevma.ringermodes.view.CreateActivity;
import ru.dyadischevma.ringermodes.view.RecyclerViewAdapter;
import ru.dyadischevma.ringermodes.view.SwipeToDeleteHelperCallback;

public class MainActivity extends AppCompatActivity {

    private DataViewModel viewModel;
    private List<RingerModeItem> mRingerModeItemList;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
        viewModel.getAllData().observe(this, new Observer<List<RingerModeItem>>() {
            @Override
            public void onChanged(@Nullable List<RingerModeItem> dataItems) {
                if (dataItems != null) {
                    setListData(dataItems);
                }
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


        FloatingActionButton floatingActionButton  = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }



    private void setListData(List<RingerModeItem> dataItemList) {
        //if data changed, set new list to adapter of recyclerview

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