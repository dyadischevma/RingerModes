package ru.dyadischevma.ringermodes.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class RingerModeItem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private RingerMode ringerMode;
    private int ringerModeVolume;

    public RingerModeItem(long id, String name, RingerMode ringerMode, int ringerModeVolume) {
        this.id = id;
        this.name = name;
        this.ringerMode = ringerMode;
        this.ringerModeVolume = ringerModeVolume;
    }

    @Ignore
    public RingerModeItem(String name, RingerMode ringerMode, int ringerModeVolume) {
        this.name = name;
        this.ringerMode = ringerMode;
        this.ringerModeVolume = ringerModeVolume;
    }

    @Ignore
    public RingerModeItem() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RingerMode getRingerMode() {
        return ringerMode;
    }

    public int getRingerModeVolume() {
        return ringerModeVolume;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRingerMode(RingerMode ringerMode) {
        this.ringerMode = ringerMode;
    }

    public void setRingerModeVolume(int ringerModeVolume) {
        this.ringerModeVolume = ringerModeVolume;
    }

    @NonNull
    @Override
    public String toString() {
        return "RingerModeItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ringerMode=" + ringerMode +
                ", ringerModeValue=" + ringerModeVolume +
                '}';
    }
}
