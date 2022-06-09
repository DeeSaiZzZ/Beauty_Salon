package com.martynyshyn.beautysalon.dao;

import com.martynyshyn.beautysalon.model.Order;
import com.martynyshyn.beautysalon.model.enums.StatusNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object for entity Order.
 *
 * @author N.Martynyshyn
 */

public class OrderDao {

    private static final String FIND_USERS_ORDER = "SELECT id,employ_id,service_id,user_id,date,time,status FROM orders" +
            "WHERE user_id=? AND orders.status!=? AND date>=? AND time>?";

    private static final String FIND_ORDERS_BY_DATE = "SELECT id,employ_id,service_id,user_id,date,time,status FROM orders " +
            "WHERE date=?";

    private static final String FIND_ORDERS_BY_DATE_AND_SERVICE = "SELECT id, employ_id,service_id ,user_id,date,time,status " +
            "FROM orders WHERE date=? AND service_id=? AND employ_id=? AND user_id IS NULL";

    private static final String FIND_ORDER_BY_PARAM = "SELECT id, employ_id,service_id ,user_id,date,time,status " +
            "FROM orders WHERE date=? AND service_id=? AND employ_id=? AND time=?";

    private static final String FIND_BY_ID = "SELECT id, employ_id,service_id ,user_id,date,time,status " +
            "FROM orders WHERE id=?";

    private static final String UPDATE_ORDER = "UPDATE orders SET employ_id=?,service_id=?,user_id=?,date=?,time=?," +
            "status=?, complete_date=? WHERE id=?";

    private final SimpleDateFormat formatDateNow = new SimpleDateFormat("yyyy.MM.dd");
    private final SimpleDateFormat formatTimeNow = new SimpleDateFormat("HH:mm");
    private static final int NO_ORDER_USER = 0;

    /**
     * Returns order with the given identifier.
     *
     * @param id Order identifier.
     * @return Order entity.
     */

    public Order findById(int id) {
        Order findOrder = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {

            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                findOrder = mapRow(resultSet, connection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findOrder;
    }

    /**
     * Returns orders with the given date.
     *
     * @param date Order date.
     * @return List of Order entity.
     */

    public List<Order> findOrdersByDate(String date) {

        List<Order> orderList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ORDERS_BY_DATE)) {

            ps.setString(1, date);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                orderList.add(mapRow(resultSet, connection));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    /**
     * Returns orders with the given user identifier.
     *
     * @param userId Order user_id.
     * @return List of Order entity.
     */

    public List<Order> findUserOrders(int userId) {
        List<Order> orderList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_USERS_ORDER)) {

            ps.setInt(1, userId);
            ps.setString(2, StatusNames.COMPLETE.getStatusName());
            ps.setString(3, formatDateNow.format(new Date()));
            ps.setString(4, formatTimeNow.format(new Date()));

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                orderList.add(mapRow(resultSet, connection));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    /**
     * Returns orders with the given service, master identifier and date.
     *
     * @param serviceId Order user_id.
     * @param masterId  Order master_id.
     * @param date      Order date.
     * @return List of Order entity.
     */

    public List<Order> findOrderByDateAndService(int serviceId, int masterId, String date) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ORDERS_BY_DATE_AND_SERVICE)) {

            ps.setString(1, date);
            ps.setInt(2, serviceId);
            ps.setInt(3, masterId);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                orders.add(mapRow(resultSet, connection));
            }

        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return orders;
    }

    /**
     * Returns orders with the given service, master identifier and date, time.
     *
     * @param serviceId Order user_id.
     * @param masterId  Order master_id.
     * @param date      Order date.
     * @param time      Order time.
     * @return List of Order entity.
     */

    public Order findOrderByParam(int serviceId, int masterId, String date, String time) {
        Order findOrder = null;

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ORDER_BY_PARAM)) {

            ps.setString(1, date);
            ps.setInt(2, serviceId);
            ps.setInt(3, masterId);
            ps.setString(4, time);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                findOrder = mapRow(resultSet, connection);
            }

        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        return findOrder;
    }

    /**
     * Update user entity.
     *
     * @param order Order entity for update.
     */

    public void update(Order order) {

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_ORDER)) {

            int colIndex = 1;

            ps.setInt(colIndex++, order.getOrderMaster().getId());
            ps.setInt(colIndex++, order.getOrderService().getId());

            if (order.getOrderUser().getId() == NO_ORDER_USER) {
                ps.setNull(colIndex++, 0);
            } else {
                ps.setInt(colIndex++, order.getOrderUser().getId());
            }

            ps.setString(colIndex++, order.getOrderDate());
            ps.setString(colIndex++, order.getOrderTime());
            ps.setString(colIndex++, order.getOrderStatus());
            if (order.getCompleteDate() == null) {
                ps.setNull(colIndex++, 91);
            } else {
                ps.setString(colIndex++, order.getCompleteDate());
            }
            ps.setInt(colIndex, order.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extract Order entity from resultSet.
     *
     * @param resultSet Result set for extract entity.
     * @return Order entity.
     */

    private Order mapRow(ResultSet resultSet, Connection connection) throws SQLException {

        int colIndex = 1;
        return new Order.Builder()
                .setId(resultSet.getInt(colIndex++))
                .setMaster(new MasterDao().findById(resultSet.getInt(colIndex++), connection))
                .setService(new ServicesDao().findById(resultSet.getInt(colIndex++),connection))
                .setUser(new UserDao().findById(resultSet.getInt(colIndex++),connection))
                .setDate(formatDateNow.format(resultSet.getDate(colIndex++)))
                .setTime(formatTimeNow.format(resultSet.getTime(colIndex++)))
                .setStatus(resultSet.getString(colIndex))
                .build();
    }
}