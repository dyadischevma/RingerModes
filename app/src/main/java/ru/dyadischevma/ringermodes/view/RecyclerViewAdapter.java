package ru.dyadischevma.ringermodes.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.RingerMode;
import ru.dyadischevma.ringermodes.model.RingerModeItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<RingerModeItem> mDataItemList;

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mNameTextView.setText(mDataItemList.get(position).getName());
        holder.mRegimeModeTextView.setText(mDataItemList.get(position).getRingerMode().toString());
        holder.mRegimeVolumeTextView.setText(String.valueOf(mDataItemList.get(position).getRingerModeValue()));
    }

    @Override
    public int getItemCount() {
        if (mDataItemList != null) {
            return mDataItemList.size();
        } else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mNameTextView;
        private final TextView mRegimeModeTextView;
        private final TextView mRegimeVolumeTextView;

        private RingerModeItem mRingerModeItem;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mNameTextView = (TextView) view.findViewById(R.id.textViewName);
            mRegimeModeTextView = (TextView) view.findViewById(R.id.textViewRingerMode);
            mRegimeVolumeTextView = (TextView) view.findViewById(R.id.textViewRingerModeValue);
        }
    }
}