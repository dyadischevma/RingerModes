package ru.dyadischevma.ringermodes.view;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ru.dyadischevma.ringermodes.model.DataViewModel;

public class SwipeToDeleteHelperCallback extends ItemTouchHelper.Callback {
    private RecyclerViewAdapter mAdapter;
    DataViewModel viewModel;

    public SwipeToDeleteHelperCallback(RecyclerViewAdapter adapter, Activity activity) {
        mAdapter = adapter;
//        viewModel = new ViewModelProvider(activityty).get(DataViewModel.class);
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
//        viewModel.deleteItemById(viewHolder.getAdapterPosition());
    }
}