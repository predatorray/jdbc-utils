package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ReplicationDataSource extends AbstractDataSource {

    private final DataSource readWriteDs;
    private final DataSource readOnlyDs;

    public ReplicationDataSource(DataSource readWriteDs,
                                 DataSource readOnlyDs) {
        Check.argumentIsNotNull(readWriteDs, "readWriteDs cannot be null");
        Check.argumentIsNotNull(readOnlyDs, "readOnlyDs cannot be null");
        this.readWriteDs = readWriteDs;
        this.readOnlyDs = readOnlyDs;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new ReplicationConnection(
                readWriteDs.getConnection(),
                readOnlyDs.getConnection());
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return new ReplicationConnection(
                readWriteDs.getConnection(username, password),
                readOnlyDs.getConnection(username, password));
    }
}
