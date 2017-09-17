package com.example.tt.rasp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by tt on 14.09.17.
 */

public class EdDay {

    private String day;
    private List<Lesson> lessons = new ArrayList<>();

    public void setDay(String day) {
        this.day = day;
    }

    public void putLesson(String lesson, String time, String class_number){
        lessons.add(new Lesson(lesson,time,class_number));
    }

    public String getDay() {
        return day;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    @Override
    public String toString() {
        return day + ":"  + lessons.toString();
    }


}
