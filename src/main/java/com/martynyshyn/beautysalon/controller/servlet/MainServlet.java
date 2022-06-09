package com.martynyshyn.beautysalon.controller.servlet;

import com.martynyshyn.beautysalon.service.Command;
import com.martynyshyn.beautysalon.service.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MainServlet (controller).
 *
 * @author N.Martynyshyn
 */

public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Main method of this controller.
     */

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CommandFactory client = new CommandFactory();
        Command command = client.defineCommand(request);
        String page;
        page = command.execute(request, response);
        String method = request.getMethod();
        if (method.equals("POST")) {
            response.sendRedirect(page);
        } else if (method.equals("GET")) {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}