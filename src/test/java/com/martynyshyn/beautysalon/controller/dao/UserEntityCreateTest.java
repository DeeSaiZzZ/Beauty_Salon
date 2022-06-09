package com.martynyshyn.beautysalon.controller.dao;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
import com.martynyshyn.beautysalon.dao.UserDao;
import com.martynyshyn.beautysalon.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserEntityCreateTest {

    @InjectMocks
    UserDao userEntityCreate;

    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");
    @Mock
    private ConnectionPool connectionPool;

    UserEntityCreateTest() throws SQLException {
    }

    @Test
    void createEntityTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            boolean b = userEntityCreate.create(
                    new User.Builder()
                            .setFirstName("ForTest")
                            .setLastName("ForTest")
                            .setEmail("test@mail.com")
                            .setPassword("someTestPassword")
                            .build(),
                    "INSERT INTO users (email, password, firstname, lastname) VALUES (?,?,?,?)"
            );
            assertTrue(b);
        }
    }

    @Test
    void createEntityEmailExist() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            boolean b = userEntityCreate.create(
                    new User.Builder()
                            .setFirstName("ForTest")
                            .setLastName("ForTest")
                            .setEmail("someUser@mail.com")
                            .setPassword("someTestPassword")
                            .build(),
                    "INSERT INTO users (email, password, firstname, lastname) VALUES (?,?,?,?)"
            );
            assertFalse(b);
        }
    }
}
