package ru.dyadischevma.ringermodes.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RingerModeItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private RingerMode ringerMode;
    private int ringerModeValue;

    public RingerModeItem(int id, String name, RingerMode ringerMode, int ringerModeValue) {
        this.id = id;
        this.name = name;
        this.ringerMode = ringerMode;
        this.ringerModeValue = ringerModeValue;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RingerMode getRingerMode() {
        return ringerMode;
    }

    public int getRingerModeValue() {
        return ringerModeValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRingerMode(RingerMode ringerMode) {
        this.ringerMode = ringerMode;
    }

    public void setRingerModeValue(int ringerModeValue) {
        this.ringerModeValue = ringerModeValue;
    }
}
