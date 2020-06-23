package ru.dyadischevma.ringermodes.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.RingerModeCondition;

public class RecyclerViewConditionsAdapter extends RecyclerView.Adapter<RecyclerViewConditionsAdapter.ViewHolder> {
    private List<RingerModeCondition> mRingerModeConditionsList;

    public void setListData(List<RingerModeCondition> ringerModeConditionsList) {
        if (mRingerModeConditionsList == null) {
            mRingerModeConditionsList = new ArrayList<>();
        }
        mRingerModeConditionsList.clear();
        mRingerModeConditionsList.addAll(ringerModeConditionsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_item, parent, false);
        return new RecyclerViewConditionsAdapter.ViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RingerModeCondition item = mRingerModeConditionsList.get(position);
        StringBuilder text = new StringBuilder();
        text.append(String.format("%02d", item.getHour())).append(":").append(String.format("%02d", item.getMinute()));
        holder.textViewConditionTime.setText(text.toString());
    }

    @Override
    public int getItemCount() {
        if (mRingerModeConditionsList != null) {
            return mRingerModeConditionsList.size();
        } else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewConditionTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewConditionTime = itemView.findViewById(R.id.textViewConditionTime);
        }
    }
}


