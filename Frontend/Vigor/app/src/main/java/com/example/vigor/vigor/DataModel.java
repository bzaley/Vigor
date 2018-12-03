package com.example.vigor.vigor;

public class DataModel {

    //The aspects of an activity, will be reworked after demo
    String userEmail;
    String planName;
    String exercise;
    String sets;
    String reps;

    //Constructor for object
    //    String userEmail
    //    String planName
    //    String exercise
    //    int sets // Might be changed depending on what user accomplished
    //    int reps
    public DataModel(String userEmail, String planName, String exercise, String sets, String reps) {
        this.userEmail = userEmail;
        this.planName = planName;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
    }

    //return methods for different aspects
    public String getuserEmail() {
        return userEmail;
    }

    public String getplanName() {
        return planName;
    }

    public String getexercise() {
        return exercise;
    }

    public String getsets() {
        return sets;
    }

    public String getreps() {
        return reps;
    }

}
