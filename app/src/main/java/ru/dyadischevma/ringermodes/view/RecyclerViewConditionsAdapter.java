package ru.dyadischevma.ringermodes.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.RingerModeConditions;

public class RecyclerViewConditionsAdapter extends RecyclerView.Adapter<RecyclerViewConditionsAdapter.ViewHolder> {
    private List<RingerModeConditions> mRingerModeConditionsList;

    public void setListData(List<RingerModeConditions> ringerModeConditionsList) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.condition_item, parent, false);
        return new RecyclerViewConditionsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RingerModeConditions item = mRingerModeConditionsList.get(position);

        holder.textViewConditionHours.setText(String.valueOf(item.getHour()));
        holder.textViewConditionMinute.setText(String.valueOf(item.getMinute()));
    }

    @Override
    public int getItemCount() {
        if (mRingerModeConditionsList != null) {
            return mRingerModeConditionsList.size();
        } else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewConditionHours;
        private TextView textViewConditionMinute;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewConditionHours = itemView.findViewById(R.id.textViewConditionHours);
            textViewConditionMinute = itemView.findViewById(R.id.textViewConditionMinute);
        }
    }
}


