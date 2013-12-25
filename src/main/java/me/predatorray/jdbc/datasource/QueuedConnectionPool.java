package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class QueuedConnectionPool extends AbstractConnectionPool {

    private final DataSource dataSource;
    private final int maxSize;
    private final Queue<Connection> idleConnections;

    private final Lock idleConnsLock = new ReentrantLock();

    public QueuedConnectionPool(DataSource dataSource, int initialSize,
                                int maxSize, boolean defaultAutoCommit,
                                int defaultTransactionIsolation,
                                boolean defaultReadOnly,
                                String defaultCategory) throws SQLException {
        super(defaultAutoCommit, defaultTransactionIsolation,
                defaultReadOnly, defaultCategory);
        Check.argumentIsNotNull(dataSource, "dataSource must not be null");
        Check.argumentIsValid(initialSize >= 0,
                "initialSize must not be negative");
        Check.argumentIsValid(maxSize >= 0,
                "maxSize must not be negative");
        Check.argumentIsValid(maxSize >= initialSize,
                "maxSize must be equal or greater than initialSize");

        this.dataSource = dataSource;
        this.maxSize = maxSize;
        idleConnections = new ConcurrentLinkedQueue<Connection>();

        for (int i = 0; i < initialSize; ++i) {
            idleConnections.add(dataSource.getConnection());
        }
    }

    @Override
    public Connection borrowConnection() throws Exception {
        idleConnsLock.lock();
        try {
            return (idleConnections.isEmpty())
                    ? dataSource.getConnection()
                    : idleConnections.poll();
        } finally {
            idleConnsLock.unlock();
        }
    }

    @Override
    public void returnConnection(Connection connection) throws Exception {
        Check.argumentIsNotNull(connection, "connection must not be null");

        if (connection.isClosed()) {
            return;
        }
        reinitialize(connection);

        boolean drop = false;

        idleConnsLock.lock();
        try {
            if (idleConnections.size() >= maxSize) {
                drop = true;
            } else {
                idleConnections.add(connection);
            }
        } finally {
            idleConnsLock.unlock();
        }

        if (drop) {
            drop(connection);
        }
    }
}
