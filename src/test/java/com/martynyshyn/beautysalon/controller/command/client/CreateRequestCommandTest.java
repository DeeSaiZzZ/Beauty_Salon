package com.martynyshyn.beautysalon.controller.command.client;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
import com.martynyshyn.beautysalon.model.User;
import com.martynyshyn.beautysalon.service.Messages;
import com.martynyshyn.beautysalon.service.Url;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRequestCommandTest {

    @InjectMocks
    CreateRequestCommand command;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ServletContext context;
    @Mock
    HttpSession session;
    @Mock
    ConnectionPool connectionPool;
    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");

    public CreateRequestCommandTest() throws SQLException {
    }

    @Test
    void createRequestWithBlankParam() {
        when(request.getParameter("service_id")).thenReturn("");
        when(request.getParameter("master_id")).thenReturn("1");
        when(request.getParameter("orderDate")).thenReturn("2022-04-15");
        when(request.getParameter("orderTime")).thenReturn("17:00");

        when(request.getSession()).thenReturn(session);

        String result = command.execute(request, response);

        verify(session).setAttribute("message", Messages.BAD_INPUT_DATA);
        assertEquals(Url.REDIRECT_REG_TO_ORDER_FORM, result);
    }

    @Test
    void createRequest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            when(request.getParameter("service_id")).thenReturn("4");
            when(request.getParameter("master_id")).thenReturn("1");
            when(request.getParameter("orderDate")).thenReturn("2022-04-15");
            when(request.getParameter("orderTime")).thenReturn("17:00");

            when(request.getSession()).thenReturn(session);
            when(session.getAttribute("currentUser")).thenReturn(new User());
            when(request.getServletContext()).thenReturn(context);
            when(context.getAttribute("requestList")).thenReturn(new LinkedList<>());

            String result = command.execute(request, response);

            assertEquals(Url.REDIRECT_REG_TO_ORDER_FORM, result);
        }
    }
}
