package com.martynyshyn.beautysalon.controller.command.client;

import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.dao.MasterDao;
import com.martynyshyn.beautysalon.dao.ServicesDao;
import com.martynyshyn.beautysalon.model.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * FindServiceCommand - find services how can do master by master speciality.
 *
 * @author N.Martynyshyn
 */

public class FindServiceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        ServicesDao servicesDao = new ServicesDao();

        int masterId = Integer.parseInt(request.getParameter("master_id"));

        //find order by speciality how can do it
        List<Service> bySpeciality = servicesDao.findBySpeciality(new MasterDao().findById(masterId).getSpecialityId());

        request.setAttribute("serviceList", bySpeciality);
        //required for installation to select tag on JSP
        request.setAttribute("selectedMaster", masterId);
        request.setAttribute("masterList", new MasterDao().findAll());

        return Url.ORDERS_LIST_PAGE;
    }
}
