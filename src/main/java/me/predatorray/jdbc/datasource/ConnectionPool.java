package me.predatorray.jdbc.datasource;

import java.sql.Connection;

public interface ConnectionPool {

    Connection borrowConnection() throws Exception;

    void returnConnection(Connection connection) throws Exception;
}
