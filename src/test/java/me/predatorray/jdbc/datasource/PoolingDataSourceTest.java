package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PoolingDataSourceTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructionWithNullDataSource() {
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        new PoolingDataSource(null, connectionPool);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructionWithNullConnectionPool() {
        DataSource dataSource = mock(DataSource.class);
        new PoolingDataSource(dataSource, null);
    }

    @Test
    public void testGetConnectionWithUsernameAndPassword() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        PoolingDataSource poolingDataSource = new PoolingDataSource(
                dataSource, connectionPool);
        poolingDataSource.getConnection("u", "p");

        verify(dataSource).getConnection(eq("u"), eq("p"));
    }

    @Test
    public void testGetConnection() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        PoolingDataSource poolingDataSource = new PoolingDataSource(
                dataSource, connectionPool);
        poolingDataSource.getConnection();

        verify(connectionPool).borrowConnection();
    }
}
