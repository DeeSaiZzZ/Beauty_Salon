package com.martynyshyn.beautysalon.controller.dao;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConnectionPoolTest {

    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");
    @Mock
    private ConnectionPool connectionPool;

    public ConnectionPoolTest() throws SQLException {
    }

    @Test
    void connectionPoolTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            Connection testConnection = ConnectionPool.getInstance().getConnection();

            assertNotNull(testConnection);
            assertEquals(connection,testConnection);
        }
    }
}
