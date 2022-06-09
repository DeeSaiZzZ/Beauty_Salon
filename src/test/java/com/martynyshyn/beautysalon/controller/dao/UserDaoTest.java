package com.martynyshyn.beautysalon.controller.dao;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
import com.martynyshyn.beautysalon.dao.UserDao;
import com.martynyshyn.beautysalon.model.User;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDaoTest {

    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");
    @Mock
    private ConnectionPool connectionPool;

    private static User user;

    public UserDaoTest() throws SQLException {
    }

    @Test
    void findByIdTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            user = new User.Builder()
                    .setId(2)
                    .setFirstName("Test")
                    .setLastName("Test")
                    .build();

            assertEquals(user, new UserDao().findById(2));
        }
    }

    @Test
    void findByEmailTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            String email = "someUser1@mail.com";

            user = new User.Builder()
                    .setId(4)
                    .setEmail(email)
                    .setPassword("jonson222")
                    .setFirstName("Jonson")
                    .setLastName("Kane")
                    .setRoleId(3)
                    .build();

            assertEquals(user, new UserDao().findUserByEmail(email));
        }
    }

}
