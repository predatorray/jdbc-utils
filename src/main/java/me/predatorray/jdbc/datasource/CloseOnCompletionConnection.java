package me.predatorray.jdbc.datasource;

import java.sql.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

/**
 *
 */
public class CloseOnCompletionConnection extends ConnectionProxy {

    private final Connection originalConnection;
    private final Queue<Statement> statementsToBeClosed;

    public CloseOnCompletionConnection(Connection originalConnection) {
        super(originalConnection);
        this.originalConnection = originalConnection;
        statementsToBeClosed = new ConcurrentLinkedQueue<Statement>();
    }

    @Override
    public Statement createStatement() throws SQLException {
        Statement statement = originalConnection.createStatement();
        statementsToBeClosed.add(new CloseOnCompletionStatement(statement));
        return statement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        PreparedStatement preparedStatement = originalConnection
                .prepareStatement(sql);
        statementsToBeClosed.add(new CloseOnCompletionPreparedStatement(
                preparedStatement));
        return preparedStatement;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        CallableStatement callableStatement = originalConnection.prepareCall
                (sql);
        statementsToBeClosed.add(new CloseOnCompletionCallableStatement(
                callableStatement));
        return callableStatement;
    }

    @Override
    public void close() throws SQLException {
        try {
            for (Statement statementToBeClosed : statementsToBeClosed) {
                if (!statementToBeClosed.isClosed()) {
                    statementToBeClosed.close();
                }
            }
        } finally {
            originalConnection.close();
        }
    }

    @Override
    public Statement createStatement(int resultSetType,
                                     int resultSetConcurrency)
            throws SQLException {
        Statement statement = originalConnection.createStatement
                (resultSetType, resultSetConcurrency);
        statementsToBeClosed.add(new CloseOnCompletionStatement(statement));
        return statement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
                                              int resultSetConcurrency)
            throws SQLException {
        PreparedStatement preparedStatement = originalConnection
                .prepareStatement(sql, resultSetType, resultSetConcurrency);
        statementsToBeClosed.add(new CloseOnCompletionPreparedStatement(
                preparedStatement));
        return preparedStatement;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType,
                                         int resultSetConcurrency)
            throws SQLException {
        CallableStatement callableStatement = originalConnection.prepareCall
                (sql, resultSetType, resultSetConcurrency);
        statementsToBeClosed.add(new CloseOnCompletionCallableStatement(
                callableStatement));
        return callableStatement;
    }

    @Override
    public Statement createStatement(int resultSetType,
                                     int resultSetConcurrency,
                                     int resultSetHoldability)
            throws SQLException {
        Statement statement = originalConnection.createStatement
                (resultSetType, resultSetConcurrency, resultSetHoldability);
        statementsToBeClosed.add(new CloseOnCompletionStatement(statement));
        return statement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
                                              int resultSetConcurrency,
                                              int resultSetHoldability)
            throws SQLException {
        PreparedStatement preparedStatement = originalConnection
                .prepareStatement(sql, resultSetType, resultSetConcurrency,
                        resultSetHoldability);
        statementsToBeClosed.add(new CloseOnCompletionPreparedStatement(
                preparedStatement));
        return preparedStatement;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType,
                                         int resultSetConcurrency,
                                         int resultSetHoldability)
            throws SQLException {
        CallableStatement callableStatement = originalConnection.prepareCall
                (sql, resultSetType, resultSetConcurrency,
                        resultSetHoldability);
        statementsToBeClosed.add(new CloseOnCompletionCallableStatement(
                callableStatement));
        return callableStatement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql,
                                              int autoGeneratedKeys)
            throws SQLException {
        PreparedStatement preparedStatement = originalConnection
                .prepareStatement(sql, autoGeneratedKeys);
        statementsToBeClosed.add(new CloseOnCompletionPreparedStatement(
                preparedStatement));
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
            throws SQLException {
        PreparedStatement preparedStatement = originalConnection
                .prepareStatement(sql, columnIndexes);
        statementsToBeClosed.add(new CloseOnCompletionPreparedStatement(
                preparedStatement));
        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames)
            throws SQLException {
        PreparedStatement preparedStatement = originalConnection
                .prepareStatement(sql, columnNames);
        statementsToBeClosed.add(new CloseOnCompletionPreparedStatement(
                preparedStatement));
        return preparedStatement;
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        originalConnection.setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException {
        return originalConnection.getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        originalConnection.abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds)
            throws SQLException {
        originalConnection.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return originalConnection.getNetworkTimeout();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (!isClosed()) {
                this.close();
            }
        } finally {
            super.finalize();
        }
    }
}
