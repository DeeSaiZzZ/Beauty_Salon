package com.martynyshyn.beautysalon.controller.command.client;

import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.dao.OrderDao;
import com.martynyshyn.beautysalon.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OpenMyOrderListCommand - opens orders for which the user is registered.
 *
 * @author N.Martynyshyn
 */

public class OpenMyOrderListCommand implements Command {
    static final Logger logger = LogManager.getLogger("CommandLogger");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command start!");
        OrderDao orderDao = new OrderDao();
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        logger.trace("User " + currentUser.getFirstName() + " " + currentUser.getLastName() + " check it's order");
        request.setAttribute("myOrder", orderDao.findUserOrders(currentUser.getId()));
        logger.debug("Command end!");
        return Url.MY_ORDERS_LIST_PAGE;
    }
}
