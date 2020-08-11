package ru.dyadischevma.ringermodes.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.dyadischevma.ringermodes.presenter.MainPresenter;

public class SwipeToDeleteHelperCallback extends ItemTouchHelper.Callback {
    private RecyclerViewAdapter mAdapter;
    MainPresenter mPresenter;

    public SwipeToDeleteHelperCallback(RecyclerViewAdapter adapter, MainPresenter presenter) {
        mAdapter = adapter;
        mPresenter = presenter;
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
        mPresenter.deleteRingerMode(mAdapter.getItem(viewHolder.getAdapterPosition()));
    }
}