package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Test;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2013 Ray <predator.ray@gmail.com>
 */
public class LoadBalancingDataSourceTest {

    @Test
    public void testWrapperCall() throws Exception {
        DataSource ds = mock(DataSource.class);
        LoadBalancingDataSource lDs = new LoadBalancingDataSource(
                Arrays.asList(ds));
        lDs.getConnection();

        verify(ds).getConnection();
    }

    @Test
    public void testLoadBalancing1() throws Exception {
        RandomGenerator generator = mock(RandomGenerator.class);
        when(generator.nextInt())
                .thenReturn(0)
                .thenReturn(1);
        DataSource ds1 = mock(DataSource.class);
        DataSource ds2 = mock(DataSource.class);
        LoadBalancingDataSource lDs = new LoadBalancingDataSource(
                Arrays.asList(ds1, ds2));
        lDs.setRandomGenerator(generator);
        lDs.getConnection();
        lDs.getConnection();

        verify(ds1).getConnection();
        verify(ds2).getConnection();
    }

    @Test
    public void testLoadBalancing2() throws Exception {
        RandomGenerator generator = mock(RandomGenerator.class);
        when(generator.nextInt())
                .thenReturn(0)
                .thenReturn(1)
                .thenReturn(2);

        final DataSource ds1 = mock(DataSource.class);
        final DataSource ds2 = mock(DataSource.class);
        LoadBalancingDataSource lDs = new LoadBalancingDataSource(
                new HashMap<DataSource, Integer>(2) {
                    {
                        this.put(ds1, 1);
                        this.put(ds2, 2);
                    }
                });
        lDs.setRandomGenerator(generator);

        lDs.getConnection();
        lDs.getConnection();
        lDs.getConnection();

        verify(ds1, times(1)).getConnection();
        verify(ds2, times(2)).getConnection();
    }
}
