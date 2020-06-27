package ru.dyadischevma.ringermodes.presenter;

import android.app.Activity;

public interface Presenter {
    void attachView(Activity activity);
    void viewIsReady();
    void detachView();
}
