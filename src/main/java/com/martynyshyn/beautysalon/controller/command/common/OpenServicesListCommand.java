package com.martynyshyn.beautysalon.controller.command.common;

import com.martynyshyn.beautysalon.dao.ServicesDao;
import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.utils.QueryCreator;
import com.martynyshyn.beautysalon.service.Url;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ChangeLanguageCommand.
 *
 * @author N.Martynyshyn
 */

public class OpenServicesListCommand implements Command {
    static final Logger logger = LogManager.getLogger(OpenServicesListCommand.class);
    private static final String SIMPLE_QUERY = "SELECT id, name, price, speciality_id FROM servicelist";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command start!");
        ServicesDao servicesDao = new ServicesDao();

        //find all services
        request.setAttribute("serviceList", servicesDao.findWithFilters(QueryCreator.createSqlQuery(request, SIMPLE_QUERY)));

        logger.debug("Command end!");
        return Url.SERVICE_LIST_PAGE;
    }
}
