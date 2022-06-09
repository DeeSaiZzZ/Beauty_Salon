package com.martynyshyn.beautysalon.controller.command.common;

import com.martynyshyn.beautysalon.dao.MasterDao;
import com.martynyshyn.beautysalon.model.Master;
import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.utils.QueryCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ChangeLanguageCommand.
 *
 * @author N.Martynyshyn
 */
public class OpenMasterListCommand implements Command {
    static final Logger logger = LogManager.getLogger(OpenMasterListCommand.class);
    private static final String DEFAULT_LOCALE = "en";
    private static final String SIMPLE_QUERY = "SELECT id, email, firstName, lastName, rate, speciality_id FROM employee";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Command start!");

        MasterDao masterDao = new MasterDao();
        List<Master> allMaster;

        allMaster = masterDao.findMastersWithSortAndFilters(getUserLocale(request),
                QueryCreator.createSqlQuery(request, SIMPLE_QUERY));

        request.setAttribute("masterList", allMaster);

        logger.trace("Master list sorted!");
        logger.debug("Command end!");

        return Url.MASTER_LIST_PAGE;
    }

    /**
     * Extract current locale from cookie, if cookie don't have locale
     * using default locale.
     *
     * @param request HttpServlet request
     * @return current locale name
     */
    private String getUserLocale(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        String currentLang = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("locale")) {
                    currentLang = cookie.getValue();
                }
            }
        }

        if (currentLang == null) {
            currentLang = DEFAULT_LOCALE;
        }

        return currentLang;
    }

}
