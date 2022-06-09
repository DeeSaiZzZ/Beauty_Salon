package com.martynyshyn.beautysalon.controller.command.client;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FIndServiceCommandTest {

    @InjectMocks
    FindServiceCommand command;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    ConnectionPool connectionPool;
    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");

    public FIndServiceCommandTest() throws SQLException {
    }

    @Test
    void findServiceTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            int masterId = 2;

            when(request.getParameter("master_id")).thenReturn(String.valueOf(masterId));

            String result = command.execute(request, response);

            verify(request).setAttribute("serviceList", new ArrayList<>());
            verify(request).setAttribute("selectedMaster", masterId);
            verify(request).setAttribute("masterList", new ArrayList<>());
            assertEquals(Url.ORDERS_LIST_PAGE, result);
        }


    }
}
