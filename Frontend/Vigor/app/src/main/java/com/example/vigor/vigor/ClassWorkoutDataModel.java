package com.example.vigor.vigor;

/**
 * Data object made to handle a generic model for all class workouts.
 * Feature required, appropriate getters.
 *
 * @author Kirkland Keith
 */
public class ClassWorkoutDataModel {
    int classID;
    String classNotes;
    String classBillboard;
    String date;

    public ClassWorkoutDataModel(int classID, String classNotes, String classBillboard,
                                 String date){
        this.classID = classID;
        this.classNotes = classNotes;
        this.classBillboard = classBillboard;
        this.date = date;
    }

    public int getClassID() {
        return classID;
    }

    public String getDate() {
        return date;
    }

    public String getClassNotes() {
        return classNotes;
    }

    public String getClassBillBoard() {
        return classBillboard;
    }
}
