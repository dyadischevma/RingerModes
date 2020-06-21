package ru.dyadischevma.ringermodes.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {
    private DataDAO mDataDao;
    private LiveData<List<RingerModeItem>> mAllData;

    public DataRepository(Application application) {
        DataRoomDbase dataRoombase = DataRoomDbase.getDatabase(application);
        this.mDataDao = dataRoombase.dataDAO();
        this.mAllData = mDataDao.getAllData();
    }

    LiveData<List<RingerModeItem>> getAllData() {
        return mAllData;
    }


    public void insert(RingerModeItem dataItem) {
        new insertAsyncTask(mDataDao).execute(dataItem);
    }

    private static class insertAsyncTask extends AsyncTask<RingerModeItem, Void, Void> {
        private DataDAO mAsyncTaskDao;
        insertAsyncTask(DataDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RingerModeItem... params) {
            mAsyncTaskDao.insertItem(params[0]);
            return null;
        }
    }


    public void deleteItem(RingerModeItem ringerModeItem) {
        new deleteAsyncTask(mDataDao).execute(ringerModeItem);
    }

    private static class deleteAsyncTask extends AsyncTask<RingerModeItem, Void, Void> {
        private DataDAO mAsyncTaskDao;
        deleteAsyncTask(DataDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RingerModeItem... params) {
            mAsyncTaskDao.deleteItem(params[0]);
            return null;
        }
    }


    public void deleteItemById(int idItem) {
        new deleteByIdAsyncTask(mDataDao).execute(idItem);
    }
    // You must call this on a non-UI thread or your app will crash
    private static class deleteByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        private DataDAO mAsyncTaskDao;
        deleteByIdAsyncTask(DataDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteByItemId(params[0]);
            return null;
        }
    }
}