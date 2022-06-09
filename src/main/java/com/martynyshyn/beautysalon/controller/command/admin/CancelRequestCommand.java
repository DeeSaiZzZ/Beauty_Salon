package com.martynyshyn.beautysalon.controller.command.admin;

import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Command canceled request.
 *
 * @author N.Martynyshyn
 */

@SuppressWarnings("unchecked")
public class CancelRequestCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //get request list and delete request
        LinkedList<Order> orders = (LinkedList<Order>) request.getServletContext().getAttribute("requestList");
        orders.removeFirst();
        return Url.REDIRECT_REQUEST_FORM;
    }
}
