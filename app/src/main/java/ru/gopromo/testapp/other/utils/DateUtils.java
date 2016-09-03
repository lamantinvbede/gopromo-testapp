package ru.gopromo.testapp.other.utils;


import java.text.DateFormat;
import java.util.Date;

public class DateUtils {

    private static DateFormat format = DateFormat.getDateInstance();

    static {
        //TODO configure format
    }

    public static String getSimpleDateString(Date date) {
        return format.format(date);
    }
}
