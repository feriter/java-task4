package ru.nsu.ccfit;

import java.util.Calendar;

// Class to get convenient time value
public class MyTime {
    public static long getTimeInMillis() {
        return Calendar.getInstance().getTimeInMillis() % 100000;
    }
}
