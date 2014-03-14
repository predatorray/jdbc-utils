package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolableConnectionTest {

    @Test
    public void testClose() throws Exception {
        Connection conn = mock(Connection.class);
        ConnectionPool pool = mock(ConnectionPool.class);

        PoolableConnection sut = new PoolableConnection(conn, pool);
        sut.close();

        verify(conn, never()).close();
        verify(pool).returnConnection(eq(conn));
    }

    @Test
    public void testCloseWhenExceptionThrown() throws Exception {
        Connection conn = mock(Connection.class);
        ConnectionPool pool = mock(ConnectionPool.class);
        Exception ex = mock(Exception.class);
        doThrow(ex).when(pool).returnConnection(any(Connection.class));

        PoolableConnection sut = new PoolableConnection(conn, pool);
        try {
            sut.close();

            Assert.fail("the exception is not thrown");
        } catch (SQLException e) {
            Assert.assertNotNull(e.getCause());
            Assert.assertTrue(e.getCause() instanceof Exception);
            Assert.assertSame(ex, e.getCause());
        }
    }

    @Test
    public void testCloseWhenSQLExceptionThrown() throws Exception {
        Connection conn = mock(Connection.class);
        ConnectionPool pool = mock(ConnectionPool.class);
        SQLException sqlEx = mock(SQLException.class);
        doThrow(sqlEx).when(pool).returnConnection(any(Connection.class));

        PoolableConnection sut = new PoolableConnection(conn, pool);
        try {
            sut.close();

            Assert.fail("the exception is not thrown");
        } catch (SQLException actual) {
            Assert.assertSame(sqlEx, actual);
        }
    }
}
