package me.predatorray.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;

public abstract class AbstractDataSource implements DataSource {

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("getLogWriter is not " +
                "supported");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("setLogWriter is not " +
                "supported");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("setLoginTimeout is not " +
                "supported");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        Check.argumentIsNotNull(iface, "Argument iface cannot be null.");
        if (getClass().equals(iface)) {
            throw new SQLException(String.format("no object found that " +
                    "implements the interface [%s]", iface));
        }
        return (T) this;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return DataSource.class.equals(iface);
    }
}