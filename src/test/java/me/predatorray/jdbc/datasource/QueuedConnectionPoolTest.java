package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class QueuedConnectionPoolTest {

    @Test
    public void testConstruction() throws SQLException {
        DataSource ds = mock(DataSource.class);
        Connection conn = mock(Connection.class);
        when(ds.getConnection()).thenReturn(conn);

        final int initialSize = 2;
        new QueuedConnectionPool(ds, initialSize, 10, true,
                Connection.TRANSACTION_READ_UNCOMMITTED, false, null);
        verify(ds, times(initialSize)).getConnection();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructionWithIllegalArg1() throws SQLException {
        new QueuedConnectionPool(null, 1, 5, true,
                Connection.TRANSACTION_READ_UNCOMMITTED, false, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructionWithIllegalArg2() throws SQLException {
        DataSource ds = mock(DataSource.class);
        Connection conn = mock(Connection.class);
        when(ds.getConnection()).thenReturn(conn);
        new QueuedConnectionPool(ds, -1, 5, true,
                Connection.TRANSACTION_READ_UNCOMMITTED, false, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructionWithIllegalArg3() throws SQLException {
        DataSource ds = mock(DataSource.class);
        Connection conn = mock(Connection.class);
        when(ds.getConnection()).thenReturn(conn);
        new QueuedConnectionPool(ds, 1, -5, true,
                Connection.TRANSACTION_READ_UNCOMMITTED, false, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructionWithIllegalArg4() throws SQLException {
        DataSource ds = mock(DataSource.class);
        Connection conn = mock(Connection.class);
        when(ds.getConnection()).thenReturn(conn);
        new QueuedConnectionPool(ds, 5, 1, true,
                Connection.TRANSACTION_READ_UNCOMMITTED, false, null);
    }

    @Test
    public void testBorrowFromPool() throws Exception {
        DataSource ds = mock(DataSource.class);
        Connection conn = mock(Connection.class);
        when(ds.getConnection()).thenReturn(conn);

        final int initialSize = 2;
        final int maxSize = 5;
        final int borrowTimes = 3;
        QueuedConnectionPool pool = new QueuedConnectionPool(ds, initialSize,
                maxSize, true, Connection.TRANSACTION_READ_UNCOMMITTED, false,
                null);
        for (int i = 1; i <= borrowTimes; ++i) {
            pool.borrowConnection();
        }

        verify(ds, times(borrowTimes)).getConnection();
    }

    @Test
    public void testReturnAndBorrowFromPool() throws Exception {
        DataSource ds = mock(DataSource.class);
        Connection conn = mock(Connection.class);
        Connection otherConn = mock(Connection.class);
        when(ds.getConnection()).thenReturn(conn).thenReturn(otherConn);

        final int initialSize = 1;
        final int maxSize = 5;
        QueuedConnectionPool pool = new QueuedConnectionPool(ds, initialSize,
                maxSize, true, Connection.TRANSACTION_READ_UNCOMMITTED, false,
                null);
        Connection connFromPool1 = pool.borrowConnection();
        pool.returnConnection(connFromPool1);
        Connection connFromPool2 = pool.borrowConnection();

        verify(ds, times(1)).getConnection();
        Assert.assertSame(conn, connFromPool2);
    }

    @Test
    public void testBorrowOrderOfConstructor() throws Exception {
        DataSource ds = mock(DataSource.class);
        Connection conn1 = mock(Connection.class);
        Connection conn2 = mock(Connection.class);
        Connection conn3 = mock(Connection.class);
        when(ds.getConnection()).thenReturn(conn1)
                .thenReturn(conn2)
                .thenReturn(conn3);

        final int initialSize = 3;
        final int maxSize = 5;
        QueuedConnectionPool pool = new QueuedConnectionPool(ds, initialSize,
                maxSize, true, Connection.TRANSACTION_READ_UNCOMMITTED, false,
                null);
        Connection connFromPool1 = pool.borrowConnection();
        Connection connFromPool2 = pool.borrowConnection();
        Connection connFromPool3 = pool.borrowConnection();

        Assert.assertSame(conn1, connFromPool1);
        Assert.assertSame(conn2, connFromPool2);
        Assert.assertSame(conn3, connFromPool3);
    }

    @Test
    public void testBorrowOrderFromReturn() throws Exception {
        DataSource ds = mock(DataSource.class);
        Connection conn1 = mock(Connection.class);
        Connection conn2 = mock(Connection.class);
        Connection conn3 = mock(Connection.class);

        final int initialSize = 0;
        final int maxSize = 3;
        QueuedConnectionPool pool = new QueuedConnectionPool(ds, initialSize,
                maxSize, true, Connection.TRANSACTION_READ_UNCOMMITTED, false,
                null);
        pool.returnConnection(conn1);
        pool.returnConnection(conn2);
        pool.returnConnection(conn3);

        Connection connFromPool1 = pool.borrowConnection();
        Connection connFromPool2 = pool.borrowConnection();
        Connection connFromPool3 = pool.borrowConnection();

        Assert.assertSame(conn1, connFromPool1);
        Assert.assertSame(conn2, connFromPool2);
        Assert.assertSame(conn3, connFromPool3);
    }

    @Test
    public void testDropConnection() throws Exception {
        DataSource ds = mock(DataSource.class);
        Connection connInPool = mock(Connection.class);
        when(ds.getConnection()).thenReturn(connInPool);
        Connection connReturned = mock(Connection.class);

        final int initialSize = 1;
        final int maxSize = 1;
        QueuedConnectionPool pool = new QueuedConnectionPool(ds, initialSize,
                maxSize, true, Connection.TRANSACTION_READ_UNCOMMITTED, false,
                null);
        pool.returnConnection(connReturned);

        verify(connReturned).close();
    }

    @Test
    public void testForbidPoolClosedConnection() throws Exception {
        DataSource ds = mock(DataSource.class);
        Connection conn = mock(Connection.class);
        when(ds.getConnection()).thenReturn(conn);
        Connection closedConnection = mock(Connection.class);
        when (closedConnection.isClosed()).thenReturn(true);

        final int initialSize = 0;
        final int maxSize = 1;
        QueuedConnectionPool pool = new QueuedConnectionPool(ds, initialSize,
                maxSize, true, Connection.TRANSACTION_READ_UNCOMMITTED, false,
                null);
        pool.returnConnection(closedConnection);
        pool.borrowConnection();

        verify(ds).getConnection();
    }

    @Test
    public void testConnectionBeSetToDefaultState() throws Exception {
        DataSource ds = mock(DataSource.class);
        Connection conn = mock(Connection.class);
        when(ds.getConnection()).thenReturn(conn);
        when(conn.isClosed()).thenReturn(false);

        final int initialSize = 1;
        final int maxSize = 1;
        final boolean defaultAutoCommit = true;
        final int defaultIsolation = Connection.TRANSACTION_READ_UNCOMMITTED;
        final boolean defaultReadOnly = true;
        final String defaultCategory = "category";
        QueuedConnectionPool pool = new QueuedConnectionPool(ds, initialSize,
                maxSize, defaultAutoCommit, defaultIsolation, defaultReadOnly,
                defaultCategory);
        Connection connFromPool = pool.borrowConnection();
        connFromPool.setReadOnly(false);
        connFromPool.setAutoCommit(false);
        connFromPool.setTransactionIsolation(Connection.TRANSACTION_NONE);
        connFromPool.setCatalog("foobar");
        pool.returnConnection(connFromPool);

        pool.borrowConnection();

        verify(conn).rollback();
        verify(conn).setReadOnly(eq(defaultReadOnly));
        verify(conn).setAutoCommit(eq(defaultAutoCommit));
        verify(conn).setCatalog(eq(defaultCategory));
        verify(conn).setTransactionIsolation(eq(defaultIsolation));
    }
}
