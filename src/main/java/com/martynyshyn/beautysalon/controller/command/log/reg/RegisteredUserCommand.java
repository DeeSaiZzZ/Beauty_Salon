package com.martynyshyn.beautysalon.controller.command.log.reg;

import com.martynyshyn.beautysalon.dao.MasterDao;
import com.martynyshyn.beautysalon.dao.UserDao;
import com.martynyshyn.beautysalon.dao.UserEntityCreate;
import com.martynyshyn.beautysalon.model.Master;
import com.martynyshyn.beautysalon.model.User;
import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Messages;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.utils.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * RegisteredUserCommand.
 *
 * @author N.Martynyshyn
 */

public class RegisteredUserCommand implements Command {

    //master email regex
    private static final String EMPLOY_EMAIL_PATTERN = "\\w+@bfactor.com";
    static final Logger logger = LogManager.getLogger(RegisteredUserCommand.class);

    private static final String INSERT_NEW_USER = "INSERT INTO users (email, password, firstname, lastname) " +
            "VALUES (?,?,?,?)";

    private static final String INSERT_NEW_MASTER = "INSERT INTO employee (email, password, firstname, lastname) " +
            "VALUES (?,?,?,?)";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command start!");
        boolean regStatus;

        //extract registration param from request
        String email = request.getParameter("email");
        String firstName = request.getParameter("name");
        String lastName = request.getParameter("surname");
        String pass = request.getParameter("pass");

        if (Validator.isBlankOrNull(Stream.of(email, firstName, lastName, pass))) {
            logger.error("Data not valid!");
            request.getSession().setAttribute("message", Messages.ENTERED_BLANK_FIELD);
            return Url.REGISTRATION_PAGE;
        }

        if (Validator.noRequiredLength(pass)) {
            logger.error("Password don't have required length");
            request.getSession().setAttribute("message", Messages.SHORT_PASSWORD);
            return Url.REGISTRATION_PAGE;
        }

        Pattern pattern = Pattern.compile(EMPLOY_EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        if (matcher.find()) {
            UserEntityCreate masterDao = new MasterDao();
            regStatus = masterDao.create(new Master(email, pass, firstName, lastName), INSERT_NEW_MASTER);
        } else {
            UserEntityCreate userDao = new UserDao();
            regStatus = userDao.create(new User(email, pass, firstName, lastName), INSERT_NEW_USER);
        }

        logger.trace(email + " registered status: " + regStatus);
        logger.debug("Command end!");

        if (regStatus) {
            return Url.LOGIN_PAGE;
        } else {
            request.getSession().setAttribute("message", Messages.EMAIL_EXIST);
            return Url.REGISTRATION_PAGE;
        }
    }
}
