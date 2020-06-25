package ru.dyadischevma.ringermodes.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface RingerModeDAO {
    /*
    RingerModeItems
     */
    @Query("SELECT * FROM ringermodeitem")
    LiveData<List<RingerModeItem>> getAllRingerModeItems();

    @Query("SELECT * FROM ringermodeitem WHERE id = :itemId")
    Single<RingerModeItem> getRingerModeItem(long itemId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertRingerModeItem(RingerModeItem item);

    @Delete
    Completable deleteItem(RingerModeItem item);

    @Query("DELETE FROM ringermodeitem WHERE id = :itemId")
    Completable deleteByItemId(int itemId);

    /*
    RingerModeCondition
     */
    @Query("SELECT * FROM RingerModeCondition")
    Single<List<RingerModeCondition>> getAllConditionData();

    @Query("SELECT * FROM RingerModeCondition WHERE ringerModeId = :ringerModeId")
    LiveData<List<RingerModeCondition>> getConditions(long ringerModeId);

    @Query("SELECT * FROM RingerModeCondition WHERE (hour > :hour OR (hour = :hour AND minute > :minute )) AND days LIKE :day ORDER BY hour")
    Maybe<RingerModeCondition> getNearlyCondition(int hour, int minute, String day);

    @Query("SELECT * FROM RingerModeCondition WHERE days LIKE :day ORDER BY hour, minute LIMIT 1")
    Maybe<RingerModeCondition> getMinimumTimeCondition(String day);

    @Insert(onConflict = IGNORE)
    Single<Long> insertItem(RingerModeCondition ringerModeConditions);

    @Insert(onConflict = IGNORE)
    Completable insertRingerModeConditionsItems(List<RingerModeCondition> ringerModeConditions);

    @Query("DELETE FROM RingerModeCondition WHERE id = :id")
    Completable deleteConditionByItemId(int id);

    @Delete
    Completable deleteRingerModeConditionItem(RingerModeCondition ringerModeConditions);

    @Query("DELETE FROM RingerModeCondition WHERE ringerModeId = :ringerModeId")
    Completable deleteConditionByRingerModeId(int ringerModeId);
}
