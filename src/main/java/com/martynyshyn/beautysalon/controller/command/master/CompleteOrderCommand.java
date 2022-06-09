package com.martynyshyn.beautysalon.controller.command.master;

import com.martynyshyn.beautysalon.dao.OrderDao;
import com.martynyshyn.beautysalon.model.Order;
import com.martynyshyn.beautysalon.model.enums.StatusNames;
import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Messages;
import com.martynyshyn.beautysalon.service.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CompleteOrderCommand.
 *
 * @author N.Martynyshyn
 */

public class CompleteOrderCommand implements Command {

    private final SimpleDateFormat formatDateNow = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        if (request.getParameter("order_number") == null || request.getParameter("order_number").isBlank()) {
            request.getSession().setAttribute("message", Messages.BAD_INPUT_DATA);
        } else {

            int orderId = Integer.parseInt(request.getParameter("order_number"));

            OrderDao orderDao = new OrderDao();

            Order order = orderDao.findById(orderId);

            if (order == null) {
                request.getSession().setAttribute("message", Messages.NOT_FOUND_ORDER);
            } else if (!order.getOrderStatus().equals(StatusNames.PAID.getStatusName())) {
                request.getSession().setAttribute("message", Messages.ORDER_NOT_PAID);
            } else {
                order.setOrderStatus(StatusNames.COMPLETE.getStatusName());
                order.setCompleteDate(formatDateNow.format(new Date()));
                orderDao.update(order);
            }
        }

        return Url.REDIRECT_MASTER_TIMETABLE_PAGE;
    }
}
