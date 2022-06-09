package com.martynyshyn.beautysalon.controller.command.admin;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckAllOrderCommandTest {

    @InjectMocks
    CheckAllOrderCommand command;
    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    public CheckAllOrderCommandTest() throws SQLException {
    }

    @Test
    void checkAllOrderTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            String date = "2022-04-15";

            when(request.getParameter("date")).thenReturn(date);

            String result = command.execute(request, response);

            verify(request).setAttribute(eq("orders"), any(ArrayList.class));
            verify(request).setAttribute("currentDate", date);
            assertEquals(Url.ALL_ORDER_PAGE, result);
        }
    }
}
