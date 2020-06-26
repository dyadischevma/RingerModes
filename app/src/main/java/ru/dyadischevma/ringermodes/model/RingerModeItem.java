package ru.dyadischevma.ringermodes.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class RingerModeItem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private UUID uuid;
    private String name;
    private RingerMode ringerMode;
    private int ringerModeVolume;

    public RingerModeItem(long id, String name, RingerMode ringerMode, int ringerModeVolume) {
        this.id = id;
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.ringerMode = ringerMode;
        this.ringerModeVolume = ringerModeVolume;
    }

    @Ignore
    public RingerModeItem(String name, RingerMode ringerMode, int ringerModeVolume) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.ringerMode = ringerMode;
        this.ringerModeVolume = ringerModeVolume;
    }

    @Ignore
    public RingerModeItem() {
        this.uuid = UUID.randomUUID();
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @NonNull
    @Override
    public String toString() {
        return "RingerModeItem{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", ringerMode=" + ringerMode +
                ", ringerModeValue=" + ringerModeVolume +
                '}';
    }
}
