package com.martynyshyn.beautysalon.controller.command.admin;

import com.martynyshyn.beautysalon.dao.OrderDao;
import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Messages;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.utils.Validator;
import com.martynyshyn.beautysalon.model.Order;
import com.martynyshyn.beautysalon.model.enums.StatusNames;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

/**
 * Moderate user's order, can cancel or submit payment.
 *
 * @author N.Martynyshyn
 */

public class ModerateOrderCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String orderId = request.getParameter("order_number");
        String moderateType = request.getParameter("moderate_type");

        if (Validator.isBlankOrNull(Stream.of(orderId, moderateType))) {
            request.getSession().setAttribute("message", Messages.ENTERED_BLANK_FIELD);
            return Url.REDIRECT_ALL_ORDER_PAGE;
        }

        int order_id = Integer.parseInt(orderId);

        OrderDao orderDao = new OrderDao();

        Order order = orderDao.findById(order_id);

        //check mb order not found
        if (order == null) {
            request.getSession().setAttribute("message", Messages.NOT_FOUND_ORDER);
            //check order status, it must have status AWAITING_PAYMENT
        } else if (order.getOrderStatus().equals(StatusNames.AWAITING_PAYMENT.getStatusName())) {
            //cancel
            if (moderateType.equals("cancel_order")) {
                order.setOrderStatus(StatusNames.FREE.getStatusName());
                order.getOrderUser().setId(0);
            } else {
                //or submit payment
                order.setOrderStatus(StatusNames.PAID.getStatusName());
            }
            orderDao.update(order);
        } else {
            request.getSession().setAttribute("message", "Unable to cancel recording");
        }

        return Url.REDIRECT_ALL_ORDER_PAGE;
    }
}
