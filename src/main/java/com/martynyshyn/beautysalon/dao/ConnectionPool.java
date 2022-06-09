package com.martynyshyn.beautysalon.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Connection pool. Works with mySQL.
 *
 * @author N.Martynyshyn
 *
 */

public class ConnectionPool {

    //singleton
    private static ConnectionPool instance;

    public static synchronized ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    private ConnectionPool() {
    }

    /**
     * Returns a DB connection from the Pool Connections.
     *
     * @return DB connection.
     */

    public Connection getConnection() {
        Connection con = null;
        try {
            Context initContext = new InitialContext();

            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/beautysalon");

            con = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }
}
