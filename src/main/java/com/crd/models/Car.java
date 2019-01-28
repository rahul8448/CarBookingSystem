package com.crd.models;

import com.crd.constants.Constants;
import com.crd.utils.Helper;

import java.util.ArrayList;
import java.util.List;


/**
 * This car represents a Car. For this exercise we do not consider other charactertics of car like color make ,
 * whether the car is a luxury car etc.
 */
public class Car {

    //Unique id of the car.
    public final int carId;

    //The type of the car.
    public final Constants.TYPE type;

    public final List<Reservation> reservationList;


    public Car(Constants.TYPE type) {
        this.carId = Helper.getInstance().generateNextCarId();
        this.type = type;
        this.reservationList = new ArrayList<Reservation>();
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", type=" + type +
                ", reservationList=" + reservationList +
                '}';
    }

}
