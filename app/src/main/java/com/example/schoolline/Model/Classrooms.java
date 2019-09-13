package com.example.schoolline.Model;

public class Classrooms {

    String name;
    String description;
    String id;
    String teacher01;
    String teacher02;

    public String getGrade() {
        return grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    String grade;


    public Classrooms() {
    }

    public Classrooms(String name, String description, String teacher01, String teacher02) {
        this.name = name;
        this.description = description;
        this.teacher01 = teacher01;
        this.teacher02 = teacher02;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacher01() {
        return teacher01;
    }

    public void setTeacher01(String teacher01) {
        this.teacher01 = teacher01;
    }

    public String getTeacher02() {
        return teacher02;
    }

    public void setTeacher02(String teacher02) {
        this.teacher02 = teacher02;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
