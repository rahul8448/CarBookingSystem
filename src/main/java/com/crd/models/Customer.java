package com.crd.models;

import com.crd.utils.Helper;

/**
 * This class respresents the customer. The customer may have lot more details stored in them such as credit card details , address, ssn etc
 * For this exercise we are making it as minimalistic as possible.
 */
public class Customer {

    public final int customerId;
    public final String firstName;
    public final String lastName;

    public Customer(String firstName, String lastName) {
        this.customerId = Helper.getInstance().generateNextCustomerId();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
