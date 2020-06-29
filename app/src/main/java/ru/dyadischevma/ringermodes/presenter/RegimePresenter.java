package ru.dyadischevma.ringermodes.presenter;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.dyadischevma.ringermodes.model.entity.RingerMode;
import ru.dyadischevma.ringermodes.model.entity.RingerModeItem;
import ru.dyadischevma.ringermodes.model.repository.RingerModeRepository;
import ru.dyadischevma.ringermodes.model.entity.RingerModeTimeCondition;
import ru.dyadischevma.ringermodes.view.RegimeActivity;


public class RegimePresenter implements Presenter {
    private List<RingerModeTimeCondition> mRingerModeTimeConditionsList = new ArrayList<>();
    private RingerModeRepository mRingerModeRepository;
    private RingerModeItem mRingerModeItem;
    private RingerMode mRingerMode = RingerMode.NORMAL;
    private long mRingerModeId;
    private CompositeDisposable mCompositeDisposable;

    private RegimeActivity mRegimeActivity;

    public RegimePresenter(Application application, long ringerModeId) {
        mCompositeDisposable = new CompositeDisposable();
        mRingerModeRepository = new RingerModeRepository(application);
        this.mRingerModeId = ringerModeId;
    }

    @Override
    public void attachView(Activity activity) {
        mRegimeActivity = (RegimeActivity) activity;
    }

    @Override
    public void viewIsReady() {
        if (mRingerModeId != -1) {
            Disposable conditionsDisposable = mRingerModeRepository.getTimeConditions(mRingerModeId)
                    .subscribe(ringerModeConditionsList -> {
                        if (ringerModeConditionsList != null) {
                            mRingerModeTimeConditionsList = ringerModeConditionsList;
                            mRegimeActivity.setConditionsListData(mRingerModeTimeConditionsList);
                        }
                    });
            mCompositeDisposable.add(conditionsDisposable);
            Disposable disposable = mRingerModeRepository.getRingerMode(mRingerModeId)
                    .subscribe(ringerModeItem -> {
                                mRingerModeItem = ringerModeItem;
                                mRegimeActivity.setRingerMode(ringerModeItem);
                            }
                    );
            mCompositeDisposable.add(disposable);
        } else {
            mRingerModeItem = mRegimeActivity.getRingerModeItem();
        }
    }

    public int getVolume() {
        return mRingerModeItem.getRingerModeVolume();
    }

    public void setVolume(int volume) {
        mRingerModeItem.setRingerModeVolume(volume);
    }

    public void saveRegime() {
        mRingerModeItem = mRegimeActivity.getRingerModeItem();
        if (mRingerModeId > -1) {
            mRingerModeItem.setId(mRingerModeId);
        }
        Disposable insertRingerMode = mRingerModeRepository.insertRingerMode(mRingerModeItem)
                .subscribe(l -> {
                    for (RingerModeTimeCondition rmc : mRingerModeTimeConditionsList) {
                        rmc.setRingerModeId(l);
                    }
                    mRingerModeRepository.insertRingerModeTimeConditions(mRingerModeTimeConditionsList);
                });
        mCompositeDisposable.add(insertRingerMode);
        mRegimeActivity.finish();
    }

    public void deleteRingerModeTimeCondition(RingerModeTimeCondition ringerModeTimeCondition) {
        mRingerModeRepository.deleteRingerModeTimeCondition(ringerModeTimeCondition);
    }

    public void addToRingerModeTimeConditionsList(RingerModeTimeCondition ringerModeTimeCondition) {
        mRingerModeTimeConditionsList.add(ringerModeTimeCondition);
    }

    public List<RingerModeTimeCondition> getRingerModeTimeConditionsList() {
        return mRingerModeTimeConditionsList;
    }

    @Override
    public void detachView() {
        mRegimeActivity = null;
        mCompositeDisposable.dispose();
    }
}

