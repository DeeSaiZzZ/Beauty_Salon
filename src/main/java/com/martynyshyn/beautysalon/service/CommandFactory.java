package com.martynyshyn.beautysalon.service;

import com.martynyshyn.beautysalon.controller.command.ErrorCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * CommandFactory.
 *
 * @author N.Martynyshyn
 */

public class CommandFactory {
    public Command defineCommand(HttpServletRequest request) {
        Command current = new ErrorCommand();
        String action = request.getParameter("action");
        try {
            EnumCommand currentEnum = EnumCommand.valueOf(action.toUpperCase());
            current = currentEnum.getCommand();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return current;
    }
}
