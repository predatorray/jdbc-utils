package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import me.predatorray.jdbc.test.BlockingAssert;
import me.predatorray.jdbc.test.BlockingJob;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class BlockingQueuedConnectionPoolTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructionWithNullDataSource() throws SQLException {
        new BlockingQueuedConnectionPool(null, 1, true,
                Connection.TRANSACTION_NONE, true, "category");
    }

    @Test(expected = SQLException.class)
    public void testConstructionWithSQLExceptionFromDataSource()
            throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenThrow(new SQLException());

        new BlockingQueuedConnectionPool(dataSource, 1, true,
                Connection.TRANSACTION_NONE, true, "category");
    }

    @Test(timeout = 3000)
    public void testBorrowFromPool() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);

        BlockingQueuedConnectionPool pool = new BlockingQueuedConnectionPool(
                dataSource, 1, true, Connection.TRANSACTION_NONE, true,
                "category");
        Connection connFromPool = pool.borrowConnection();
        Assert.assertSame(connection, connFromPool);

    }

    @Test(timeout = 3000)
    public void testBlockingReturnConnection() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);

        final BlockingQueuedConnectionPool pool =
                new BlockingQueuedConnectionPool(
                        dataSource, 1, true, Connection.TRANSACTION_NONE, true,
                        "category");
        pool.borrowConnection();

        BlockingAssert.assertBlockingAtLeast(new BlockingJob() {
            @Override
            public void perform() throws InterruptedException {
                try {
                    pool.borrowConnection();
                } catch (InterruptedException ex) {
                    throw ex;
                } catch (Exception ex) {
                    Assert.fail("pool.borrowConnection() " +
                            "throws unhandled exception");
                }
            }
        }, 2000);
    }
}
