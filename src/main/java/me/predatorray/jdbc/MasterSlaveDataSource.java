package me.predatorray.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MasterSlaveDataSource extends AbstractDataSource {

    private final DataSource masterSlaveDataSource;

    public MasterSlaveDataSource(DataSource master,
                                 Collection<? extends DataSource>
                                         dataSources) {
        // join all data sources as read replicas
        Collection<DataSource> masterAndSlaves = new
                ArrayList<DataSource>(dataSources.size() + 1);
        masterAndSlaves.add(master);
        masterAndSlaves.addAll(dataSources);

        // each node is the substitution for another according to the list
        // order
        new SubstitutableDataSourceCycle(masterAndSlaves);

        // round-robin replicas
        RoundRobinDataSource roundRobinReads =
                new RoundRobinDataSource(masterAndSlaves);
        // split read (master) and write (replicas)
        masterSlaveDataSource = new ReadWriteSplitDataSource(master,
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
