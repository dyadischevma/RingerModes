package ru.dyadischevma.ringermodes.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class DataViewModel extends AndroidViewModel {

    private RingerModeRepository mRingerModeRepository;
    private LiveData<List<RingerModeItem>> mListLiveData;

    public DataViewModel(@NonNull Application application) {
        super(application);
        mRingerModeRepository = new RingerModeRepository((application));
        mListLiveData = mRingerModeRepository.getAllData();
    }

    public LiveData<List<RingerModeItem>> getAllData() {
        return mListLiveData;
    }

    public Flowable<RingerModeItem> getRingerModeItem(long id) {
        return mRingerModeRepository.getRingerModeItem(id);
    }

    public LiveData<List<RingerModeConditions>> getAllConditions(){
        return mRingerModeRepository.getAllConditions();
    }

    public LiveData<List<RingerModeConditions>> getConditions(long ringerModeId){
        return mRingerModeRepository.getConditions(ringerModeId);
    }

    public Single<Long> insertItem(RingerModeItem ringerModeItem) {
        return mRingerModeRepository.insert(ringerModeItem);
    }

    public void insertItem(RingerModeConditions ringerModeConditions) {
        mRingerModeRepository.insert(ringerModeConditions);
    }

    public void deleteItem(RingerModeItem ringerModeItem) {
        mRingerModeRepository.deleteItem(ringerModeItem);
    }

    public void deleteItemById(int idItem) {
        mRingerModeRepository.deleteItemById(idItem);
    }
}