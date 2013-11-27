package me.predatorray.jdbc;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

abstract class ConnectionWrapper implements Connection {

    private final Connection wrapped;

    public ConnectionWrapper(Connection wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return wrapped.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        wrapped.setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return wrapped.getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        wrapped.commit();
    }

    @Override
    public void rollback() throws SQLException {
        wrapped.rollback();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return wrapped.isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return wrapped.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        wrapped.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return wrapped.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        wrapped.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        return wrapped.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        wrapped.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return wrapped.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return wrapped.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        wrapped.clearWarnings();
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return wrapped.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        wrapped.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        wrapped.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return wrapped.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return wrapped.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return wrapped.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        wrapped.rollback();
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        wrapped.releaseSavepoint(savepoint);
    }

    @Override
    public Clob createClob() throws SQLException {
        return wrapped.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return wrapped.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return wrapped.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return wrapped.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return wrapped.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value)
            throws SQLClientInfoException {
        wrapped.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties)
            throws SQLClientInfoException {
        wrapped.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return wrapped.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return wrapped.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements)
            throws SQLException {
        return wrapped.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes)
            throws SQLException {
        return wrapped.createStruct(typeName, attributes);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return wrapped.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return wrapped.isWrapperFor(iface);
    }
}
