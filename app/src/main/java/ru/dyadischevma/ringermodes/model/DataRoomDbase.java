package ru.dyadischevma.ringermodes.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {RingerModeItem.class, RingerModeTimeCondition.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DataRoomDbase extends RoomDatabase {
    private static DataRoomDbase INSTANCE;

    public abstract RingerModeDAO dataDAO();

    public static DataRoomDbase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataRoomDbase.class, DataRoomDbase.class.getName())
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
