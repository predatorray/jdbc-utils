package me.predatorray.jdbc.datasource;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CloseOnCompletionCallableStatement
        extends CallableStatementProxy {

    private final Queue<ResultSet> resultSetsToBeClosed;

    public CloseOnCompletionCallableStatement(
            CallableStatement callableStatement) {
        super(callableStatement);
        resultSetsToBeClosed = new ConcurrentLinkedQueue<ResultSet>();
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        ResultSet resultSetToBeClosed = super.executeQuery();
        resultSetsToBeClosed.add(resultSetToBeClosed);
        return resultSetToBeClosed;
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        ResultSet resultSetToBeClosed = super.executeQuery(sql);
        resultSetsToBeClosed.add(resultSetToBeClosed);
        return resultSetToBeClosed;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        ResultSet resultSetToBeClosed = super.getResultSet();
        resultSetsToBeClosed.add(resultSetToBeClosed);
        return resultSetToBeClosed;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        ResultSet resultSetToBeClosed = super.getGeneratedKeys();
        resultSetsToBeClosed.add(resultSetToBeClosed);
        return resultSetToBeClosed;
    }

    @Override
    public boolean isCloseOnCompletion() {
        return true;
    }

    @Override
    public void close() throws SQLException {
        try {
            for (ResultSet resultSetToBeClosed : resultSetsToBeClosed) {
                if (!resultSetToBeClosed.isClosed()) {
                    resultSetToBeClosed.close();
                }
            }
        } finally {
            super.close();
        }
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
