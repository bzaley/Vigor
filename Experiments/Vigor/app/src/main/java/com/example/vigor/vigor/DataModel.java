package com.example.vigor.vigor;

public class DataModel {

    //The aspects of an activity, will be reworked after demo
    String Activity;
    String Sets;
    String Reps;
    String AssignedBy;
    String PlanName;
    String TrainerId;

    //Constructor for object
    public DataModel(String Activity, String Sets, String Reps, String AssignedBy, String PlanName, String TrainerId) {
        this.Activity = Activity;
        this.Sets = Sets;
        this.Reps = Reps;
        this.AssignedBy = AssignedBy;
        this.PlanName = PlanName;
        this.TrainerId = TrainerId;
    }

    //return methods for different aspects
    public String getActivity() {
        return Activity;
    }

    public String getSets() {
        return Sets;
    }

    public String getReps() {
        return Reps;
    }

    public String getAssignedBy() {
        return AssignedBy;
    }

    public String getPlanName() {
        return PlanName;
    }

    public String getTrainerId() {
        return TrainerId;
    }
}
