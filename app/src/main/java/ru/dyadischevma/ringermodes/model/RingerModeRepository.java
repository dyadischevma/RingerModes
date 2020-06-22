package ru.dyadischevma.ringermodes.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

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


    public int insert(RingerModeItem dataItem) throws ExecutionException, InterruptedException {
       return new insertAsyncTask(mRingerModeDao).execute(dataItem).get();
    }

    private static class insertAsyncTask extends AsyncTask<RingerModeItem, Void, Integer> {
        private RingerModeDAO mAsyncTaskDao;
        insertAsyncTask(RingerModeDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final RingerModeItem... params) {
            return mAsyncTaskDao.insertItem(params[0]);
        }
    }


    public void deleteItem(RingerModeItem ringerModeItem) {
        new deleteAsyncTask(mRingerModeDao).execute(ringerModeItem);
    }

    private static class deleteAsyncTask extends AsyncTask<RingerModeItem, Void, Void> {
        private RingerModeDAO mAsyncTaskDao;
        deleteAsyncTask(RingerModeDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RingerModeItem... params) {
            mAsyncTaskDao.deleteItem(params[0]);
            return null;
        }
    }


    public void deleteItemById(int idItem) {
        new deleteByIdAsyncTask(mRingerModeDao).execute(idItem);
    }
    // You must call this on a non-UI thread or your app will crash
    private static class deleteByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        private RingerModeDAO mAsyncTaskDao;
        deleteByIdAsyncTask(RingerModeDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteByItemId(params[0]);
            return null;
        }
    }
}