package com.martynyshyn.beautysalon.model;

import java.util.Objects;

/**
 * Order entity.
 *
 * @author N.Martynyshyn
 */

public class Order {
    private int id;

    private User orderUser;
    private Master orderMaster;
    private Service orderService;

    private String orderStatus;
    private String orderDate;
    private String orderTime;

    private String completeDate;

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderUser(User orderUser) {
        this.orderUser = orderUser;
    }

    public void setOrderMaster(Master orderMaster) {
        this.orderMaster = orderMaster;
    }

    public void setOrderService(Service orderService) {
        this.orderService = orderService;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getId() {
        return id;
    }

    public User getOrderUser() {
        return orderUser;
    }

    public Master getOrderMaster() {
        return orderMaster;
    }

    public Service getOrderService() {
        return orderService;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(orderUser, order.orderUser) && Objects.equals(orderMaster, order.orderMaster) && Objects.equals(orderService, order.orderService) && Objects.equals(orderStatus, order.orderStatus) && Objects.equals(orderDate, order.orderDate) && Objects.equals(orderTime, order.orderTime) && Objects.equals(completeDate, order.completeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderUser, orderMaster, orderService, orderStatus, orderDate, orderTime, completeDate);
    }

    public static class Builder {

        private Order newOrder;

        public Builder() {
            newOrder = new Order();
        }

        public Builder setId(int id) {
            newOrder.id = id;
            return this;
        }

        public Builder setUser(User user) {
            newOrder.orderUser = user;
            return this;
        }

        public Builder setMaster(Master master) {
            newOrder.orderMaster = master;
            return this;
        }

        public Builder setService(Service service) {
            newOrder.orderService = service;
            return this;
        }

        public Builder setStatus(String status) {
            newOrder.orderStatus = status;
            return this;
        }

        public Builder setDate(String date) {
            newOrder.orderDate = date;
            return this;
        }

        public Builder setTime(String time) {
            newOrder.orderTime = time;
            return this;
        }

        public Order build() {
            return newOrder;
        }
    }
}
