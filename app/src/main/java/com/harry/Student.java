package com.harry;

/**
 * Created by Harry on 8/3/15.
 */
public class Student {
    String firstName;
    String lastName;
    String branch;
    int id;

    public Student(String firstName, String lastName, String branch) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.branch = branch;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
