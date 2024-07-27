package com.korgun.springcourse.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Person {
    private int user_id;

    @NotEmpty(message = "Name should not be empty")
    private String fullName;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    private int yearOfBirth;

    public Person() {
    }

    public Person(int user_id, String fullName, int yearOfBirth) {
        this.user_id = user_id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
