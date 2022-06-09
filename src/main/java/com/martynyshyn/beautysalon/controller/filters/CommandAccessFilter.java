package com.martynyshyn.beautysalon.controller.filters;

import com.martynyshyn.beautysalon.model.enums.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Security filter, configure permission.
 *
 * @author N.Martynyshyn
 */

public class CommandAccessFilter implements Filter {


    //Command with role
    private static final Map<Role, List<String>> accessMap = new HashMap<>();
    private static final List<String> common = new ArrayList<>();

    //init command container
    @Override
    public void init(FilterConfig filterConfig) {
        accessMap.put(Role.ADMIN, List.of(filterConfig.getInitParameter("admin").split(" ")));
        accessMap.put(Role.MASTER, List.of(filterConfig.getInitParameter("master").split(" ")));
        accessMap.put(Role.DEFAULT, List.of(filterConfig.getInitParameter("default").split(" ")));
        Collections.addAll(common, filterConfig.getInitParameter("common").split(" "));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (hasPermission(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

            httpRequest.getRequestDispatcher("/WEB-INF/view/error/404.jsp").forward(servletRequest, servletResponse);
        }
    }

    /**
     * Checks if the user has access to the command.
     *
     * @param servletRequest ServletRequest entity.
     * @return boolean.
     */

    private boolean hasPermission(ServletRequest servletRequest) {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String actionName = servletRequest.getParameter("action");

        if (actionName == null || actionName.isEmpty()) {
            return false;
        }

        if (common.contains(actionName)) {
            return true;
        }

        Role userRole = (Role) httpRequest.getSession().getAttribute("userRole");

        if (userRole == null) {
            return false;
        }

        return accessMap.get(userRole).contains(actionName) || common.contains(actionName);
    }
}
