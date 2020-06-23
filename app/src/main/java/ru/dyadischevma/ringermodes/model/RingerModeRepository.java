package ru.dyadischevma.ringermodes.model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RingerModeRepository {
    private RingerModeDAO mRingerModeDao;
    private LiveData<List<RingerModeItem>> mAllData;

    public RingerModeRepository(Application application) {
        DataRoomDbase dataRoombase = DataRoomDbase.getDatabase(application);
        this.mRingerModeDao = dataRoombase.dataDAO();
        this.mAllData = mRingerModeDao.getAllData();
    }

    LiveData<List<RingerModeItem>> getAllData() {
        return mAllData;
    }

    public Single<Long> insert(RingerModeItem dataItem) {
        return mRingerModeDao.insertItem(dataItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insert(RingerModeConditions ringerModeConditions) {
        mRingerModeDao.insertItem(ringerModeConditions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void deleteItem(RingerModeItem ringerModeItem) {
        mRingerModeDao.deleteItem(ringerModeItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void deleteItemById(int idItem) {
        mRingerModeDao.deleteByItemId(idItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}