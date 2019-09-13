package com.example.schoolline.Model;

public class Classroom {

    String name;
    String description;


    public Classroom(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Classroom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
