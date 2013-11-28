package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinDataSource extends AbstractDataSource {

    private AtomicInteger counter = new AtomicInteger(0);
    private final DataSource[] dataSources;
    private final int sizeOfDataSources;

    public RoundRobinDataSource(Collection<? extends DataSource>
            dataSourceCollection) {
        Check.argumentIsNotNull(dataSourceCollection, "dataSourceCollection " +
                "should not be null");
        Check.argumentIsValid(dataSourceCollection.size() > 0,
                "the dataSourceCollection should contain at least one item");

        sizeOfDataSources = dataSourceCollection.size();
        dataSources = new DataSource[sizeOfDataSources];
        int i = 0;
        for (DataSource dataSource : dataSourceCollection) {
            dataSources[i] = dataSource;
            ++i;
        }
    }

    public RoundRobinDataSource(DataSource[] dataSources) {
        Check.argumentIsNotNull(dataSources, "dataSources should not be null");
        Check.argumentIsValid(dataSources.length > 0,
                "the dataSources should contain at least one item");

        sizeOfDataSources = dataSources.length;
        this.dataSources = dataSources.clone();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getNextDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return getNextDataSource().getConnection(username, password);
    }

    private DataSource getNextDataSource() {
        while (true) {
            int current = counter.get();
            int next = (current + 1) % sizeOfDataSources;
            if (counter.compareAndSet(current, next)) {
                return dataSources[current];
            }
        }
    }
}
