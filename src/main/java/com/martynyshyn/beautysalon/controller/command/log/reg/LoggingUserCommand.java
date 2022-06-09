package com.martynyshyn.beautysalon.controller.command.log.reg;

import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Messages;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.dao.MasterDao;
import com.martynyshyn.beautysalon.dao.UserDao;
import com.martynyshyn.beautysalon.model.Master;
import com.martynyshyn.beautysalon.model.User;
import com.martynyshyn.beautysalon.model.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LoggingUserCommand.
 *
 * @author N.Martynyshyn
 */

public class LoggingUserCommand implements Command {
    static final Logger logger = LogManager.getLogger(LoggingUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command start!");
        HttpSession session = request.getSession();

        String redirectPage = Url.REDIRECT_MASTER_LIST_PAGE;

        //extract email and password from request
        String email = request.getParameter("email");
        String password = request.getParameter("pass");

        User user = new UserDao().findUserByEmail(email);
        Master master = new MasterDao().findMasterByEmail(email);

        if (user != null && password.equals(user.getPassword())) {
            setSessionUser(user, session);
        } else if (master != null && password.equals(master.getPassword())) {
            setSessionUser(master, session);
        } else {
            request.getSession().setAttribute("message", Messages.EMAIL_OR_PASSWORD_WAS_BAD);
            redirectPage = Url.LOGIN_PAGE;
            logger.info("Bad input data");
        }
        logger.debug("Command end!");
        return redirectPage;
    }

    /**
     * Set user in session.
     *
     * @param user
     *    The user you want to install in the session.
     *
     * @param session
     *          Session object.
     */

    private void setSessionUser(User user, HttpSession session) {
        session.setAttribute("currentUser", user);
        session.setAttribute("userRole", Role.getRole(user));
        logger.trace(user.getEmail() + " who have role " + Role.getRole(user).getName() + " log now");
    }
}
