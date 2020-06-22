package ru.dyadischevma.ringermodes.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public int insertItem(RingerModeItem ringerModeItem) {
        try {
            return mRingerModeRepository.insert(ringerModeItem);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteItem(RingerModeItem ringerModeItem) {
        mRingerModeRepository.deleteItem(ringerModeItem);
    }

    public void deleteItemById(int idItem) {
        mRingerModeRepository.deleteItemById(idItem);
    }
}