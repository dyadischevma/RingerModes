package ru.dyadischevma.ringermodes.data;

public enum RingerMode {
    SILENT(0),
    VIBRATE(1),
    NORMAL(2);

    public final int value;

    RingerMode(int value) {
        this.value = value;
    }

    public static RingerMode fromInt(int value) {
        for (RingerMode r : RingerMode.values()) {
            if (r.value == value) {
                return r;
            }
        }
        return null;
    }
}
