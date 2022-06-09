package com.martynyshyn.beautysalon.dao;

import com.martynyshyn.beautysalon.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implements the insertion of a new user in the database.
 *
 * @author N.Martynyshyn
 */

public interface UserEntityCreate {

    default boolean create(User entity, String query) {
        int numOfInsert = 1;
        int result = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getFirstName());
            ps.setString(4, entity.getLastName());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == numOfInsert;
    }
}

