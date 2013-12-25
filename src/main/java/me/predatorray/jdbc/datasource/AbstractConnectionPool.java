package me.predatorray.jdbc.datasource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractConnectionPool implements ConnectionPool {

    private final boolean defaultAutoCommit;
    private final int defaultTransactionIsolation;
    private final boolean defaultReadOnly;
    private final String defaultCategory;

    public AbstractConnectionPool(boolean defaultAutoCommit,
                                  int defaultTransactionIsolation,
                                  boolean defaultReadOnly,
                                  String defaultCategory) {
        this.defaultAutoCommit = defaultAutoCommit;
        this.defaultTransactionIsolation = defaultTransactionIsolation;
        this.defaultReadOnly = defaultReadOnly;
        this.defaultCategory = defaultCategory;
    }

    protected void reinitialize(Connection connection) throws SQLException {
        connection.rollback();
        connection.setAutoCommit(defaultAutoCommit);
        connection.setTransactionIsolation(defaultTransactionIsolation);
        connection.setReadOnly(defaultReadOnly);
        connection.setCatalog(defaultCategory);
    }

    protected void drop(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
