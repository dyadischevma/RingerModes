package ru.dyadischevma.ringermodes.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.dyadischevma.ringermodes.adapters.RecyclerViewConditionsAdapter;
import ru.dyadischevma.ringermodes.presenter.Presenter;
import ru.dyadischevma.ringermodes.presenter.RegimePresenter;
import ru.dyadischevma.ringermodes.view.RegimeActivity;

public class SwipeToDeleteTimeConditionHelperCallback extends ItemTouchHelper.Callback {
    private RecyclerViewConditionsAdapter mAdapter;
    RegimePresenter mRegimePresenter;

    public SwipeToDeleteTimeConditionHelperCallback(RecyclerViewConditionsAdapter adapter, RegimePresenter regimePresenter) {
        mAdapter = adapter;
        mRegimePresenter = regimePresenter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mRegimePresenter.deleteRingerModeTimeCondition(mAdapter.getItem(viewHolder.getAdapterPosition()));
    }
}