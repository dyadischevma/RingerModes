package ru.dyadischevma.ringermodes.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = RingerModeItem.class, parentColumns = "id", childColumns = "ringerModeId"))
public class RingerModeConditions {
    @PrimaryKey(autoGenerate = true)
    int id;
    int ringerModeId;
    int hour;
    int minute;
//    int[] days;
}
