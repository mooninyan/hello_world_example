package com.example.tt.rasp.model;

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
        weekDay.put(MONDAY, "Понедельник");
        weekDay.put(TUESDAY, "Вторник");
        weekDay.put(WEDNESDAY, "Среда");
        weekDay.put(THURSDAY, "Четверг");
        weekDay.put(FRIDAY, "Пятница");
        weekDay.put(SATURDAY, "Суббота");
    }
}
