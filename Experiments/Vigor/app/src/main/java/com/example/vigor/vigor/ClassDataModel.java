package com.example.vigor.vigor;

public class ClassDataModel {

    int classId;
    String ClassName;
    int instructorId;
    String schedule;
    String status;
    String billboard;
    Boolean locked;

    //Constructor for object
    public ClassDataModel(int classId, String ClassName, int instructorId, String schedule, String status, String billboard, Boolean locked) {
        this.classId = classId;
        this.ClassName = ClassName;
        this.instructorId = instructorId;
        this.schedule = schedule;
        this.status = status;
        this.billboard = billboard;
        this.locked = locked;
    }

    //return methods for different aspects
    public int getClassId() {
        return classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getStatus() {
        return status;
    }

    public String getBillboard() {
        return billboard;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void unlock(){
        this.locked = false;
    }

    public void lock(){
        this.locked = true;
    }

}
