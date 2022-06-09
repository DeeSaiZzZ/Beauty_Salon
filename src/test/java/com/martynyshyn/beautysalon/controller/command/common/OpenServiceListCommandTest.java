package com.martynyshyn.beautysalon.controller.command.common;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
import com.martynyshyn.beautysalon.service.Url;
import com.martynyshyn.beautysalon.utils.QueryCreator;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenServiceListCommandTest {
    @InjectMocks
    OpenServicesListCommand command;
    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    OpenServiceListCommandTest() throws SQLException {
    }

    @Test
    void openServiceListTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            String query = "SELECT id, name, price, speciality_id FROM servicelist";

            try (MockedStatic<QueryCreator> qc = Mockito.mockStatic(QueryCreator.class)) {
                qc.when(() -> QueryCreator.createSqlQuery(request, eq(anyString()))).thenReturn(query);

                String result = command.execute(request, response);

                verify(request).setAttribute("serviceList", new ArrayList<>());
                assertEquals(Url.SERVICE_LIST_PAGE, result);
            }
        }
    }
}
