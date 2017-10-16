package com.example.tt.rasp.model;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by tt on 14.09.17.
 */

@RealmClass
public class Lesson implements RealmModel {
    private String subject;
    private String time;
    private String classroom;

    public Lesson(){};

    public Lesson(String name, String time, String classroom) {
        this.subject = name;
        this.time = time;
        this.classroom = classroom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    @Override
    public String toString() {
        return subject + "\n " + time + "\n " + classroom + "\n \n";
    }
}
