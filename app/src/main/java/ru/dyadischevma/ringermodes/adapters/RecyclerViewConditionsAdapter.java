package ru.dyadischevma.ringermodes.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dpro.widgets.WeekdaysPicker;

import java.util.ArrayList;
import java.util.List;

import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.entity.RingerModeTimeCondition;

public class RecyclerViewConditionsAdapter extends RecyclerView.Adapter<RecyclerViewConditionsAdapter.ViewHolder> {
    private List<RingerModeTimeCondition> mRingerModeTimeConditionsList;

    public void setTimesListData(List<RingerModeTimeCondition> ringerModeTimeConditionsList) {
        if (mRingerModeTimeConditionsList == null) {
            mRingerModeTimeConditionsList = new ArrayList<>();
        }
        mRingerModeTimeConditionsList.clear();
        mRingerModeTimeConditionsList.addAll(ringerModeTimeConditionsList);
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
        RingerModeTimeCondition item = mRingerModeTimeConditionsList.get(position);
        StringBuilder text = new StringBuilder();
        text.append(String.format("%02d", item.getHour())).append(":").append(String.format("%02d", item.getMinute()));
        holder.textViewConditionTime.setText(text.toString());

        List<Integer> selectedDays = new ArrayList<>();
        char[] charDays = mRingerModeTimeConditionsList.get(position).getDays().toCharArray();
        for (char charDay : charDays) {
            selectedDays.add(charDay - '0');
        }
        holder.weekdaysPicker.setSelectedDays(selectedDays);
    }

    @Override
    public int getItemCount() {
        if (mRingerModeTimeConditionsList != null) {
            return mRingerModeTimeConditionsList.size();
        } else return 0;
    }

    public RingerModeTimeCondition getItem(int position) {
        return mRingerModeTimeConditionsList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewConditionTime;
        private WeekdaysPicker weekdaysPicker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewConditionTime = itemView.findViewById(R.id.textViewConditionTime);
            weekdaysPicker = itemView.findViewById(R.id.weekdaysPicker);
        }
    }
}


