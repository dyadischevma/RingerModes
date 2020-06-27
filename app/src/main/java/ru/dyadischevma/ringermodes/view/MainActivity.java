package ru.dyadischevma.ringermodes.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.RingerModeItem;
import ru.dyadischevma.ringermodes.presenter.MainPresenter;
import ru.dyadischevma.ringermodes.adapters.RecyclerViewAdapter;
import ru.dyadischevma.ringermodes.adapters.SwipeToDeleteHelperCallback;

public class MainActivity extends AppCompatActivity {
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private MainPresenter mainPresenter;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.detachView();
        super.onDestroy();
    }

    private void init() {
        mainPresenter = new MainPresenter(getApplication());
        mainPresenter.attachView(this);
        mainPresenter.viewIsReady();

        mainPresenter.checkGrants();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new RecyclerViewAdapter();

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        SwipeToDeleteHelperCallback swipeToDeleteHelperCallback = new SwipeToDeleteHelperCallback(mRecyclerViewAdapter, mainPresenter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mFloatingActionButton = findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(v -> mainPresenter.startCreatingRegime());
    }

    public void setListData(List<RingerModeItem> dataItemList) {
        mRecyclerViewAdapter.setListData(dataItemList);
    }
}