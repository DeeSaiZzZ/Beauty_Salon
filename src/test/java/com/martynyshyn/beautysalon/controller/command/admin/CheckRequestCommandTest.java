package com.martynyshyn.beautysalon.controller.command.admin;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
import com.martynyshyn.beautysalon.model.Master;
import com.martynyshyn.beautysalon.model.Order;
import com.martynyshyn.beautysalon.model.Service;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckRequestCommandTest {

    @InjectMocks
    CheckRequestCommand command;
    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext context;

    CheckRequestCommandTest() throws SQLException {
    }

    @Test
    @SuppressWarnings("unchecked")
    void checkRequestOrderNoExistTest() {
        LinkedList<Order> orders = spy(LinkedList.class);

        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute("requestList")).thenReturn(orders);

        String result = command.execute(request, response);

        verify(request).setAttribute("orderRequest", null);
        verify(request).setAttribute("orderList", null);
        assertEquals(Url.ORDER_REQUEST, result);
    }

    @Test
    @SuppressWarnings("unchecked")
    void checkRequestOrderCommand() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            LinkedList<Order> orders = spy(LinkedList.class);

            Order order = new Order.Builder()
                    .setService(new Service())
                    .setMaster(new Master())
                    .setDate(anyString())
                    .build();

            orders.add(order);

            when(request.getServletContext()).thenReturn(context);
            when(context.getAttribute("requestList")).thenReturn(orders);

            command.execute(request, response);

            verify(request).setAttribute("orderRequest", order);
            verify(request).setAttribute("orderList", new ArrayList<>());
        }

    }
}