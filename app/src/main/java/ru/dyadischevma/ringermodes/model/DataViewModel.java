package ru.dyadischevma.ringermodes.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class DataViewModel extends AndroidViewModel {

    private RingerModeRepository mRingerModeRepository;
    private LiveData<List<RingerModeItem>> mRingerModeItemsListLiveData;

    public DataViewModel(@NonNull Application application) {
        super(application);
        mRingerModeRepository = new RingerModeRepository((application));
        mRingerModeItemsListLiveData = mRingerModeRepository.getAllRingerModeItems();
    }

    public LiveData<List<RingerModeItem>> getAllRingerModeItems() {
        return mRingerModeItemsListLiveData;
    }

    public Flowable<RingerModeItem> getRingerModeItem(long id) {
        return mRingerModeRepository.getRingerModeItem(id);
    }

    public LiveData<List<RingerModeCondition>> getAllConditions(){
        return mRingerModeRepository.getAllConditions();
    }

    public LiveData<List<RingerModeCondition>> getConditions(long ringerModeId){
        return mRingerModeRepository.getConditions(ringerModeId);
    }

    public Single<Long> insertItem(RingerModeItem ringerModeItem) {
        return mRingerModeRepository.insert(ringerModeItem);
    }

    public void insertItem(RingerModeCondition ringerModeConditions) {
        mRingerModeRepository.insert(ringerModeConditions);
    }

    public void insertRingerModeConditionsItems(List<RingerModeCondition> ringerModeConditionArrayList){
       mRingerModeRepository.insertRingerModeConditionsItems(ringerModeConditionArrayList);
    }

    public void deleteItem(RingerModeItem ringerModeItem) {
        mRingerModeRepository.deleteItem(ringerModeItem);
    }

    public void deleteItemById(int idItem) {
        mRingerModeRepository.deleteItemById(idItem);
    }
}