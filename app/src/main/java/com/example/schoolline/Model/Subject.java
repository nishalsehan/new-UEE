package com.example.schoolline.Model;

public class Subject {

    private String name;
    private String grade;
    private String classroom;

    public Subject(String name, String grade, String classroom) {
        this.name = name;
        this.grade = grade;
        this.classroom = classroom;
    }

    public Subject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
