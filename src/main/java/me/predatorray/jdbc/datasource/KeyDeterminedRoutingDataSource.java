package me.predatorray.jdbc.datasource;

import javax.sql.DataSource;
import java.util.Map;

public abstract class KeyDeterminedRoutingDataSource
        extends RoutingDataSource {

    public KeyDeterminedRoutingDataSource(
            Map<?, ? extends DataSource> dataSourcesMap) {
        super(dataSourcesMap);
    }

    public KeyDeterminedRoutingDataSource(
            Map<?, ? extends DataSource> dataSourcesMap,
            DataSource defaultDataSource) {
        super(dataSourcesMap, defaultDataSource);
    }

    @Override
    protected final DataSource getTargetDataSource(
            Map<Object, DataSource> availableDataSourcesMap) throws Exception {
        return availableDataSourcesMap.get(getTargetKey());
    }

    protected abstract Object getTargetKey() throws Exception;
}
