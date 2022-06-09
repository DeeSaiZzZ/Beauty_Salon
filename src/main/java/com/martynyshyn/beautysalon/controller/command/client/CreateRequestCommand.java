package com.martynyshyn.beautysalon.controller.command.client;

import com.martynyshyn.beautysalon.dao.MasterDao;
import com.martynyshyn.beautysalon.dao.ServicesDao;
import com.martynyshyn.beautysalon.model.Order;
import com.martynyshyn.beautysalon.model.User;
import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Messages;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.utils.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * CreateRequestCommand.
 *
 * @author N.Martynyshyn
 */

@SuppressWarnings("unchecked")
public class CreateRequestCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String serviceId = request.getParameter("service_id");
        String masterId = request.getParameter("master_id");
        String orderDate = request.getParameter("orderDate");
        String orderTime = request.getParameter("orderTime");

        //validate input data
        if (Validator.isBlankOrNull(Stream.of(serviceId, masterId, orderDate, orderTime))) {
            request.getSession().setAttribute("message", Messages.BAD_INPUT_DATA);
        } else {

            //change in the state of the entity
            Order order = new Order.Builder()
                    .setUser((User) request.getSession().getAttribute("currentUser"))
                    .setMaster(new MasterDao().findById(Integer.parseInt(masterId)))
                    .setService(new ServicesDao().findById(Integer.parseInt(serviceId)))
                    .setDate(orderDate)
                    .setTime(orderTime)
                    .build();

            //added entity to requestList
            LinkedList<Order> requestList = (LinkedList<Order>) request.getServletContext().getAttribute("requestList");
            requestList.add(order);
        }
        return Url.REDIRECT_REG_TO_ORDER_FORM;
    }
}
