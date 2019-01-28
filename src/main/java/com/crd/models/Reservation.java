package com.crd.models;

import com.crd.constants.Constants;
import com.crd.utils.Helper;

import java.util.Date;


/**
 * This class represent each reservation. For simplcity the Date represents one full Day. For example if the reservation is between
 * 12th March 2019 and 15th March 2019 , it starts from 12.00am 12th March and ends at  (not inclusive)12.00 am 16th March 2019. Time
 * is not takes for this exercise. i.e reservation like 12th MArch 2019 10.45 am etc.
 */
public class Reservation {

    public final int reservationNum;
    public final Date startDate;
    public final Date endDate;
    public final Customer customer;
    public final int carId;
    public final Constants.TYPE carType;

    public Reservation(Date startDate, Date endDate, String fname, String lname, int carId, Constants.TYPE carType) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationNum = Helper.getInstance().generateNextReservationNum();
        this.customer = new Customer(fname, lname);
        this.carId = carId;
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationNum=" + reservationNum +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", customer=" + customer +
                ", carId=" + carId +
                ", carType=" + carType +
                '}';
    }
}
