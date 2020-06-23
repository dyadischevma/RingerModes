package ru.dyadischevma.ringermodes.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RingerModeRepository {
    private RingerModeDAO mRingerModeDao;
    private LiveData<List<RingerModeItem>> mRingerModeItems;

    public RingerModeRepository(Application application) {
        DataRoomDbase dataRoombase = DataRoomDbase.getDatabase(application);
        this.mRingerModeDao = dataRoombase.dataDAO();
        this.mRingerModeItems = mRingerModeDao.getAllRingerModeItems();
    }

    LiveData<List<RingerModeItem>> getAllRingerModeItems() {
        return mRingerModeItems;
    }

    Flowable<RingerModeItem> getRingerModeItem(long id) {
        return mRingerModeDao.getRingerModeItem(id)
                .observeOn(AndroidSchedulers.mainThread());
    }

    LiveData<List<RingerModeCondition>> getConditions(long ringerModeId) {
        return mRingerModeDao.getConditions(ringerModeId);
    }

    LiveData<List<RingerModeCondition>> getAllConditions() {
        return mRingerModeDao.getAllConditionData();
    }

    public Single<Long> insert(RingerModeItem dataItem) {
        return mRingerModeDao.insertRingerModeItem(dataItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insert(RingerModeCondition ringerModeConditions) {
        Disposable result = mRingerModeDao.insertItem(ringerModeConditions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> Log.d("Regime", "saved condition " + l));
    }

    public void insertRingerModeConditionsItems(List<RingerModeCondition> ringerModeConditionList) {
        mRingerModeDao.insertRingerModeConditionsItems(ringerModeConditionList)
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