package me.predatorray.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;

public interface ConnectionManager {

    /**
     * the factory method of the connection from the data source.
     * @param dataSource the dataSource to get a connection from
     * @return a connection from the data source.
     */
    Connection getConnection(DataSource dataSource) throws DataAccessException;

    /**
     * the tear down method is used to close a connection as soon as the task
     * has either been successfully executed or failed with an exception.
     * @param connection the connection to be closed
     * @throws me.predatorray.jdbc.DataAccessException if SQLException is thrown during the close
     * phrase.
     */
    void closeConnection(Connection connection) throws DataAccessException;

    /**
     * this method is used to rollback a connection if SQLException is thrown
     * during the execution. But when the auto-commit is not disabled
     * (by default), the <code>connection.rollback()</code> will not be
     * executed.
     * @param connection the connection to rollback
     * @throws DataAccessException if SQLException is thrown during the
     * rollback
     */
    void rollbackConnection(Connection connection) throws DataAccessException;
}
