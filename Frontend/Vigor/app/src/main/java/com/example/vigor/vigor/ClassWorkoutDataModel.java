package com.example.vigor.vigor;

public class ClassWorkoutDataModel {
    int classID;
    String classNotes;
    String classDescription;

    public ClassWorkoutDataModel(int classID, String classNotes, String classDescription){
        this.classID = classID;
        this.classNotes = classNotes;
        this.classDescription = classDescription;
    }

    public int getClassID() {
        return classID;
    }

    public String getClassNotes() {
        return classNotes;
    }

    public String getClassDescription() {
        return classDescription;
    }
}
