package com.example.vigor.vigor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateController {

    Date workingDate = null;
    Calendar workingCalendarDate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DateController() {
        dateOfToday();
        workingCalendarDate = Calendar.getInstance();
    }

    public Date dateOfToday() {
        Date today = Calendar.getInstance().getTime();
        if (workingDate == null) {
            workingDate = today;
        }
        return today;
    }

    public void addDay() {
        workingCalendarDate.add(Calendar.DATE, 1);
        workingDate = workingCalendarDate.getTime();
    }

    public void subtractDay() {
        workingCalendarDate.add(Calendar.DATE, -1);
        workingDate = workingCalendarDate.getTime();
    }

    public String returnWorkingDateAsString() {
        return sdf.format(workingDate);
    }

    public Date returnWorkingDate() {
        return workingDate;
    }

    public void setWorkingDateToToday() {
        workingCalendarDate = Calendar.getInstance();
        workingDate = workingCalendarDate.getTime();
    }



}
