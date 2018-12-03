package com.example.vigor.vigor;

/**
 * @author Adrian H
 * This is the custom object we are using to contain classes when they
 * are created or viewed
 */
public class ClassDataModel {

    int classId;
    String ClassName;
    int instructorId;
    String description;
    String schedule;
    String status;
    String billboard;
    Boolean locked;

    /**
     * This is the constructor for the Object setting all the parameters
     * to their private variables
     * @param classId
     * @param ClassName
     * @param instructorId
     * @param description
     * @param schedule
     * @param status
     * @param billboard
     * @param locked
     */
    public ClassDataModel(int classId, String ClassName, int instructorId, String description, String schedule, String status, String billboard, Boolean locked) {
        this.classId = classId;
        this.ClassName = ClassName;
        this.instructorId = instructorId;
        this.description = description;
        this.schedule = schedule;
        this.status = status;
        this.billboard = billboard;
        this.locked = locked;
    }

    /**
     *
     * @return
     */
    public int getClassId() {
        return classId;
    }

    /**
     *
     * @return
     */
    public String getClassName() {
        return ClassName;
    }

    /**
     *
     * @return
     */
    public int getInstructorId() {
        return instructorId;
    }

    /**
     *
     * @return
     */
    public String getClassDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @return
     */
    public String getBillboard() {
        return billboard;
    }

    /**
     *
     * @return
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     *
     */
    public void unlock(){
        this.locked = false;
    }

    /**
     *
     */
    public void lock(){
        this.locked = true;
    }
}
