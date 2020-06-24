package ru.dyadischevma.ringermodes.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.dyadischevma.ringermodes.MainActivity;

public class SwipeToDeleteHelperCallback extends ItemTouchHelper.Callback {
    private RecyclerViewAdapter mAdapter;
    MainActivity mActivity;

    public SwipeToDeleteHelperCallback(RecyclerViewAdapter adapter, MainActivity activity) {
        mAdapter = adapter;
        mActivity = activity;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mActivity.deleteItem(mAdapter.getItem( viewHolder.getAdapterPosition()));
    }
}