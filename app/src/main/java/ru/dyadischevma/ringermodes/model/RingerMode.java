package ru.dyadischevma.ringermodes.model;

public enum RingerMode {
    SILENT(0),
    VIBRATE(1),
    NORMAL(2);

    public final int value;

    RingerMode(int value) {
        this.value = value;
    }
}
