package com.example.vigor.vigor;

public class DataModel {

    //The aspects of an activity, will be reworked after demo
    String TrainerId;
    String Email;
    String Exercise;
    String Sets;
    String Reps;
    String PlanName;

    //Constructor for object
    public DataModel(String TrainerId, String Email, String Exercise, String Sets, String Reps, String PlanName) {
        this.TrainerId = TrainerId;
        this.Email = Email;
        this.Exercise = Exercise;
        this.Sets = Sets;
        this.Reps = Reps;
        this.PlanName = PlanName;
    }

    //return methods for different aspects
    public String getTrainerId() {
        return TrainerId;
    }

    public String getEmail() {
        return Email;
    }

    public String getExercise() {
        return Exercise;
    }

    public String getSets() {
        return Sets;
    }

    public String getReps() {
        return Reps;
    }

    public String getPlanName() {
        return PlanName;
    }

}
