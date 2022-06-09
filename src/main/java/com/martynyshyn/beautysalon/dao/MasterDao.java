package com.martynyshyn.beautysalon.dao;

import com.martynyshyn.beautysalon.model.Master;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for entity Master.
 *
 * @author N.Martynyshyn
 */

public class MasterDao implements UserEntityCreate {
    static final Logger logger = LogManager.getLogger(MasterDao.class);

    private static final String FIND_ALL_MASTER_QUERY = "SELECT id, firstname, lastname, speciality_id FROM employee";
    private static final String FIND_MASTER_BY_EMAIL = "SELECT id, email,firstName,lastName, password, role_id FROM employee WHERE email=?";
    private static final String FIND_MASTER_BY_ID = "SELECT id, email, firstname, lastname, rate, speciality_id, role_id FROM employee WHERE id=?";

    /**
     * Returns the entire list of masters.
     *
     * @return List<Master>.
     */

    public List<Master> findAll() {
        List<Master> allMasters = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(FIND_ALL_MASTER_QUERY);
            while (resultSet.next()) {
                int colIndex = 1;
                allMasters.add(new Master.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setFirstName(resultSet.getString(colIndex++))
                        .setLastName(resultSet.getString(colIndex++))
                        .setSpecialityId(resultSet.getInt(colIndex))
                        .build());
            }
        } catch (SQLException exp) {
            logger.error("Method findAll" + exp.getMessage());
        }
        return allMasters;
    }

    /**
     * Returns sorted entire list of masters.
     *
     * @param locale   Users locale, required to localized data.
     * @param sqlQuery Query with sort and filters param.
     * @return List<Master>.
     */
    public List<Master> findMastersWithSortAndFilters(String locale, String sqlQuery) {
        List<Master> allMasters = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                int colIndex = 1;
                allMasters.add(new Master.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setEmail(resultSet.getString(colIndex++))
                        .setFirstName(resultSet.getString(colIndex++))
                        .setLastName(resultSet.getString(colIndex++))
                        .setRate(resultSet.getDouble(colIndex++))
                        .setSpecialityId(resultSet.getInt(colIndex))
                        .setSpecialityName(new SpecialityDao().getSpecialityNameById(resultSet.getInt(colIndex), locale,connection))
                        .build());
            }
        } catch (SQLException exp) {
            logger.error("Method findAll" + exp.getMessage());
        }
        return allMasters;
    }

    /**
     * Returns a user with the given identifier.
     *
     * @param searchId Master identifier.
     * @return Master entity.
     */
    public Master findById(int searchId) {
        Master findMaster = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_MASTER_BY_ID)) {

            ps.setInt(1, searchId);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int colIndex = 1;

                findMaster = new Master.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setEmail(resultSet.getString(colIndex++))
                        .setFirstName(resultSet.getString(colIndex++))
                        .setLastName(resultSet.getString(colIndex++))
                        .setRate(resultSet.getDouble(colIndex++))
                        .setSpecialityId(resultSet.getInt(colIndex++))
                        .setRoleId(resultSet.getInt(colIndex))
                        .build();
            }

        } catch (SQLException e) {
            logger.error("Method findById" + e.getMessage());
        }
        return findMaster;
    }

    public Master findById(int searchId,Connection connection) {
        Master findMaster = null;

        try (PreparedStatement ps = connection.prepareStatement(FIND_MASTER_BY_ID)) {

            ps.setInt(1, searchId);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int colIndex = 1;

                findMaster = new Master.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setEmail(resultSet.getString(colIndex++))
                        .setFirstName(resultSet.getString(colIndex++))
                        .setLastName(resultSet.getString(colIndex++))
                        .setRate(resultSet.getDouble(colIndex++))
                        .setSpecialityId(resultSet.getInt(colIndex++))
                        .setRoleId(resultSet.getInt(colIndex))
                        .build();
            }

        } catch (SQLException e) {
            logger.error("Method findById" + e.getMessage());
        }
        return findMaster;
    }

    /**
     * Returns a user with the given email.
     *
     * @param emailPattern Master email.
     * @return Master entity.
     */
    public Master findMasterByEmail(String emailPattern) {
        Master findMaster = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_MASTER_BY_EMAIL)) {
            ps.setString(1, emailPattern);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int colIndex = 1;

                findMaster = new Master.Builder()
                        .setId(resultSet.getInt(colIndex++))
                        .setEmail(resultSet.getString(colIndex++))
                        .setFirstName(resultSet.getString(colIndex++))
                        .setLastName(resultSet.getString(colIndex++))
                        .setPassword(resultSet.getString(colIndex++))
                        .setRoleId(resultSet.getInt(colIndex))
                        .build();
            }

        } catch (SQLException e) {
            logger.error("Method findMasterByEmail" + e.getMessage());
        }
        return findMaster;
    }
}
