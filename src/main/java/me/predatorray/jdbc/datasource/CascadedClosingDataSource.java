package me.predatorray.jdbc.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Copyright (c) 2013 Ray <predator.ray@gmail.com>
 */
public class CascadedClosingDataSource extends AbstractDataSource {

    private DataSource dataSource;

    public CascadedClosingDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new CascadedClosingConnection(dataSource.getConnection());
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return new CascadedClosingConnection(dataSource
                .getConnection(username, password));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (DataSource.class.equals(iface)) {
            return (T) dataSource;
        }
        return super.unwrap(iface);
    }
}
