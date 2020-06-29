package ru.dyadischevma.ringermodes.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = RingerModeItem.class, parentColumns = "id", childColumns = "ringerModeId", onDelete = CASCADE))
public class RingerModeTimeCondition implements Comparable<RingerModeTimeCondition> {
    @PrimaryKey(autoGenerate = true)
    long id;
    long ringerModeId;
    int hour;
    int minute;
    String days;

    public RingerModeTimeCondition(long ringerModeId, int hour, int minute) {
        this.ringerModeId = ringerModeId;
        this.hour = hour;
        this.minute = minute;
    }

    @Ignore
    public RingerModeTimeCondition(int hour, int minute, String days) {
        this.hour = hour;
        this.minute = minute;
        this.days = days;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    @Override
    public int compareTo(@NonNull RingerModeTimeCondition o) {
        if (o == this) {
            return 0;
        }
        if (o.hour > this.hour) {
            return -1;
        }
        if (o.hour < this.hour) {
            return 0;
        } else {
            return Integer.compare(this.minute, o.minute);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "RingerModeCondition{" +
                "id=" + id +
                ", ringerModeId=" + ringerModeId +
                ", hour=" + hour +
                ", minute=" + minute +
                ", days='" + days + '\'' +
                '}';
    }

    public int getNearestWeekDay(int day) {
        char[] charDays = days.toCharArray();
        for (char charDay : charDays) {
            if ((charDay - '0') - day > 0) return (charDay - '0');
        }
        return charDays[0] - '0';
    }
}
