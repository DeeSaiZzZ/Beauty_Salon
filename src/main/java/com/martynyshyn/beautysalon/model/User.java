package com.martynyshyn.beautysalon.model;

import java.util.Objects;

/**
 * User entity.
 *
 * @author N.Martynyshyn
 */

public class User {

    protected int id;
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected int roleId;

    public User() {
    }

    public User(String email, String pass, String firstName, String lastName) {
        this.email = email;
        this.password = pass;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getRoleId() {
        return roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && roleId == user.roleId && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, firstName, lastName, roleId);
    }

    //Entity builder
    public static class Builder {

        private User newUser;

        public Builder() {
            newUser = new User();
        }

        public Builder setId(int id) {
            newUser.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            newUser.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            newUser.password = password;
            return this;
        }

        public Builder setFirstName(String firstName) {
            newUser.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            newUser.lastName = lastName;
            return this;
        }

        public Builder setRoleId(int roleId) {
            newUser.roleId = roleId;
            return this;
        }

        public User build() {
            return newUser;
        }
    }
}
