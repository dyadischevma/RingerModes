package ru.dyadischevma.ringermodes.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import ru.dyadischevma.ringermodes.model.entity.RingerModeItem;
import ru.dyadischevma.ringermodes.model.entity.RingerModeTimeCondition;

@Dao
public interface RingerModeDAO {
    /*
    RingerModeItems
     */
    @Query("SELECT * FROM ringermodeitem")
    Flowable<List<RingerModeItem>> getAllRingerModeItems();

    @Query("SELECT * FROM ringermodeitem WHERE id = :itemId")
    Single<RingerModeItem> getRingerMode(long itemId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertRingerMode(RingerModeItem item);

    @Delete
    Completable deleteRingerMode(RingerModeItem item);

    /*
    RingerModeCondition
     */
    @Query("SELECT * FROM RingerModeTimeCondition")
    Single<List<RingerModeTimeCondition>> getAllTimeConditions();

    @Query("SELECT * FROM RingerModeTimeCondition WHERE ringerModeId = :ringerModeId")
    Flowable<List<RingerModeTimeCondition>> getTimeConditions(long ringerModeId);

    @Query("SELECT * FROM RingerModeTimeCondition WHERE (hour > :hour OR (hour = :hour AND minute > :minute )) AND days LIKE :day ORDER BY hour")
    Maybe<RingerModeTimeCondition> getNearestTimeCondition(int hour, int minute, String day);

    @Query("SELECT * FROM RingerModeTimeCondition WHERE days LIKE :day ORDER BY hour, minute LIMIT 1")
    Maybe<RingerModeTimeCondition> getMinimumTimeCondition(String day);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRingerModeTimeConditions(List<RingerModeTimeCondition> ringerModeTimeConditions);

    @Delete
    Completable deleteRingerModeTimeCondition(RingerModeTimeCondition ringerModeTimeConditions);
}
