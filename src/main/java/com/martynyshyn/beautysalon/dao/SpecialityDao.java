package com.martynyshyn.beautysalon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object for extract master speciality name
 * whit locale
 *
 * @author N.Martynyshyn
 */

public class SpecialityDao {

    public String getSpecialityNameById(int id, String locale, Connection connection) {

        StringBuilder sqlQuery = new StringBuilder("SELECT Name");
        sqlQuery.append("_").append(locale).append(" FROM speciality WHERE id=?");

        String findName = null;

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery.toString())) {

            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                findName = resultSet.getString(1);
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return findName;
    }
}
