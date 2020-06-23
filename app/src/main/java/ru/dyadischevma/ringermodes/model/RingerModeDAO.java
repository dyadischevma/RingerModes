package ru.dyadischevma.ringermodes.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface RingerModeDAO {
    //Insert one item
    @Insert(onConflict = IGNORE)
    Single<Long> insertItem(RingerModeItem item);

    @Insert(onConflict = IGNORE)
    Completable insertItem(RingerModeConditions ringerModeConditions);

    // Delete one item
    @Delete
    Completable deleteItem(RingerModeItem item);

    //Delete one item by id
    @Query("DELETE FROM ringermodeitem WHERE id = :itemId")
    Completable deleteByItemId(int itemId);

    //Get all items
    @Query("SELECT * FROM ringermodeitem")
    LiveData<List<RingerModeItem>> getAllData();

    //Delete All
    @Query("DELETE FROM ringermodeitem")
    Completable deleteAll();


    // Delete one item
    @Delete
    Completable deleteItem(RingerModeConditions ringerModeConditions);

    //Delete one item by id
    @Query("DELETE FROM ringermodeconditions WHERE id = :id")
    Completable deleteConditionByItemId(int id);

    @Query("DELETE FROM ringermodeconditions WHERE ringerModeId = :ringerModeId")
    Completable deleteConditionByRingerModeId(int ringerModeId);

    //Get all items
    @Query("SELECT * FROM ringermodeconditions")
    LiveData<List<RingerModeConditions>> getAllConditionData();

    //Delete All
    @Query("DELETE FROM ringermodeconditions")
    void deleteAllConditions();
}
