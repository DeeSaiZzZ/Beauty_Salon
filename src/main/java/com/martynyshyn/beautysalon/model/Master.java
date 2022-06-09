package com.martynyshyn.beautysalon.model;

import java.util.Objects;

/**
 * Master entity.
 *
 * @author N.Martynyshyn
 */

public class Master extends User {

    private double rate;
    private int specialityId;
    private String specialityName;

    public Master() {
    }

    public Master(String email, String pass, String firstName, String lastName) {
        super(email, pass, firstName, lastName);
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public double getRate() {
        return rate;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Master master = (Master) o;
        return Double.compare(master.rate, rate) == 0 && specialityId == master.specialityId && Objects.equals(specialityName, master.specialityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rate, specialityId, specialityName);
    }

    //Entity builder
    public static class Builder {

        private Master newMaster;

        public Builder() {
            newMaster = new Master();
        }

        public Builder setId(int id) {
            newMaster.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            newMaster.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            newMaster.password = password;
            return this;
        }

        public Builder setFirstName(String firstName) {
            newMaster.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            newMaster.lastName = lastName;
            return this;
        }

        public Builder setRoleId(int roleId) {
            newMaster.roleId = roleId;
            return this;
        }

        public Builder setRate(double rate) {
            newMaster.rate = rate;
            return this;
        }

        public Builder setSpecialityId(int specialityId) {
            newMaster.specialityId = specialityId;
            return this;
        }

        public Builder setSpecialityName(String specialityName) {
            newMaster.specialityName = specialityName;
            return this;
        }

        public Master build() {
            return newMaster;
        }
    }
}