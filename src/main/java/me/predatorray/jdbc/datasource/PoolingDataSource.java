package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class PoolingDataSource implements DataSource {

    private final DataSource originalDataSource;
    private final ConnectionPool connectionPool;

    // TODO pass connectionPool instance enabled

    public PoolingDataSource(DataSource dataSource, int initialSize,
                             int maxSize, boolean defaultAutoCommit,
                             int defaultTransactionIsolation,
                             String defaultCategory) throws SQLException {
        Check.argumentIsNotNull(dataSource, "dataSource must not be null");
        this.originalDataSource = dataSource;

        connectionPool = new QueuedConnectionPool(dataSource, initialSize,
                maxSize, defaultAutoCommit, defaultTransactionIsolation,
                defaultCategory);
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return connectionPool.borrowConnection();
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return originalDataSource.getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return originalDataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        originalDataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        originalDataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return originalDataSource.getLoginTimeout();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface == null) {
            throw new SQLException("iface must not be null");
        }
        if (!iface.isInstance(originalDataSource)) {
            throw new SQLException(String.format(
                    "no object found that implements the interface: %s",
                    iface));
        }
        return (T) originalDataSource;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(originalDataSource);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return originalDataSource.getParentLogger();
    }
}
