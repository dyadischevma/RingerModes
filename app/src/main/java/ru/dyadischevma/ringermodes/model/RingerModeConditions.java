package ru.dyadischevma.ringermodes.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = RingerModeItem.class, parentColumns = "id", childColumns = "ringerModeId", onDelete = CASCADE))
public class RingerModeConditions {
    @PrimaryKey(autoGenerate = true)
    long id;
    long ringerModeId;
    int hour;
    int minute;
//    int[] days;

    public RingerModeConditions(long ringerModeId, int hour, int minute) {
        this.ringerModeId = ringerModeId;
        this.hour = hour;
        this.minute = minute;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRingerModeId() {
        return ringerModeId;
    }

    public void setRingerModeId(long ringerModeId) {
        this.ringerModeId = ringerModeId;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
