package com.martynyshyn.beautysalon.controller.command.admin;

import com.martynyshyn.beautysalon.dao.OrderDao;
import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The method finds all orders by a specific date.
 *
 * @author N.Martynyshyn
 */

public class CheckAllOrderCommand implements Command {

    private final SimpleDateFormat formatDateNow = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String date = request.getParameter("date");
        //If the date is not found, make it current
        if (date == null) {
            date = formatDateNow.format(new Date());
        }

        //find order by date
        request.setAttribute("orders", new OrderDao().findOrdersByDate(date));
        request.setAttribute("currentDate", date);

        return Url.ALL_ORDER_PAGE;
    }
}
