package com.example.vigor.vigor;

public class DataModel {

    String Activity;
    String Sets;
    String Reps;
    String AssignedBy;

    public DataModel(String Activity, String Sets, String Reps, String AssignedBy) {
        this.Activity = Activity;
        this.Sets = Sets;
        this.Reps = Reps;
        this.AssignedBy = AssignedBy;
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
}
