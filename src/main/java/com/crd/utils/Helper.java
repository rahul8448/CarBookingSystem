package com.crd.utils;

public class Helper {

    private static int customerId;
    private static int reservationId;
    private static int carId;

    private static volatile Helper instance;
    private static Object lock = new Object();
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();
    private static Object lock3 = new Object();


    private Helper() {
    }

    public static Helper getInstance() {
        Helper result = instance;
        if (result == null) {
            synchronized (lock) {
                result = instance;
                customerId = 1;
                reservationId = 1;
                carId = 1;
                if (result == null)
                    instance = result = new Helper();
            }
        }
        return result;
    }

    public int generateNextCustomerId() {

        synchronized (lock1) {
            int nextVal = customerId;
            customerId++;
            return nextVal;
        }
    }

    public int generateNextReservationNum() {

        synchronized (lock2) {
            int nextVal = reservationId;
            reservationId++;
            return nextVal;
        }
    }

    public int generateNextCarId() {

        synchronized (lock3) {
            int nextVal = carId;
            carId++;
            return nextVal;
        }
    }
}
