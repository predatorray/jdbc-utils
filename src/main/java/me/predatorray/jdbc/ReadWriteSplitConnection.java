package me.predatorray.jdbc;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

class ReadWriteSplitConnection implements Connection {

    private Connection currentConn;
    private final Connection readWriteConn;
    private final Connection readOnlyConn;

    public ReadWriteSplitConnection(Connection readWriteConn,
                                    Connection readOnlyConn) {
        Check.argumentIsNotNull(readWriteConn, "connection cannot be null");
        Check.argumentIsNotNull(readOnlyConn, "connection cannot be null");

        this.readWriteConn = readWriteConn;
        this.readOnlyConn = readOnlyConn;
        currentConn = this.readWriteConn;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return currentConn.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return currentConn.prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return currentConn.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return currentConn.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        currentConn.setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return currentConn.getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        currentConn.commit();
    }

    @Override
    public void rollback() throws SQLException {
        currentConn.rollback();
    }

    @Override
    public void close() throws SQLException {
        readWriteConn.close();
        readOnlyConn.close();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return currentConn.isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return currentConn.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        if (readOnly) {
            if (currentConn != readOnlyConn) {
                currentConn = readOnlyConn;
            }
        } else {
            if (currentConn != readWriteConn) {
                currentConn = readWriteConn;
            }
        }
        currentConn.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return currentConn.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        currentConn.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        return currentConn.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        currentConn.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return currentConn.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return currentConn.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        currentConn.clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType,
                                     int resultSetConcurrency)
            throws SQLException {
        return currentConn.createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
                                              int resultSetConcurrency)
            throws SQLException {
        return currentConn.prepareStatement(sql, resultSetType,
                resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType,
                                         int resultSetConcurrency)
            throws SQLException {
        return currentConn.prepareCall(sql, resultSetType,
                resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return currentConn.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        currentConn.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        currentConn.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return currentConn.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return currentConn.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return currentConn.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        currentConn.rollback();
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        currentConn.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType,
                                     int resultSetConcurrency,
                                     int resultSetHoldability)
            throws SQLException {
        return currentConn.createStatement(resultSetType, resultSetConcurrency,
                resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
                                              int resultSetConcurrency,
                                              int resultSetHoldability)
            throws SQLException {
        return currentConn.prepareStatement(sql, resultSetType,
                resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType,
                                         int resultSetConcurrency,
                                         int resultSetHoldability)
            throws SQLException {
        return currentConn.prepareCall(sql, resultSetType, resultSetConcurrency,
                resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SQLException {
        return currentConn.prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
            throws SQLException {
        return currentConn.prepareStatement(sql, columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames)
            throws SQLException {
        return currentConn.prepareStatement(sql, columnNames);
    }

    @Override
    public Clob createClob() throws SQLException {
        return currentConn.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return currentConn.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return currentConn.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return currentConn.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return currentConn.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value)
            throws SQLClientInfoException {
        currentConn.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties)
            throws SQLClientInfoException {
        currentConn.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return currentConn.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return currentConn.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements)
            throws SQLException {
        return currentConn.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes)
            throws SQLException {
        return currentConn.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        currentConn.setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException {
        return currentConn.getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        currentConn.abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds)
            throws SQLException {
        currentConn.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return currentConn.getNetworkTimeout();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return (T) currentConn;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return currentConn.isWrapperFor(iface);
    }
}
