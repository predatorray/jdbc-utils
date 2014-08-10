package me.predatorray.jdbc;

import me.predatorray.jdbc.datasource.CloseOnCompletionConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DefaultConnectionManager implements ConnectionManager {

    @Override
    public Connection getConnection(DataSource dataSource) throws DataAccessException {
        try {
            Connection connection = dataSource.getConnection();
            return new CloseOnCompletionConnection(connection);
        } catch (SQLException ex) {
            throw new DataAccessException(
                    "failed to get a connection from the data source", ex);
        }
    }

    @Override
    public void closeConnection(Connection connection) throws DataAccessException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new DataAccessException(
                    "failed to close the connection", ex);
        }
    }

    @Override
    public void rollbackConnection(Connection connection) throws DataAccessException {
        try {
            if (!connection.getAutoCommit()) {
                connection.rollback();
            }
        } catch (SQLException ex) {
            throw new DataAccessException(
                    "failed to rollback the connection", ex);
        }
    }
}
