package ru.dyadischevma.ringermodes.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DataViewModel extends AndroidViewModel {

    private DataRepository mDataRepository;
    private LiveData<List<RingerModeItem>> mListLiveData;

    public DataViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = new DataRepository((application));
        mListLiveData = mDataRepository.getAllData();
    }

    public LiveData<List<RingerModeItem>> getAllData() {
        return mListLiveData;
    }

    public void insertItem(RingerModeItem ringerModeItem) {
        mDataRepository.insert(ringerModeItem);
    }

    public void deleteItem(RingerModeItem ringerModeItem) {
        mDataRepository.deleteItem(ringerModeItem);
    }

    public void deleteItemById(int idItem) {
        mDataRepository.deleteItemById(idItem);
    }
}
