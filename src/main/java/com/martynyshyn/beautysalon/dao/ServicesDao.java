package com.martynyshyn.beautysalon.dao;

import com.martynyshyn.beautysalon.model.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for entity Service.
 *
 * @author N.Martynyshyn
 */


public class ServicesDao {
    private static final String FIND_SERVICES_BY_ID = "SELECT id, name, price, speciality_id FROM servicelist WHERE id=?";
    private static final String FIND_SERVICES_BY_SPECIALITY = "SELECT id,name,price,speciality_id FROM servicelist WHERE speciality_id=?";

    /**
     * Returns services can be used filter.
     *
     * @return List of Service entity.
     */
    public List<Service> findWithFilters(String sqlQuery) {
        List<Service> allServices = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                int colIndex = 1;

                allServices.add(new Service.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setName(resultSet.getString(colIndex++))
                        .setPrice(resultSet.getInt(colIndex++))
                        .setSpeciality_id(resultSet.getInt(colIndex))
                        .build());
            }

        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return allServices;
    }

    /**
     * Returns service with the given identifier.
     *
     * @param searchId Service identifier.
     * @return Service entity.
     */

    public Service findById(int searchId) {
        Service findService = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_SERVICES_BY_ID)) {

            ps.setInt(1, searchId);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int colIndex = 1;

                findService = new Service.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setName(resultSet.getString(colIndex++))
                        .setPrice(resultSet.getInt(colIndex++))
                        .setSpeciality_id(resultSet.getInt(colIndex))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findService;
    }

    public Service findById(int searchId, Connection connection) {
        Service findService = null;

        try (PreparedStatement ps = connection.prepareStatement(FIND_SERVICES_BY_ID)) {

            ps.setInt(1, searchId);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int colIndex = 1;

                findService = new Service.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setName(resultSet.getString(colIndex++))
                        .setPrice(resultSet.getInt(colIndex++))
                        .setSpeciality_id(resultSet.getInt(colIndex))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findService;
    }

    /**
     * Returns service with the given speciality identifier.
     *
     * @param specialId Speciality identifier.
     * @return List of Service entity.
     */

    public List<Service> findBySpeciality(int specialId) {
        List<Service> findServices = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_SERVICES_BY_SPECIALITY)) {

            ps.setInt(1, specialId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int colIndex = 1;

                findServices.add(new Service.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setName(resultSet.getString(colIndex++))
                        .setPrice(resultSet.getInt(colIndex++))
                        .setSpeciality_id(resultSet.getInt(colIndex))
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findServices;
    }
}