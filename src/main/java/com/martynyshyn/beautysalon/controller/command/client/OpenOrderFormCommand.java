package com.martynyshyn.beautysalon.controller.command.client;

import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.dao.MasterDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OpenOrderFormCommand.
 *
 * @author N.Martynyshyn
 */

public class OpenOrderFormCommand implements Command {
    static final Logger logger = LogManager.getLogger(OpenOrderFormCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command start!");
        MasterDao masterDao = new MasterDao();
        request.setAttribute("masterList", masterDao.findAll());
        logger.debug("Command end...");
        return Url.ORDERS_LIST_PAGE;
    }
}
