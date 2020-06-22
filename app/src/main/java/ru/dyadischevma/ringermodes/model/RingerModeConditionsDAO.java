package ru.dyadischevma.ringermodes.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface RingerModeConditionsDAO {

    @Insert(onConflict = IGNORE)
    void insertItem(RingerModeConditions ringerModeConditions);

    // Delete one item
    @Delete
    void deleteItem(RingerModeConditions ringerModeConditions);

    //Delete one item by id
    @Query("DELETE FROM ringermodeconditions WHERE id = :id")
    void deleteByItemId(int id);

    @Query("DELETE FROM ringermodeconditions WHERE ringerModeId = :ringerModeId")
    void deleteByRingerModeId(int ringerModeId);

    //Get all items
    @Query("SELECT * FROM ringermodeconditions")
    LiveData<List<RingerModeConditions>> getAllData();

    //Delete All
    @Query("DELETE FROM ringermodeconditions")
    void deleteAll();
}
