package com.martynyshyn.beautysalon.dao;

import com.martynyshyn.beautysalon.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for entity User.
 *
 * @author N.Martynyshyn
 */

public class UserDao implements UserEntityCreate {
    static final Logger logger = LogManager.getLogger(UserDao.class);

    private static final String FIND_USER_BY_EMAIL = "SELECT id, email, password, firstname, lastname, role_id FROM users WHERE email=?";
    private static final String FIND_USER_BY_ID = "SELECT id,firstname,lastname FROM users WHERE id=?";

    /**
     * Returns a user with the given identifier.
     *
     * @param searchId User identifier.
     * @return User entity.
     */

    public User findById(int searchId) {
        User findUser = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_ID)) {

            ps.setInt(1, searchId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {

                int colIndex = 1;

                findUser = new User.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setFirstName(resultSet.getString(colIndex++))
                        .setLastName(resultSet.getString(colIndex))
                        .build();

            }
        } catch (SQLException e) {
            logger.error("Method findById" + e.getMessage());
        }
        return findUser;
    }

    public User findById(int searchId, Connection connection) {
        User findUser = null;

        try (PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_ID)) {

            ps.setInt(1, searchId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {

                int colIndex = 1;

                findUser = new User.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setFirstName(resultSet.getString(colIndex++))
                        .setLastName(resultSet.getString(colIndex))
                        .build();

            }
        } catch (SQLException e) {
            logger.error("Method findById" + e.getMessage());
        }
        return findUser;
    }

    /**
     * Returns a user with the given email.
     *
     * @param emailPattern User email.
     * @return User entity.
     */

    public User findUserByEmail(String emailPattern) {

        User findUser = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_USER_BY_EMAIL)) {

            ps.setString(1, emailPattern);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int colIndex = 1;

                findUser = new User.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setEmail(resultSet.getString(colIndex++))
                        .setPassword(resultSet.getString(colIndex++))
                        .setFirstName(resultSet.getString(colIndex++))
                        .setLastName(resultSet.getString(colIndex++))
                        .setRoleId(resultSet.getInt(colIndex))
                        .build();

            }
        } catch (SQLException e) {
            logger.error("Method findUserByEmail" + e.getMessage());
        }
        return findUser;
    }
}
