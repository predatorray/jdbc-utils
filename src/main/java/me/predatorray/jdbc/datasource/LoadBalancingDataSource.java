package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class LoadBalancingDataSource extends AbstractDataSource {

    private final int[] normalizedBorderValue;
    private final DataSource[] dataSources;
    private final int maxBorder;
    private RandomGenerator randomGenerator = new DefaultRandomGenerator();

    public LoadBalancingDataSource(Map<? extends DataSource,
            Integer> dataSourcesWeightMap) {
        Check.argumentIsNotNull(dataSourcesWeightMap,
                "dataSourcesWeight should not be null");
        final int mapSize = dataSourcesWeightMap.size();
        normalizedBorderValue = new int[mapSize];
        dataSources = new DataSource[mapSize];

        int borderValue = 0;
        int index = 0;
        for (Map.Entry<? extends DataSource, Integer> dataSourceWeight :
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

    public LoadBalancingDataSource(
            Collection<? extends DataSource> dataSourceCollection) {
        final int listSize = dataSourceCollection.size();
        normalizedBorderValue = null;
        dataSources = new DataSource[listSize];
        int i = 0;
        for (DataSource dataSource : dataSourceCollection) {
            dataSources[i] = dataSource;
            ++i;
        }
        maxBorder = listSize;
    }

    public void setRandomGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
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
        int randomBorder = randomGenerator.nextInt() % maxBorder;
        if (normalizedBorderValue == null) { // average
            return dataSources[randomBorder];
        }

        for (int i = 0; i < normalizedBorderValue.length; ++i) {
            int borderValue = normalizedBorderValue[i];
            if (randomBorder < borderValue) {
                return dataSources[i];
            }
        }
        return dataSources[0];
    }

    private static class DefaultRandomGenerator implements RandomGenerator {

        private Random random = new Random();

        @Override
        public int nextInt() {
            return random.nextInt();
        }
    }
}
