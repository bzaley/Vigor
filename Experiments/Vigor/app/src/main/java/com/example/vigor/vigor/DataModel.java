package com.example.vigor.vigor;

public class DataModel {

    String Activity;
    String Sets;
    String Reps;
    String AssignedBy;
    String PlanName;

    public DataModel(String Activity, String Sets, String Reps, String AssignedBy, String PlanName) {
        this.Activity = Activity;
        this.Sets = Sets;
        this.Reps = Reps;
        this.AssignedBy = AssignedBy;
        this.PlanName = PlanName;
    }

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
}
