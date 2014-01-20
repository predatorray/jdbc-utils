package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class RoutingDataSource extends AbstractDataSource {

    private final Map<Object, DataSource> availableDataSourcesMap;
    private final DataSource defaultDataSource;

    public RoutingDataSource(Map<?, ? extends DataSource> dataSourcesMap) {
        this(dataSourcesMap, null);
    }

    public RoutingDataSource(Map<?, ? extends DataSource> dataSourcesMap,
                             DataSource defaultDataSource) {
        Check.mapIsNotNullOrEmpty(dataSourcesMap,
                "dataSourcesMap must not be null or empty");
        availableDataSourcesMap = Collections.unmodifiableMap(
                new HashMap<Object, DataSource>(dataSourcesMap));
        this.defaultDataSource = defaultDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getTargetDataSourceOrThrowException().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return getTargetDataSourceOrThrowException().getConnection(
                username, password);
    }

    private DataSource getTargetDataSourceOrThrowException()
            throws SQLException {
        try {
            return getTargetDataSource(availableDataSourcesMap);
        } catch (Exception ex) {
            if (defaultDataSource == null) {
                throw new SQLException(ex);
            }
            return defaultDataSource;
        }
    }

    protected abstract DataSource getTargetDataSource(
            Map<Object, DataSource> availableDataSourcesMap) throws Exception;
}
