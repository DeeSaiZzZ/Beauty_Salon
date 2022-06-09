package com.martynyshyn.beautysalon.model.enums;

/**
 * StatusNames entity.
 *
 * @author N.Martynyshyn
 *
 */

public enum StatusNames {

    FREE("Free"),

    AWAITING_PAYMENT("Awaiting Payment"),

    PAID("Paid"),

    COMPLETE("Complete");

    StatusNames(String statusName) {
        this.statusName = statusName;
    }

    final String statusName;

    public String getStatusName() {
        return statusName;
    }

}
