package ru.dyadischevma.ringermodes.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface RingerModeDAO {
    //Insert one regime_item
    @Insert(onConflict = IGNORE)
    Single<Long> insertItem(RingerModeItem item);

    @Insert(onConflict = IGNORE)
    Single<Long> insertItem(RingerModeConditions ringerModeConditions);

    // Delete one regime_item
    @Delete
    Completable deleteItem(RingerModeItem item);

    //Delete one regime_item by id
    @Query("DELETE FROM ringermodeitem WHERE id = :itemId")
    Completable deleteByItemId(int itemId);

    //Get all items
    @Query("SELECT * FROM ringermodeitem")
    LiveData<List<RingerModeItem>> getAllData();

    @Query("SELECT * FROM ringermodeitem WHERE id = :itemId")
    Flowable<RingerModeItem> getRingerModeItem(long itemId);

    //Delete All
    @Query("DELETE FROM ringermodeitem")
    Completable deleteAll();


    // Delete one regime_item
    @Delete
    Completable deleteItem(RingerModeConditions ringerModeConditions);

    //Delete one regime_item by id
    @Query("DELETE FROM ringermodeconditions WHERE id = :id")
    Completable deleteConditionByItemId(int id);

    @Query("DELETE FROM ringermodeconditions WHERE ringerModeId = :ringerModeId")
    Completable deleteConditionByRingerModeId(int ringerModeId);

    @Query("SELECT * FROM ringermodeconditions")
    LiveData<List<RingerModeConditions>> getAllConditionData();

    @Query("SELECT * FROM ringermodeconditions WHERE ringerModeId = :ringerModeId")
    LiveData<List<RingerModeConditions>> getConditions(long ringerModeId);

    //Delete All
    @Query("DELETE FROM ringermodeconditions")
    void deleteAllConditions();
}
