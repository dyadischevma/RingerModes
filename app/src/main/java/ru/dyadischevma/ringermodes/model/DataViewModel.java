package ru.dyadischevma.ringermodes.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class DataViewModel extends AndroidViewModel {

    private RingerModeRepository mRingerModeRepository;
    private LiveData<List<RingerModeItem>> mRingerModeItemsListLiveData;

    public DataViewModel(@NonNull Application application) {
        super(application);
        mRingerModeRepository = new RingerModeRepository(application);
        mRingerModeItemsListLiveData = mRingerModeRepository.getAllRingerModes();
    }

    /*
       RingerModeItems
    */
    public LiveData<List<RingerModeItem>> getAllRingerModes() {
        return mRingerModeItemsListLiveData;
    }

    public Single<RingerModeItem> getRingerMode(long id) {
        return mRingerModeRepository.getRingerMode(id);
    }

    public Single<Long> insertRingerMode(RingerModeItem ringerModeItem) {
        return mRingerModeRepository.insertRingerMode(ringerModeItem);
    }

    public void deleteRingerMode(RingerModeItem ringerModeItem) {
        mRingerModeRepository.deleteRingerMode(ringerModeItem);
    }

    /*
        RingerModeTimeCondition
    */
    public Single<List<RingerModeTimeCondition>> getAllTimeConditions() {
        return mRingerModeRepository.getAllTimeConditions();
    }

    public LiveData<List<RingerModeTimeCondition>> getTimeConditions(long ringerModeId) {
        return mRingerModeRepository.getTimeConditions(ringerModeId);
    }

    public Maybe<RingerModeTimeCondition> getNearestTimeCondition(int hour, int minutes, int day) {
        return mRingerModeRepository.getNearestTimeCondition(hour, minutes, day);
    }

    public Maybe<RingerModeTimeCondition> getMinimumTimeCondition(int day) {
        return mRingerModeRepository.getMinimumTimeCondition(day);
    }

    public void insertRingerModeTimeConditions(List<RingerModeTimeCondition> ringerModeTimeConditionArrayList) {
        mRingerModeRepository.insertRingerModeTimeConditions(ringerModeTimeConditionArrayList);
    }

    public void deleteRingerModeTimeCondition(RingerModeTimeCondition ringerModeTimeCondition) {
        mRingerModeRepository.deleteRingerModeTimeCondition(ringerModeTimeCondition);
    }
}