package ru.dyadischevma.ringermodes.presenter;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ru.dyadischevma.ringermodes.helpers.Helper;
import ru.dyadischevma.ringermodes.model.entity.RingerModeItem;
import ru.dyadischevma.ringermodes.model.repository.RingerModeRepository;
import ru.dyadischevma.ringermodes.view.MainActivity;
import ru.dyadischevma.ringermodes.view.RegimeActivity;

public class MainPresenter implements Presenter {
    private RingerModeRepository mRingerModeRepository;
    private CompositeDisposable mCompositeDisposable;
    private MainActivity mainActivity;
    private Context mContext;

    public MainPresenter(Application application) {
        mCompositeDisposable = new CompositeDisposable();
        mRingerModeRepository = new RingerModeRepository(application);
        mContext = application.getApplicationContext();
    }

    @Override
    public void attachView(Activity activity) {
        mainActivity = (MainActivity) activity;
    }

    @Override
    public void viewIsReady() {
        Disposable disposable = mRingerModeRepository.getAllRingerModes()
                .subscribe(ringerModeItemList -> {
                    if (ringerModeItemList != null) {
                        mainActivity.setListData(ringerModeItemList);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void checkGrants() {
        NotificationManager notificationManager =
                (NotificationManager) mainActivity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent =
                    new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            mainActivity.startActivityForResult(intent, 0);
        }
    }

    public void setAlarm() {
        Helper.setAlarm(mRingerModeRepository, mContext, mCompositeDisposable);
    }

    public void startCreatingRegime() {
        Intent intent = new Intent(mainActivity, RegimeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        mainActivity.startActivity(intent);
    }

    public void deleteRingerMode(RingerModeItem ringerModeItem) {
        mRingerModeRepository.deleteRingerMode(ringerModeItem);
    }

    @Override
    public void detachView() {
        mCompositeDisposable.dispose();
        mainActivity = null;
    }
}
