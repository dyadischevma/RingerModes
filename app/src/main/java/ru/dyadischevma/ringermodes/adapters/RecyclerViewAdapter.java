package ru.dyadischevma.ringermodes.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.entity.RingerMode;
import ru.dyadischevma.ringermodes.model.entity.RingerModeItem;
import ru.dyadischevma.ringermodes.view.RegimeActivity;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<RingerModeItem> mDataItemList;

    public void setListData(List<RingerModeItem> dataItemList) {
        if (mDataItemList == null) {
            mDataItemList = new ArrayList<>();
        }
        mDataItemList.clear();
        mDataItemList.addAll(dataItemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.regime_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        RingerModeItem item = mDataItemList.get(position);

        holder.mNameTextView.setText(item.getName());
        holder.mRegimeModeTextView.setText(item.getRingerMode().toString());
        if (item.getRingerMode().equals(RingerMode.NORMAL)) {
            holder.textViewLabelRingerModeVolume.setVisibility(View.VISIBLE);
            holder.mRegimeVolumeTextView.setVisibility(View.VISIBLE);
            holder.mRegimeVolumeTextView.setText(String.valueOf(item.getRingerModeVolume()));
        }
    }

    @Override
    public int getItemCount() {
        if (mDataItemList != null) {
            return mDataItemList.size();
        } else return 0;
    }

    public RingerModeItem getItem(int position) {
        return mDataItemList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mNameTextView;
        private final TextView mRegimeModeTextView;
        private final TextView textViewLabelRingerModeVolume;
        private final TextView mRegimeVolumeTextView;

        private RingerModeItem mRingerModeItem;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            mNameTextView = view.findViewById(R.id.textViewName);
            mRegimeModeTextView = view.findViewById(R.id.textViewRingerMode);
            textViewLabelRingerModeVolume = view.findViewById(R.id.textViewLabelRingerModeVolume);
            mRegimeVolumeTextView = view.findViewById(R.id.textViewRingerModeValue);

            mView.setOnClickListener(v -> {
                Intent intent = new Intent(view.getContext(), RegimeActivity.class);
                intent.putExtra("ringerModeId", getItem(getAdapterPosition()).getId());
                view.getContext().startActivity(intent);
            });
        }
    }
}