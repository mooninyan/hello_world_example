package com.example.tt.rasp;

/**
 * Created by tt on 14.09.17.
 */

class Lesson {
    String name;
    String time;
    String classNumber;

    public Lesson(String name, String time, String classNumber) {
        this.name = name;
        this.time = time;
        this.classNumber = classNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    @Override
    public String toString() {
        return name + "\n " + time + "\n " + classNumber + "\n \n";
    }
}
