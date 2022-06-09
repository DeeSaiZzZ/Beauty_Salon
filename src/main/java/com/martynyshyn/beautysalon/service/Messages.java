package com.martynyshyn.beautysalon.service;

/**
 * Class whit constant messages.
 *
 * @author N.Martynyshyn
 */

public final class Messages {
    private Messages() {
        //It is necessary that there was no possibility to create an instance of the class
    }

    public static final String SHORT_PASSWORD = "Password must be longer than 13 characters";
    public static final String EMAIL_OR_PASSWORD_WAS_BAD = "Email or Password was bad!";
    public static final String EMAIL_EXIST = "Email is exist please change him!";
    public static final String ENTERED_BLANK_FIELD = "Field cannot be blank";
    public static final String NOT_FOUND_ORDER = "Order with this property not found!";
    public static final String TIMESLOT_NOT_FREE = "This time-slot are not free";
    public static final String BAD_INPUT_DATA = "Bad input data, enter all field";
    public static final String ORDER_NOT_PAID = "Order isn't paid!";

}
