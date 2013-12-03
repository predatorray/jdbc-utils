package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2013 Ray <predator.ray@gmail.com>
 */
public class SubstitutableDataSource extends AbstractDataSource {

    private final DataSource dataSource;
    private DataSource substitution;
    private boolean infiniteLoopDetectionEnabled = true;
    private boolean exceptionOmitted = true;
    private ThreadLocal<Set<SubstitutableDataSource>> dataSourceErrorSet =
            new ThreadLocal<Set<SubstitutableDataSource>>() {
                @Override
                protected Set<SubstitutableDataSource> initialValue() {
                    return new HashSet<SubstitutableDataSource>();
                }
            };

    public SubstitutableDataSource(DataSource dataSource) {
        Check.argumentIsNotNull(dataSource, "dataSource cannot be null");
        this.dataSource = dataSource;
    }

    public void setSubstitution(DataSource substitution) {
        this.substitution = substitution;
    }

    public void setInfiniteLoopDetectionEnabled(
            boolean infiniteLoopDetectionEnabled) {
        this.infiniteLoopDetectionEnabled = infiniteLoopDetectionEnabled;
    }

    public boolean isInfiniteLoopDetectionEnabled() {
        return infiniteLoopDetectionEnabled;
    }

    public boolean isExceptionOmitted() {
        return exceptionOmitted;
    }

    public void setExceptionOmitted(boolean exceptionOmitted) {
        this.exceptionOmitted = exceptionOmitted;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Connection conn = dataSource.getConnection();
            afterConnectionReturned();
            return conn;
        } catch (SQLException ex) {
            afterExceptionOccurred(ex);
            return substitution.getConnection();
        }
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        try {
            Connection conn = dataSource.getConnection(username, password);
            afterConnectionReturned();
            return conn;
        } catch (SQLException ex) {
            afterExceptionOccurred(ex);
            return substitution.getConnection(username, password);
        }
    }

    private void afterConnectionReturned() {
        if (infiniteLoopDetectionEnabled) {
            dataSourceErrorSet.get().clear();
        }
    }

    private void afterExceptionOccurred(SQLException ex) throws SQLException {
        if (substitution == null
                || dataSourceErrorSet.get().contains(this)) {
            throw ex;
        }
        if (infiniteLoopDetectionEnabled) {
            dataSourceErrorSet.get().add(this);
        }
        if (!exceptionOmitted) {
            ex.printStackTrace();
        }
    }
}
