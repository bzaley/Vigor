package com.example.vigor.vigor;

/**
 * @author Adrian H
 * This is a custom object type made to contain exercises that the user
 * is currently seeing.
 */
public class DataModel {

    //The aspects of an activity, will be reworked after demo
    String userEmail;
    String planName;
    String exercise;
    String sets;
    String reps;

    /**
     * Constructor for object
     * @param userEmail
     * @param planName
     * @param exercise
     * @param sets
     * @param reps
     */
    public DataModel(String userEmail, String planName, String exercise, String sets, String reps) {
        this.userEmail = userEmail;
        this.planName = planName;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
    }

    /**
     *
     * @return
     */
    public String getuserEmail() {
        return userEmail;
    }

    /**
     *
     * @return
     */
    public String getplanName() {
        return planName;
    }

    /**
     *
     * @return
     */
    public String getexercise() {
        return exercise;
    }

    /**
     *
     * @return
     */
    public String getsets() {
        return sets;
    }

    /**
     *
     * @return
     */
    public String getreps() {
        return reps;
    }

}
