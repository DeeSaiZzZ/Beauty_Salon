package com.martynyshyn.beautysalon.controller.listener;

import com.martynyshyn.beautysalon.model.Order;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.LinkedList;

/**
 * ContextListener, init request list and put in context.
 *
 * @author N.Martynyshyn
 */

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("requestList", new LinkedList<Order>());
    }
}
