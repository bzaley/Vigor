package com.example.vigor.vigor;

public class ActivityStore {
    private String Activity;
    private int Amount;
    private String AssignedBy;

    public ActivityStore(String activity, int amount, String assignedBy) {
        this.Activity = activity;
        this.Amount = amount;
        this.AssignedBy = assignedBy;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        this.Amount = amount;
    }

    public String getAssignedBy() {
        return AssignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        AssignedBy = assignedBy;
    }
}
