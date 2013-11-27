package me.predatorray.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Copyright (c) 2013 Ray <predator.ray@gmail.com>
 */
public class SubstitutableDataSource extends AbstractDataSource {

    private DataSource dataSource;
    private DataSource substitution;

    public SubstitutableDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setSubstitution(DataSource substitution) {
        this.substitution = substitution;
    }

    @Override
    public Connection getConnection() throws SQLException {
        // TODO prevent a infinite loop
        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            if (substitution == null) {
                throw ex;
            }
            ex.printStackTrace();
            return substitution.getConnection();
        }
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        // TODO prevent a infinite loop
        try {
            return dataSource.getConnection(username, password);
        } catch (SQLException ex) {
            if (substitution == null) {
                throw ex;
            }
            ex.printStackTrace();
            return substitution.getConnection(username, password);
        }
    }
}
