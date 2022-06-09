package com.martynyshyn.beautysalon.controller.command.common;

import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ChangeLanguageCommand.
 *
 * @author N.Martynyshyn
 */

public class ChangeLanguageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter("lang");
        Cookie cookie = new Cookie("locale", lang);
        response.addCookie(cookie);
        return Url.REDIRECT_MASTER_LIST_PAGE;
    }
}
