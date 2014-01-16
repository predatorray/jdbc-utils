package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.InOrder;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class LoadBalancingDataSourceTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullDataSourceCollection() {
        LoadBalancingStrategy strategy = mock(LoadBalancingStrategy.class);
        new LoadBalancingDataSource(null, strategy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithEmptyDataSourceCollection() {
        LoadBalancingStrategy strategy = mock(LoadBalancingStrategy.class);
        new LoadBalancingDataSource(Collections.<DataSource>emptyList(),
                strategy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullLoadBalancingStrategy() {
        DataSource dataSource = mock(DataSource.class);
        new LoadBalancingDataSource(Collections.singleton(dataSource), null);
    }

    @Test
    public void testGetDataSourceWithUsernameAndPassword1()
            throws SQLException {
        DataSource dataSource1 = mock(DataSource.class);
        DataSource dataSource2 = mock(DataSource.class);
        Collection<DataSource> dataSources = Arrays.asList(dataSource1,
                dataSource2);
        LoadBalancingStrategy strategy = mock(LoadBalancingStrategy.class);
        when(strategy.next()).thenReturn(0).thenReturn(1);
        LoadBalancingDataSource loadBalancingDataSource =
                new LoadBalancingDataSource(dataSources, strategy);
        String user = "u";
        String pass = "p";

        loadBalancingDataSource.getConnection(user, pass);

        verify(strategy).next();
        verify(dataSource1).getConnection(eq(user), eq(pass));
    }

    @Test
    public void testGetDataSourceWithUsernameAndPassword2()
            throws SQLException {
        DataSource dataSource1 = mock(DataSource.class);
        DataSource dataSource2 = mock(DataSource.class);
        Collection<DataSource> dataSources = Arrays.asList(dataSource1,
                dataSource2);
        LoadBalancingStrategy strategy = mock(LoadBalancingStrategy.class);
        when(strategy.next()).thenReturn(0).thenReturn(1);
        LoadBalancingDataSource loadBalancingDataSource =
                new LoadBalancingDataSource(dataSources, strategy);
        String user = "u";
        String pass = "p";

        loadBalancingDataSource.getConnection(user, pass);
        loadBalancingDataSource.getConnection(user, pass);

        verify(strategy, times(2)).next();
        InOrder inOrder = inOrder(dataSource1, dataSource2);
        inOrder.verify(dataSource1).getConnection(eq(user), eq(pass));
        inOrder.verify(dataSource2).getConnection(eq(user), eq(pass));
    }

    @Test
    public void testGetDataSource1() throws SQLException {
        DataSource dataSource1 = mock(DataSource.class);
        DataSource dataSource2 = mock(DataSource.class);
        Collection<DataSource> dataSources = Arrays.asList(dataSource1,
                dataSource2);
        LoadBalancingStrategy strategy = mock(LoadBalancingStrategy.class);
        when(strategy.next()).thenReturn(0).thenReturn(1);
        LoadBalancingDataSource loadBalancingDataSource =
                new LoadBalancingDataSource(dataSources, strategy);

        loadBalancingDataSource.getConnection();

        verify(strategy).next();
        verify(dataSource1).getConnection();
    }

    @Test
    public void testGetDataSource2() throws SQLException {
        DataSource dataSource1 = mock(DataSource.class);
        DataSource dataSource2 = mock(DataSource.class);
        Collection<DataSource> dataSources = Arrays.asList(dataSource1,
                dataSource2);
        LoadBalancingStrategy strategy = mock(LoadBalancingStrategy.class);
        when(strategy.next()).thenReturn(0).thenReturn(1);
        LoadBalancingDataSource loadBalancingDataSource =
                new LoadBalancingDataSource(dataSources, strategy);

        loadBalancingDataSource.getConnection();
        loadBalancingDataSource.getConnection();

        verify(strategy, times(2)).next();
        InOrder inOrder = inOrder(dataSource1, dataSource2);
        inOrder.verify(dataSource1).getConnection();
        inOrder.verify(dataSource2).getConnection();
    }
}
