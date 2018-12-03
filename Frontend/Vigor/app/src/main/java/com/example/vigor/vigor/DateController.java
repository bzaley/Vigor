package com.example.vigor.vigor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Controller to efficiently manage and process dates variables across the several activities and
 * processes of the app.
 *
 * @author Kirkland Keith
 */
public class DateController {

    private Date workingDate = null;
    private Calendar workingCalendarDate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Method to initialize the date controller, setting the current date as the active, working
     * date
     */
    public DateController() {
        dateOfToday();
        workingCalendarDate = Calendar.getInstance();
        workingDate = workingCalendarDate.getTime();
    }

    /**
     * Method to reinitialize the date controller to the current date while also returning that date
     * @return Today's date
     */
    public Date dateOfToday() {
        Date today = Calendar.getInstance().getTime();
        if (workingDate == null) {
            workingDate = today;
        }
        return today;
    }

    /**
     * Adds a single day to the current working date. (e.g. If the current working date is today,
     * addDay changes the working date to tomorrow.
     */
    public void addDay() {
        workingCalendarDate.add(Calendar.DATE, 1);
        workingDate = workingCalendarDate.getTime();
    }

    /**
     * Subtracts a single day to the current working date. (e.g. If the current working date is
     * today subtractDay changes the working date to tomorrow.
     */
    public void subtractDay() {
        workingCalendarDate.add(Calendar.DATE, -1);
        workingDate = workingCalendarDate.getTime();
    }

    /**
     * Getter method to return the current working date
     * @return String representing current working date associated with the controller object
     */
    public String returnWorkingDateAsString() {
        return sdf.format(workingDate);
    }

    /**
     * Getter method to return the current working date
     * @return Date object representing current working date associated with the controller object
     */
    public Date returnWorkingDate() {
        return workingDate;
    }

    /**
     * Method to force the current working date to today's date
     */
    public void setWorkingDateToToday() {
        workingCalendarDate = Calendar.getInstance();
        workingDate = workingCalendarDate.getTime();
    }



}
