package ru.dyadischevma.ringermodes.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface DataDAO {
    //Insert one item
    @Insert(onConflict = IGNORE)
    void insertItem(RingerModeItem item);

    // Delete one item
    @Delete
    void deleteItem(RingerModeItem item);

    //Delete one item by id
    @Query("DELETE FROM ringermodeitem WHERE id = :itemId")
    void deleteByItemId(int itemId);

    //Get all items
    @Query("SELECT * FROM ringermodeitem")
    LiveData<List<RingerModeItem>> getAllData();

    //Delete All
    @Query("DELETE FROM ringermodeitem")
    void deleteAll();
}
