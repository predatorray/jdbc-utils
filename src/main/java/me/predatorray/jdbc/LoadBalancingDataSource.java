package me.predatorray.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class LoadBalancingDataSource extends AbstractDataSource {

    private final int[] normalizedBorderValue;
    private final DataSource[] dataSources;
    private final int maxBorder;

    public LoadBalancingDataSource(Map<DataSource,
            Integer> dataSourcesWeightMap) {
        Check.argumentIsNotNull(dataSourcesWeightMap,
                "dataSourcesWeight should not be null");
        final int mapSize = dataSourcesWeightMap.size();
        normalizedBorderValue = new int[mapSize];
        dataSources = new DataSource[mapSize];

        int borderValue = 0;
        int index = 0;
        for (Map.Entry<DataSource, Integer> dataSourceWeight :
                dataSourcesWeightMap.entrySet()) {
            int weight = dataSourceWeight.getValue();
            Check.argumentIsValid(weight > 0, String.format("Weight [%d]" +
                    " should be a positive value.", weight));
            borderValue += weight;
            DataSource dataSource = dataSourceWeight.getKey();

            normalizedBorderValue[index] = borderValue;
            dataSources[index] = dataSource;

            ++index;
        }
        maxBorder = borderValue;
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        return getDataSourceRandomly().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return getDataSourceRandomly().getConnection(username, password);
    }

    private DataSource getDataSourceRandomly() {
        int randomBorder = new Random().nextInt() % maxBorder;
        for (int i = 0; i < normalizedBorderValue.length; ++i) {
            int borderValue = normalizedBorderValue[i];
            if (randomBorder < borderValue) {
                return dataSources[i];
            }
        }
        return dataSources[0];
    }
}
