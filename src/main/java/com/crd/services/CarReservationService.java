package com.crd.services;

import com.crd.models.BookingDetails;
import com.crd.models.Car;
import com.crd.models.Reservation;

import java.util.*;

/**
 * This is a service class for the CarReservation system.
 */
public class CarReservationService {

    private static volatile CarReservationService instance;
    private static Object lock = new Object();

    private CarReservationService() {
    }

    public static CarReservationService getInstance() {
        CarReservationService result = instance;
        if (result == null) {
            synchronized (lock) {
                result = instance;
                if (result == null)
                    instance = result = new CarReservationService();
            }
        }
        return result;
    }

    /**
     * This method is for booking a single reservation.
     *
     * @param bookingDetails
     * @param car
     * @return
     */
    public Reservation reserveCarHelper(BookingDetails bookingDetails, Car car) {

        String fname = bookingDetails.fname;
        String lname = bookingDetails.lname;
        Date startDate = bookingDetails.startDate;
        Date endDate = bookingDetails.endDate;

        //If startDate is lesser than todays date or startdate is greater or equal to end date.
        if (startDate.compareTo(Calendar.getInstance().getTime()) <= 0 || endDate.compareTo(startDate) <= 0) {
            throw new IllegalArgumentException("The date params are not correct");
        }


        if (isBookingPossible(car, startDate, endDate)) {
            Reservation reservation = addReservation(fname, lname, startDate, endDate, car);
            if (reservation != null) {
                return reservation;
            }
        }
        return null;
    }

    /**
     * Helper for reserving a car for list of non overlaping reservation periods.
     *
     * @param bookingDetailsList
     * @param car
     * @return
     */
    public List<Reservation> reserveCarHelper(List<BookingDetails> bookingDetailsList, Car car) {
        List<Reservation> reservationList = new ArrayList<>();
        for (BookingDetails bookingDetails : bookingDetailsList) {
            boolean canBeBooked = isBookingPossible(car, bookingDetails.startDate, bookingDetails.endDate);
            if (!canBeBooked)
                return reservationList;
        }


        for (BookingDetails bookingDetails : bookingDetailsList) {
            String fname = bookingDetails.fname;
            String lname = bookingDetails.lname;
            Date startDate = bookingDetails.startDate;
            Date endDate = bookingDetails.endDate;
            Reservation reservation = addReservation(fname, lname, startDate, endDate, car);
            reservationList.add(reservation);
        }

        return reservationList;
    }

    /**
     * Helper for returning a car.
     *
     * @param reservation
     * @param carMap
     * @return
     */
    public boolean returnCarHelper(Reservation reservation, final Map<Integer, Car> carMap) {
        if (carMap.containsKey(reservation.carId)) {
            Car car = carMap.get(reservation.carId);
            List<Reservation> reservationList = car.reservationList;
            for (Reservation rev : reservationList) {
                if (rev.reservationNum == reservation.reservationNum) {
                    reservationList.remove(reservation);
                    if (reservationList != null) {
                        Collections.sort(reservationList, this.new SortDate());
                    }
                    return true;
                }
            }
        }

        return false;
    }


    private boolean isBookingPossible(Car car, Date startDate, Date endDate) {

        if (!car.reservationList.isEmpty()) {
            // This list contains booking for the car (non overlapping) in sorted order of start date asending.
            List<Reservation> reservationList = car.reservationList;
            return !hasDateConflict(startDate, endDate, reservationList);
        } else {
            return true;
        }
    }

    private boolean hasDateConflict(Date startDate, Date endDate, List<Reservation> reservationList) {


        Reservation firstRev = reservationList.get(0);
        //Its before the first item in the list or after the last item in the list. In this case both first and last item are the same.
        if (reservationList.size() == 1) {
            if (firstRev.startDate.compareTo(endDate) > 0 || firstRev.endDate.compareTo(startDate) < 0) {
                return false;
            } else {
                return true;
            }
        }

        Reservation lastRev = reservationList.get(reservationList.size() - 1);
        //Its before the first item in the list or after the last item in the list.
        if (firstRev.startDate.compareTo(endDate) > 0 || lastRev.endDate.compareTo(startDate) < 0) {
            return false;
        }


        Date prevEndDate = firstRev.endDate;
        for (int i = 1; i < reservationList.size(); i++) {
            Date currStartDate = reservationList.get(i).startDate;
            if (prevEndDate.compareTo(startDate) < 0 && currStartDate.compareTo(endDate) > 0) {
                return false;
            }
            prevEndDate = reservationList.get(i).endDate;
        }

        return true;
    }

    private Reservation addReservation(String fname, String lname, Date startDate, Date endDate, Car car) {
        List<Reservation> reservationsList = car.reservationList;
        Reservation newReservation = new Reservation(startDate, endDate, fname, lname, car.carId, car.type);
        if (reservationsList.isEmpty()) {
            reservationsList.add(newReservation);
        } else {
            reservationsList.add(newReservation);
            Collections.sort(reservationsList, this.new SortDate());
        }

        return newReservation;
    }

    /**
     * This class is used to sort the List in the map in order of reservation start date.
     */
    class SortDate implements Comparator<Reservation> {

        @Override
        public int compare(Reservation r1, Reservation r2) {
            return r1.startDate.compareTo(r2.startDate);
        }
    }
}

