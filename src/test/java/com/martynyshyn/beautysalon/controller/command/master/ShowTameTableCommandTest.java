package com.martynyshyn.beautysalon.controller.command.master;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
import com.martynyshyn.beautysalon.model.Master;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowTameTableCommandTest {

    @InjectMocks
    ShowTimetableCommand command;
    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private Master master;

    public ShowTameTableCommandTest() throws SQLException {
    }

    @Test
    void showTimetableTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            int currentMasterId = 3;
            String findDate = "2022-04-15";

            when(request.getParameter("date")).thenReturn(findDate);
            when(request.getSession()).thenReturn(session);
            master.setId(currentMasterId);
            when(session.getAttribute("currentUser")).thenReturn(master);

            String result = command.execute(request, response);

            verify(request).setAttribute("orderList", new ArrayList<>());
            verify(request).setAttribute("currentDate", findDate);
            assertEquals(Url.MASTER_TIME_TABLE_PAGE, result);
        }
    }
}
