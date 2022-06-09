package com.martynyshyn.beautysalon.controller.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * EncodingFilter, set encoding UTF-8.
 *
 * @author N.Martynyshyn
 */

public class EncodingFilter implements Filter {

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestEncoding = servletRequest.getCharacterEncoding();
        if (requestEncoding == null) {
            servletRequest.setCharacterEncoding(encoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
