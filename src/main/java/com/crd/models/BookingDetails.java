package com.crd.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the booking details POJO
 */
public class BookingDetails {

    public Date startDate;
    public Date endDate;
    public String fname;
    public String lname;

    public BookingDetails(String startDate, String endDate, String fname, String lname) {
        try {
            this.startDate = new SimpleDateFormat("MM/dd/yyyy").parse(startDate);
            this.endDate = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.fname = fname;
        this.lname = lname;
    }
}
