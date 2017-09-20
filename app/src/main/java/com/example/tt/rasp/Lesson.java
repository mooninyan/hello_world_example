package com.example.tt.rasp;

/**
 * Created by tt on 14.09.17.
 */

class Lesson {
    String subject;
    String time;
    String classroom;

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
