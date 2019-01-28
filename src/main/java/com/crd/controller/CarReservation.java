package com.crd.controller;

import com.crd.constants.Constants;
import com.crd.models.BookingDetails;
import com.crd.models.Car;
import com.crd.models.Reservation;
import com.crd.services.CarReservationService;

import java.util.*;

/**
 * This is the main class for car reservation.
 */

public class CarReservation {


    //Stores the details about list of small cars
    private final Map<Integer, Car> smallCarsMap;
    //Stores the details about list of medium cars
    private final Map<Integer, Car> mediumCarsMap;
    //Stores the details about list of large cars
    private final Map<Integer, Car> largeCarsMap;
    private final CarReservationService carReservationService;

    /**
     * @param smallCarsList
     * @param mediumCarsList
     * @param largeCarsList
     */
    public CarReservation(List<Car> smallCarsList, List<Car> mediumCarsList, List<Car> largeCarsList) {

        smallCarsMap = initializeMap(smallCarsList);
        mediumCarsMap = initializeMap(mediumCarsList);
        largeCarsMap = initializeMap(largeCarsList);
        carReservationService = CarReservationService.getInstance();
    }

    /**
     * This method initializes the maps with cars.
     *
     * @param carList
     * @return
     */
    private Map<Integer, Car> initializeMap(List<Car> carList) {

        Map<Integer, Car> carMap = new HashMap<Integer, Car>();
        for (Car car : carList) {
            carMap.put(car.carId, car);
        }
        Collections.unmodifiableMap(carMap); //Once its unmodifiable the cars in each map are fixed and cannot be changed.
        return carMap;
    }


    /**
     * This method is used to reserve a car for a customer. It only takes one reservation.
     *
     * @param bookingDetails
     * @return
     */
    public Reservation reserveCar(BookingDetails bookingDetails, Constants.TYPE typeOfCar) {

        if (typeOfCar == Constants.TYPE.SMALL) {
            for (Integer carID : smallCarsMap.keySet()) {
                Car car = smallCarsMap.get(carID);
                Reservation reservation = carReservationService.reserveCarHelper(bookingDetails, car);
                if (reservation != null)
                    return reservation;
            }
        } else if (typeOfCar == Constants.TYPE.MEDIUM) {
            for (Integer carID : mediumCarsMap.keySet()) {
                Car car = mediumCarsMap.get(carID);
                Reservation reservation = carReservationService.reserveCarHelper(bookingDetails, car);
                if (reservation != null)
                    return reservation;
            }
        } else if (typeOfCar == Constants.TYPE.LARGE) {
            for (Integer carID : largeCarsMap.keySet()) {
                Car car = largeCarsMap.get(carID);
                Reservation reservation = carReservationService.reserveCarHelper(bookingDetails, car);
                if (reservation != null)
                    return reservation;
            }
        }
        return null;
    }

    /**
     * This methods books car for multiple non overlapping periods. This assumes that the List of Booking Details passed is valid and non
     * overlapping date ranges. Does not check for that.
     * This method is all or nothing. If any of the date range has a conflict with exiting booking for Car , none of the other dates get booked.
     * It also assumes that all the booking are for the same type of car <SMALL,MEDUIM,or LARGE>
     *
     * @param bookingDetailsList
     * @return
     */
    public List<Reservation> reserveCar(List<BookingDetails> bookingDetailsList, Constants.TYPE typeOfCar) {

        List<Reservation> reservationList = new ArrayList<>();
        if (typeOfCar == Constants.TYPE.SMALL) {
            for (Integer carID : smallCarsMap.keySet()) {
                Car car = smallCarsMap.get(carID);
                reservationList = carReservationService.reserveCarHelper(bookingDetailsList, car);
                if (!reservationList.isEmpty()) {
                    return reservationList;
                }
            }
        } else if (typeOfCar == Constants.TYPE.MEDIUM) {
            for (Integer carID : mediumCarsMap.keySet()) {
                Car car = mediumCarsMap.get(carID);
                reservationList = carReservationService.reserveCarHelper(bookingDetailsList, car);
                if (!reservationList.isEmpty()) {
                    return reservationList;
                }

            }
        } else if (typeOfCar == Constants.TYPE.LARGE) {
            for (Integer carID : largeCarsMap.keySet()) {
                Car car = largeCarsMap.get(carID);
                reservationList = carReservationService.reserveCarHelper(bookingDetailsList, car);
                if (!reservationList.isEmpty()) {
                    return reservationList;
                }
            }
        }
        return reservationList;
    }

    /**
     * This method returns the car.
     *
     * @param reservation
     */
    public boolean returnCar(Reservation reservation) {

        if (reservation.carType == Constants.TYPE.SMALL) {
            return carReservationService.returnCarHelper(reservation, smallCarsMap);
        } else if (reservation.carType == Constants.TYPE.MEDIUM) {
            return carReservationService.returnCarHelper(reservation, mediumCarsMap);
        } else if (reservation.carType == Constants.TYPE.LARGE) {
            return carReservationService.returnCarHelper(reservation, largeCarsMap);
        }
        return false;
    }

}
