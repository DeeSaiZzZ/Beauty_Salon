package com.martynyshyn.beautysalon.controller.command;

import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorCommand implements Command {
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return Url.ERROR_PAGE;
    }
}
