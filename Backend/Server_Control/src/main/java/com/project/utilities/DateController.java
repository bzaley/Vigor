package com.project.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 
 * @author Kirkland Keith
 *
 */
public class DateController {

    private Date workingDate = null;
    private Calendar workingCalendarDate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   /**
    * Controller constructor
    */
    public DateController() {
        dateOfToday();
        workingCalendarDate = Calendar.getInstance();
    }
    /**
     * returns the current date
     * @return
     */
    public Date dateOfToday() {
        Date today = Calendar.getInstance().getTime();
        if (workingDate == null) {
            workingDate = today;
        }
        return today;
    }
    /**
     * increments calendar by a day.
     */
    public void addDay() {
        workingCalendarDate.add(Calendar.DATE, 1);
        workingDate = workingCalendarDate.getTime();
    }
    /**
     * decrements calendar one day.
     */
    public void subtractDay() {
        workingCalendarDate.add(Calendar.DATE, -1);
        workingDate = workingCalendarDate.getTime();
    }
    /**
     * Returns string of the working date.
     * @return
     */
    public String returnWorkingDateAsString() {
        return sdf.format(workingDate);
    }
    /**
     * Returns working date as date object.
     * @return
     */
    public Date returnWorkingDate() {
        return workingDate;
    }
    /**
     * sets working date to current day
     */
    public void setWorkingDateToToday() {
        workingCalendarDate = Calendar.getInstance();
        workingDate = workingCalendarDate.getTime();
    }



}

