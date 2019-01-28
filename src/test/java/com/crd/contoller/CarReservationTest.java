package com.crd.contoller;

import com.crd.constants.Constants;
import com.crd.controller.CarReservation;
import com.crd.models.BookingDetails;
import com.crd.models.Car;
import com.crd.models.Reservation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CarReservationTest {

    private CarReservation carReservation = null;

    @Before
    public void setUp() throws Exception {

        List<Car> smallCarsList = new ArrayList<Car>();
        Car c1 = new Car(Constants.TYPE.SMALL);
        Car c2 = new Car(Constants.TYPE.SMALL);
        Car c3 = new Car(Constants.TYPE.SMALL);
        smallCarsList.add(c1);

        List<Car> mediumCarsList = new ArrayList<Car>();
        Car c4 = new Car(Constants.TYPE.MEDIUM);
        Car c5 = new Car(Constants.TYPE.MEDIUM);
        Car c6 = new Car(Constants.TYPE.MEDIUM);
        mediumCarsList.add(c4);
        mediumCarsList.add(c5);
        mediumCarsList.add(c6);

        List<Car> largeCarsList = new ArrayList<Car>();
        Car c7 = new Car(Constants.TYPE.LARGE);
        Car c8 = new Car(Constants.TYPE.LARGE);
        Car c9 = new Car(Constants.TYPE.LARGE);
        largeCarsList.add(c7);
        largeCarsList.add(c8);
        largeCarsList.add(c9);

        carReservation = new CarReservation(smallCarsList, mediumCarsList, largeCarsList);
    }

    @After
    public void tearDown() throws Exception {


    }

    /**
     * Start date and end date are same.
     *
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReserveCar1() throws Exception {
        BookingDetails bookingDetails = new BookingDetails("01/26/2019", "01/26/2019", "John", "Doe");
        carReservation.reserveCar(bookingDetails, Constants.TYPE.MEDIUM);
    }

    /**
     * Start date after end date.
     *
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReserveCar2() throws Exception {
        BookingDetails bookingDetails = new BookingDetails("01/26/2019", "01/25/2019", "John", "Doe");
        carReservation.reserveCar(bookingDetails, Constants.TYPE.LARGE);
    }

    /**
     * Start date before todays date.
     *
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReserveCar3() throws Exception {
        BookingDetails bookingDetails = new BookingDetails("01/24/2019", "01/25/2019", "John", "Doe");
        carReservation.reserveCar(bookingDetails, Constants.TYPE.SMALL);
    }

    /**
     * This method test adding valid reservations.
     * <p>
     * Test have to be written with taking todays date and adding days to it so that they do not fail in future,
     * when that date is past. for this exercise I have not done that
     *
     * @throws Exception
     */
    @Test
    public void testReserveCar4() throws Exception {
        //Able to add as its the first Reservation for the car 1.
        BookingDetails b1 = new BookingDetails("02/21/2019", "02/24/2019", "John", "Doe");
        Reservation r1 = carReservation.reserveCar(b1, Constants.TYPE.SMALL);
        assertNotNull(r1);

        //Unable to add as there is only one car of type small and it already has a reservation for the specified date and time
        BookingDetails b2 = new BookingDetails("02/21/2019", "02/24/2019", "Tina", "Fey");
        Reservation r2 = carReservation.reserveCar(b2, Constants.TYPE.SMALL);
        assertNull(r2);

        //Was able to add as there was no conflict.
        BookingDetails b3 = new BookingDetails("02/26/2019", "02/27/2019", "George", "Brett");
        Reservation r3 = carReservation.reserveCar(b3, Constants.TYPE.SMALL);
        assertNotNull(r3);

        //Was unable to add as the startdate is overlapping with George wallace's resevation
        BookingDetails b4 = new BookingDetails("02/25/2019", "02/28/2019", "Tom", "Cruze");
        Reservation r4 = carReservation.reserveCar(b4, Constants.TYPE.SMALL);
        assertNull(r4);

        //Was able to add as the start date was after all the other reservation before it.
        BookingDetails b5 = new BookingDetails("03/4/2019", "03/7/2019", "Brad", "Pitt");
        Reservation r5 = carReservation.reserveCar(b5, Constants.TYPE.SMALL);
        assertNotNull(r5);

        //Was able to add as its start date and end date are before any other reservation date in the list but equal or after todays date.
        BookingDetails b6 = new BookingDetails("02/10/2019", "02/15/2019", "Mike", "Pence");
        Reservation r6 = carReservation.reserveCar(b6, Constants.TYPE.SMALL);
        assertNotNull(r6);

        //Cannot add as its end date is in conflict with Brad Pitts reservation.
        BookingDetails b7 = new BookingDetails("03/01/2019", "03/05/2019", "Hendry", "Dorothy");
        Reservation r7 = carReservation.reserveCar(b7, Constants.TYPE.SMALL);
        assertNull(r7);

        //Remove george wallace reservation.
        assertTrue(carReservation.returnCar(r3));

        //Now u should be able to add Tom Cruze's reservation.
        BookingDetails b8 = new BookingDetails("02/25/2019", "02/28/2019", "Tom", "Cruze");
        Reservation r8 = carReservation.reserveCar(b8, Constants.TYPE.SMALL);
        assertNotNull(r8);

        //Remove Brad Pitts reservation
        assertTrue(carReservation.returnCar(r5));

        //Now u should be able to add Hendy Dorothy resevation.
        BookingDetails b9 = new BookingDetails("03/01/2019", "03/05/2019", "Hendry", "Dorothy");
        Reservation r9 = carReservation.reserveCar(b9, Constants.TYPE.SMALL);
        assertNotNull(r9);

    }

    /**
     * Test for booking multiple non overlapping reservation.
     *
     * @throws Exception
     */
    @Test
    public void testReserveCar5() throws Exception {

        //Able to add as its the first Reservation for the car 1.
        BookingDetails b1 = new BookingDetails("02/21/2019", "02/24/2019", "John", "Doe");
        Reservation r1 = carReservation.reserveCar(b1, Constants.TYPE.SMALL);
        assertNotNull(r1);

        //Was able to add as there was no conflict.
        BookingDetails b2 = new BookingDetails("02/26/2019", "02/27/2019", "George", "Brett");
        Reservation r3 = carReservation.reserveCar(b2, Constants.TYPE.SMALL);
        assertNotNull(r3);

        //Was able to add as the start date was after all the other reservation before it.
        BookingDetails b3 = new BookingDetails("03/4/2019", "03/7/2019", "Brad", "Pitt");
        Reservation r5 = carReservation.reserveCar(b3, Constants.TYPE.SMALL);
        assertNotNull(r5);

        //Was able to add as its start date and end date are before any other reservation date in the list but equal or after todays date.
        BookingDetails b4 = new BookingDetails("02/10/2019", "02/15/2019", "Mike", "Pence");
        Reservation r6 = carReservation.reserveCar(b4, Constants.TYPE.SMALL);
        assertNotNull(r6);

        //Test for booking multiple non overlapping bookings for the same person.
        List<BookingDetails> bookingDetailsList = new ArrayList<BookingDetails>();
        BookingDetails b5 = new BookingDetails("03/8/2019", "03/9/2019", "Toni", "Parker");
        BookingDetails b6 = new BookingDetails("03/11/2019", "03/13/2019", "Toni", "Parker");
        BookingDetails b7 = new BookingDetails("03/14/2019", "03/18/2019", "Toni", "Parker");
        BookingDetails b8 = new BookingDetails("03/20/2019", "03/24/2019", "Toni", "Parker");
        bookingDetailsList.add(b5);
        bookingDetailsList.add(b6);
        bookingDetailsList.add(b7);
        bookingDetailsList.add(b8);

        List<Reservation> myReservationList = carReservation.reserveCar(bookingDetailsList, Constants.TYPE.SMALL);
        assertTrue(myReservationList.size() == 4);
    }

    /**
     * This is test for overlapping bookings for single person. This results in no bookings for the person.
     *
     * @throws Exception
     */
    @Test
    public void testReserveCar6() throws Exception {
        //Able to add as its the first Reservation for the car 1.
        BookingDetails b1 = new BookingDetails("02/21/2019", "02/24/2019", "John", "Doe");
        Reservation r1 = carReservation.reserveCar(b1, Constants.TYPE.SMALL);
        assertNotNull(r1);

        //Was able to add as there was no conflict.
        BookingDetails b2 = new BookingDetails("02/26/2019", "02/27/2019", "George", "Brett");
        Reservation r3 = carReservation.reserveCar(b2, Constants.TYPE.SMALL);
        assertNotNull(r3);

        //Was able to add as the start date was after all the other reservation before it.
        BookingDetails b3 = new BookingDetails("03/4/2019", "03/7/2019", "Brad", "Pitt");
        Reservation r5 = carReservation.reserveCar(b3, Constants.TYPE.SMALL);
        assertNotNull(r5);

        //Was able to add as its start date and end date are before any other reservation date in the list but equal or after todays date.
        BookingDetails b4 = new BookingDetails("02/10/2019", "02/15/2019", "Mike", "Pence");
        Reservation r6 = carReservation.reserveCar(b4, Constants.TYPE.SMALL);
        assertNotNull(r6);

        //Test for booking multiple bookings for the same person. One or more of them might be overlapping with existing bookings. In this
        //case no booking possible.
        List<BookingDetails> bookingDetailsList = new ArrayList<BookingDetails>();
        BookingDetails b5 = new BookingDetails("03/8/2019", "03/9/2019", "Toni", "Parker");
        //This booking conflicts with an existing booking.
        BookingDetails b6 = new BookingDetails("03/5/2019", "03/14/2019", "Toni", "Parker");
        BookingDetails b7 = new BookingDetails("03/16/2019", "03/19/2019", "Toni", "Parker");
        BookingDetails b8 = new BookingDetails("03/20/2019", "03/24/2019", "Toni", "Parker");
        bookingDetailsList.add(b5);
        bookingDetailsList.add(b6);
        bookingDetailsList.add(b7);
        bookingDetailsList.add(b8);

        List<Reservation> myReservationList = carReservation.reserveCar(bookingDetailsList, Constants.TYPE.SMALL);
        assertTrue(myReservationList.size() == 0);
    }


}
