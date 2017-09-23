package com.example.tt.rasp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by tt on 14.09.17.
 */
@RealmClass
public class EdDay extends RealmObject {

    @PrimaryKey
    @Required
    private String day;

    private RealmList<Lesson> lessons = new RealmList<>();

    public void setDay(String day) {
        this.day = day;
    }

    public void putLesson(String subject, String time, String class_number){
        lessons.add(new Lesson(subject,time,class_number));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EdDay)) return false;

        EdDay edDay = (EdDay) o;

        if (getDay() != null ? !getDay().equals(edDay.getDay()) : edDay.getDay() != null)
            return false;
        return getLessons() != null ? getLessons().equals(edDay.getLessons()) : edDay.getLessons() == null;

    }

    @Override
    public int hashCode() {
        int result = getDay() != null ? getDay().hashCode() : 0;
        result = 31 * result + (getLessons() != null ? getLessons().hashCode() : 0);
        return result;
    }
}
