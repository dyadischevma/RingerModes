package ru.dyadischevma.ringermodes.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RingerModeRepository {
    private RingerModeDAO mRingerModeDao;

    public RingerModeRepository(Application application) {
        DataRoomDbase dataRoomDbase = DataRoomDbase.getDatabase(application);
        this.mRingerModeDao = dataRoomDbase.dataDAO();
    }

    /*
    RingerModeItems
    */
    public Flowable<List<RingerModeItem>> getAllRingerModes() {
        return mRingerModeDao.getAllRingerModeItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<RingerModeItem> getRingerMode(long id) {
        return mRingerModeDao.getRingerMode(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> insertRingerMode(RingerModeItem dataItem) {
        return mRingerModeDao.insertRingerMode(dataItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void deleteRingerMode(RingerModeItem ringerModeItem) {
        mRingerModeDao.deleteRingerMode(ringerModeItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    /*
    RingerModeTimeCondition
    */
    public Single<List<RingerModeTimeCondition>> getAllTimeConditions() {
        return mRingerModeDao.getAllTimeConditions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public LiveData<List<RingerModeTimeCondition>> getTimeConditions(long ringerModeId) {
        return mRingerModeDao.getTimeConditions(ringerModeId);
    }

    public Maybe<RingerModeTimeCondition> getNearestTimeCondition(int hour, int minutes, int day) {
        return mRingerModeDao.getNearestTimeCondition(hour, minutes, "%" + day + "%")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<RingerModeTimeCondition> getMinimumTimeCondition(int day) {
        return mRingerModeDao.getMinimumTimeCondition("%" + day + "%")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insertRingerModeTimeConditions(List<RingerModeTimeCondition> ringerModeTimeConditionList) {
        mRingerModeDao.insertRingerModeTimeConditions(ringerModeTimeConditionList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void deleteRingerModeTimeCondition(RingerModeTimeCondition ringerModeTimeCondition) {
        mRingerModeDao.deleteRingerModeTimeCondition(ringerModeTimeCondition)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}