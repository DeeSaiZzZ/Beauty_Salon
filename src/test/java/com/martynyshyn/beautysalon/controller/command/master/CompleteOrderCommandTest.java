package com.martynyshyn.beautysalon.controller.command.master;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
import com.martynyshyn.beautysalon.service.Messages;
import com.martynyshyn.beautysalon.service.Url;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompleteOrderCommandTest {
    @InjectMocks
    CompleteOrderCommand command;
    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    CompleteOrderCommandTest() throws SQLException {
    }

    @Test
    void dontExistOrderNumber() {
        when(request.getParameter("order_number")).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        String result = command.execute(request, response);

        verify(session).setAttribute("message", Messages.BAD_INPUT_DATA);
        assertEquals(Url.REDIRECT_MASTER_TIMETABLE_PAGE, result);
    }

    @Test
    void blackOrderNumber() {
        when(request.getParameter("order_number")).thenReturn("");
        when(request.getSession()).thenReturn(session);

        String result = command.execute(request, response);

        verify(session).setAttribute("message", Messages.BAD_INPUT_DATA);
        assertEquals(Url.REDIRECT_MASTER_TIMETABLE_PAGE, result);
    }

    @Test
    void notNumberOrder() {
        when(request.getParameter("order_number")).thenReturn("ssa");
        assertThrows(NumberFormatException.class, () -> command.execute(request, response));
    }

    @Test
    void orderNotFound() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            int noExistId = 543;

            when(request.getParameter("order_number")).thenReturn(String.valueOf(noExistId));
            when(request.getSession()).thenReturn(session);

            String result = command.execute(request, response);

            verify(session).setAttribute("message", Messages.NOT_FOUND_ORDER);
            assertEquals(Url.REDIRECT_MASTER_TIMETABLE_PAGE, result);
        }
    }

    @Test
    void orderNotPaid() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            int orderId = 3;

            when(request.getParameter("order_number")).thenReturn(String.valueOf(orderId));
            when(request.getSession()).thenReturn(session);

            String result = command.execute(request, response);

            verify(session).setAttribute("message", Messages.ORDER_NOT_PAID);
            assertEquals(Url.REDIRECT_MASTER_TIMETABLE_PAGE, result);
        }
    }

    //TODO make test of complete order
}