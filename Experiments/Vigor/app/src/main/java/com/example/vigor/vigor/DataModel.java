package com.example.vigor.vigor;

public class DataModel {

    String Activity;
    String Amount;
    String AssignedBy;

    public DataModel(String Activity, String Amount, String AssignedBy) {
        this.Activity = Activity;
        this.Amount = Amount;
        this.AssignedBy = AssignedBy;
    }

    public String getActivity() {
        return Activity;
    }

    public String getAmount() {
        return Amount;
    }

    public String getAssignedBy() {
        return AssignedBy;
    }

}
