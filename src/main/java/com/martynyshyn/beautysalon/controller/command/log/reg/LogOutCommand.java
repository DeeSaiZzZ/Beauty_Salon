package com.martynyshyn.beautysalon.controller.command.log.reg;

import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LogOutCommand.
 *
 * @author N.Martynyshyn
 */

public class LogOutCommand implements Command {
    static final Logger logger = LogManager.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command start!");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            logger.info("User log out!");
        }
        logger.debug("Command end!");
        return Url.LOGIN_PAGE;
    }
}
