package ru.dyadischevma.ringermodes.model.converters;

import androidx.room.TypeConverter;

import ru.dyadischevma.ringermodes.model.entity.RingerMode;

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