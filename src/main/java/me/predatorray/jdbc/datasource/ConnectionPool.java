package me.predatorray.jdbc.datasource;

import java.sql.Connection;

/**
 * A pool of <code>Connection</code>s.
 */
public interface ConnectionPool {

    /**
     * Borrow a connection from this pool.
     * @return a connection if available.
     * @throws Exception
     */
    Connection borrowConnection() throws Exception;

    /**
     * Return a connection to this pool.
     * @param connection the connection be be returned
     * @throws Exception
     */
    void returnConnection(Connection connection) throws Exception;
}
