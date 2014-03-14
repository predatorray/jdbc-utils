package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class LoadBalancingDataSource extends AbstractDataSource {

    private final DataSource[] dataSources;
    private final LoadBalancingStrategy strategy;

    public LoadBalancingDataSource(Collection<? extends DataSource> c,
                                   LoadBalancingStrategy strategy) {
        Check.argumentIsNotNull(c, "dataSource collection must not be null");
        Check.argumentIsValid(c.size() > 0,
                "the dataSource collection must contain at least one item");
        Check.argumentIsNotNull(strategy, "strategy must not be null");
        dataSources = c.toArray(new DataSource[c.size()]);
        this.strategy = strategy;
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
        return dataSources[strategy.next()];
    }
}
