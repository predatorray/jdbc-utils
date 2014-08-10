package me.predatorray.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A thread-local transactional connection manager. Each thread can get only one connection from this manager. The
 * transaction begins when a thread gets the connection from the data source at the first time.
 */
public class TransactionalManager implements ConnectionManager {

    private final ConnectionManager wrapped;
    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

    public TransactionalManager(ConnectionManager wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Connection getConnection(DataSource dataSource) throws DataAccessException {
        Connection localConn = connectionHolder.get();
        if (localConn != null) {
            return localConn;
        }

        Connection newConn = wrapped.getConnection(dataSource);
        try {
            newConn.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        connectionHolder.set(newConn);
        return newConn;
    }

    @Override
    public void closeConnection(Connection connection) throws DataAccessException {
    }

    @Override
    public void rollbackConnection(Connection connection) throws DataAccessException {
        connectionHolder.remove();
        wrapped.rollbackConnection(connection);
    }

    public void commit() throws DataAccessException {
        Connection curr = connectionHolder.get();
        if (curr == null) {
            throw new IllegalStateException(String.format(
                    "No active connection is held by this thread [%s].",
                    Thread.currentThread().getName()));
        }

        try {
            curr.commit();
        } catch (SQLException e) {
            throw new DataAccessException("failed to commit the transaction", e);
        } finally {
            connectionHolder.remove();
            try {
                curr.close();
            } catch (SQLException e) {
                throw new DataAccessException("failed to close the connection");
            }
        }
    }
}
