package me.predatorray.jdbc.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MasterSlaveDataSource extends AbstractDataSource {

    private final DataSource masterSlaveDataSource;

    public MasterSlaveDataSource(DataSource master,
                                 Collection<? extends DataSource>
                                         slaves) {
        // join all data sources as read replicas
        Collection<DataSource> masterAndSlaves = new
                ArrayList<DataSource>(slaves.size() + 1);
        masterAndSlaves.add(master);
        masterAndSlaves.addAll(slaves);

        // each node is the substitution for another according to the list
        // order
        Collection<SubstitutableDataSource> substitutableDataSources =
                new SubstitutableDataSourceCycle(masterAndSlaves);

        // round-robin replicas
        RoundRobinDataSource roundRobinReads =
                new RoundRobinDataSource(substitutableDataSources);
        // split read (master) and write (replicas)
        masterSlaveDataSource = new ReplicationDataSource(master,
                roundRobinReads);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return masterSlaveDataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return masterSlaveDataSource.getConnection(username, password);
    }
}
