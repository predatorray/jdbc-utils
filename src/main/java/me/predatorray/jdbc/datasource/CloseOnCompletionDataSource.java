package me.predatorray.jdbc.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CloseOnCompletionDataSource extends AbstractDataSource {

    private DataSource dataSource;

    public CloseOnCompletionDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new CloseOnCompletionConnection(dataSource.getConnection());
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return new CloseOnCompletionConnection(dataSource
                .getConnection(username, password));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) dataSource;
        }
        return super.unwrap(iface);
    }
}
