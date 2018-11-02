package com.example.vigor.vigor;

public class DataModel {

    String Activity;
    String Sets;
    String Reps;

    public DataModel(String Activity, String Sets, String Reps) {
        this.Activity = Activity;
        this.Sets = Sets;
        this.Reps = Reps;
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
}
