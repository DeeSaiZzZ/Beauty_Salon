package com.martynyshyn.beautysalon.controller.command.admin;

import com.martynyshyn.beautysalon.dao.OrderDao;
import com.martynyshyn.beautysalon.dao.UserDao;
import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Messages;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.model.Order;
import com.martynyshyn.beautysalon.model.enums.StatusNames;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Accepts a request from the user.
 *
 * @author N.Martynyshyn
 */

@SuppressWarnings("unchecked")
public class AcceptRequestCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        //get all request param
        int masterId = Integer.parseInt(request.getParameter("master_id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int serviceId = Integer.parseInt(request.getParameter("service_id"));
        String date = request.getParameter("order_date");
        String time = request.getParameter("order_time");

        OrderDao orderDao = new OrderDao();
        //find order with parameters
        Order order = orderDao.findOrderByParam(serviceId, masterId, date, time);

        //Checking whether the order exists in the DB, or claimed
        if (order == null) {
            request.getSession().setAttribute("message", Messages.NOT_FOUND_ORDER);
        } else if (order.getOrderUser() != null) {
            request.getSession().setAttribute("message", Messages.TIMESLOT_NOT_FREE);
        } else {
            //set user on order and change status
            order.setOrderUser(new UserDao().findById(userId));
            order.setOrderStatus(StatusNames.AWAITING_PAYMENT.getStatusName());
            orderDao.update(order);
            LinkedList<Order> orders = (LinkedList<Order>) request.getServletContext().getAttribute("requestList");
            orders.removeFirst();
        }

        return Url.REDIRECT_REQUEST_FORM;
    }
}
