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

    public PoolingDataSource(DataSource originalDataSource, int initialSize,
                             int maxSize, boolean defaultAutoCommit,
                             int defaultTransactionIsolation,
                             boolean autoReadOnly,
                             String defaultCategory) throws SQLException {
        this(originalDataSource, new QueuedConnectionPool(originalDataSource,
                initialSize, maxSize, defaultAutoCommit,
                defaultTransactionIsolation, autoReadOnly, defaultCategory));
    }

    public PoolingDataSource(DataSource originalDataSource,
                             ConnectionPool connectionPool) {
        Check.argumentIsNotNull(originalDataSource,
                "originalDataSource must not be null");
        Check.argumentIsNotNull(connectionPool,
                "connectionPool must not be null");

        this.originalDataSource = originalDataSource;
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return new PoolableConnection(connectionPool.borrowConnection(),
                    connectionPool);
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
    }

    /**
     * Invoking this method will not retrieves the connection from the pool.
     * It will get the connection directly from the data source.
     * @param username the database user on whose behalf the connection is
     *                 being made
     * @param password the user's password
     * @return a connection from the data source
     * @exception SQLException if a database access error occurs
     */
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
