package ru.dyadischevma.ringermodes.model;

import androidx.room.TypeConverter;

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