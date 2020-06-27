package ru.dyadischevma.ringermodes.model;

import androidx.room.TypeConverter;

import ru.dyadischevma.ringermodes.data.RingerMode;

public class Converters {
    @TypeConverter
    public static int ringerModeToInt (RingerMode ringerMode) {
        return ringerMode.value;
    }
    @TypeConverter
    public static RingerMode intToRingerMode (int value) {
        return RingerMode.fromInt(value);
    }
}