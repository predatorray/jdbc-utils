package me.predatorray.jdbc.datasource;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolableConnection extends ConnectionProxy {

    private final ConnectionPool connectionPool;

    public PoolableConnection(Connection connectionToBePooled,
                              ConnectionPool connectionPool) {
        super(connectionToBePooled);
        this.connectionPool = connectionPool;
    }

    @Override
    public void close() throws SQLException {
        try {
            connectionPool.returnConnection(this);
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
    }
}
