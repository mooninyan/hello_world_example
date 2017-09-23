package com.example.tt.rasp;

import java.util.HashMap;

/**
 * Created by tt on 14.09.17.
 */

public final class Constants {
    final static public int MONDAY=2;
    final static public int TUESDAY=3;
    final static public int WEDNESDAY=4;
    final static public int THURSDAY=5;
    final static public int FRIDAY=6;
    final static public int SATURDAY=7;

    public static final HashMap<Integer, String> weekDay = new HashMap<>();

    static {
        weekDay.put(2, "Понедельник");
        weekDay.put(3, "Вторник");
        weekDay.put(4, "Среда");
        weekDay.put(5, "Четверг");
        weekDay.put(6, "Пятница");
        weekDay.put(7, "Суббота");
    }
}
