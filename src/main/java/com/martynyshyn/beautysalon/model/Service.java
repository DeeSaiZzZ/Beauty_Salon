package com.martynyshyn.beautysalon.model;

import java.util.Objects;

/**
 * Service entity.
 *
 * @author N.Martynyshyn
 */

public class Service {

    private int id;
    private String name;
    private int price;
    private int specialityId;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return id == service.id && price == service.price && specialityId == service.specialityId && Objects.equals(name, service.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, specialityId);
    }

    //Entity builder
    public static class Builder {

        private Service newService;

        public Builder() {
            newService = new Service();
        }

        public Builder setId(int id) {
            newService.id = id;
            return this;
        }

        public Builder setName(String name) {
            newService.name = name;
            return this;
        }

        public Builder setPrice(int price) {
            newService.price = price;
            return this;
        }

        public Builder setSpeciality_id(int speciality_id) {
            newService.specialityId = speciality_id;
            return this;
        }

        public Service build() {
            return newService;
        }
    }
}
