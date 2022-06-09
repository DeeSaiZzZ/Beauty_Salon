package com.martynyshyn.beautysalon.controller.command.master;

import com.martynyshyn.beautysalon.dao.OrderDao;
import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.model.Master;
import com.martynyshyn.beautysalon.model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CompleteOrderCommand.
 *
 * @author N.Martynyshyn
 */

public class ShowTimetableCommand implements Command {

    private final SimpleDateFormat formatDateNow = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String date = request.getParameter("date");

        if (date == null) {
            date = formatDateNow.format(new Date());
        }

        Master currentMaster = (Master) request.getSession().getAttribute("currentUser");

        //filter master order
        List<Order> collect = new OrderDao().findOrdersByDate(date).stream()
                .filter(o -> o.getOrderMaster().getId() == currentMaster.getId())
                .collect(Collectors.toList());

        request.setAttribute("orderList", collect);
        request.setAttribute("currentDate", date);

        return Url.MASTER_TIME_TABLE_PAGE;
    }
}
