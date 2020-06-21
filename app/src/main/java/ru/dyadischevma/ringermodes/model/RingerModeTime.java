package ru.dyadischevma.ringermodes.model;

import androidx.room.Entity;

@Entity
public class RingerModeTime {
    int hour;
    int minute;
    int[] days;
}
