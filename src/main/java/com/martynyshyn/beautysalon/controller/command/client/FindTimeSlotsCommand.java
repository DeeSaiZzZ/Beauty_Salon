package com.martynyshyn.beautysalon.controller.command.client;

import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.dao.MasterDao;
import com.martynyshyn.beautysalon.dao.OrderDao;
import com.martynyshyn.beautysalon.dao.ServicesDao;
import com.martynyshyn.beautysalon.model.Order;
import com.martynyshyn.beautysalon.model.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * FindTimeSlotsCommand - find time slot by select service and date.
 *
 * @author N.Martynyshyn
 */

public class FindTimeSlotsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String date = request.getParameter("date");
        int selectedServiceId = Integer.parseInt(request.getParameter("selected_service"));
        int selectedMasterId = Integer.parseInt(request.getParameter("master_id"));

        OrderDao orderDao = new OrderDao();
        ServicesDao servicesDao = new ServicesDao();

        List<Service> bySpeciality = servicesDao.findBySpeciality(new MasterDao().findById(selectedMasterId).getSpecialityId());
        //search for orders by parameters
        List<Order> orderByDateAndService = orderDao.findOrderByDateAndService(selectedServiceId, selectedMasterId, date);

        request.setAttribute("masterList", new MasterDao().findAll());
        request.setAttribute("serviceList", bySpeciality);
        request.setAttribute("orderList", orderByDateAndService);
        request.setAttribute("selectedMaster", selectedMasterId);
        request.setAttribute("selectedService", selectedServiceId);
        request.setAttribute("currentDate", date);

        return Url.ORDERS_LIST_PAGE;
    }
}
