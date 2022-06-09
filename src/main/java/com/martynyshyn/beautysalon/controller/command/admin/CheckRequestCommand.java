package com.martynyshyn.beautysalon.controller.command.admin;

import com.martynyshyn.beautysalon.dao.OrderDao;
import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * Method get first request from the queue.
 *
 * @author N.Martynyshyn
 */

@SuppressWarnings("unchecked")
public class CheckRequestCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //get order from request list
        LinkedList<Order> orders = (LinkedList<Order>) request.getServletContext().getAttribute("requestList");
        Order order = orders.peek();
        List<Order> otherTheSameOrder = null;

        if (order != null) {
            OrderDao orderDao = new OrderDao();
            String date = order.getOrderDate();
            int selectedServiceId = order.getOrderService().getId();
            int selectedMasterId = order.getOrderMaster().getId();
            otherTheSameOrder = orderDao.findOrderByDateAndService(selectedServiceId, selectedMasterId, date);
        }

        request.setAttribute("orderRequest", order);
        request.setAttribute("orderList", otherTheSameOrder);
        return Url.ORDER_REQUEST;
    }
}
