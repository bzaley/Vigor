package com.example.vigor.vigor;

public class PlanDataModel {

    String PlanName;
    Boolean isChecked;

    //Constructor for object
    public PlanDataModel(String PlanName, boolean isChecked) {
        this.PlanName = PlanName;
        this.isChecked = isChecked;
    }

    //return methods for different aspects
    public String getPlanName() {
        return PlanName;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void check(){
        isChecked = true;
    }

    public void unCheck(){
        isChecked = false;
    }
}
