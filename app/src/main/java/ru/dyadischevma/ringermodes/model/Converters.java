package ru.dyadischevma.ringermodes.model;

import androidx.room.TypeConverter;

import java.util.UUID;

public class Converters {
    @TypeConverter
    public static int ringerModeToInt (RingerMode ringerMode) {
        return ringerMode.value;
    }
    @TypeConverter
    public static RingerMode intToRingerMode (int value) {
        return RingerMode.fromInt(value);
    }
    @TypeConverter
    public static UUID uuidFromString(String uuid) {
        return UUID.fromString(uuid);
    }
    @TypeConverter
    public static String stringFromUUID(UUID uuid) {
        return uuid.toString();
    }
}