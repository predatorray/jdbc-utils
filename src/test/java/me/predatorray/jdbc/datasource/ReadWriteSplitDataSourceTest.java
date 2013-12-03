package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;

public class ReadWriteSplitDataSourceTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullMasterDataSource() {
        DataSource slave = mock(DataSource.class);
        new ReadWriteSplitDataSource(null, slave);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullSlaveDataSource() {
        DataSource master = mock(DataSource.class);
        new ReadWriteSplitDataSource(master, null);
    }

    @Test
    public void testConnectionOpened1() throws Exception {
        Connection dummyConn = mock(Connection.class);
        DataSource master = mock(DataSource.class);
        when(master.getConnection()).thenReturn(dummyConn);
        DataSource slave = mock(DataSource.class);
        when(slave.getConnection()).thenReturn(dummyConn);
        ReadWriteSplitDataSource rwDs = new ReadWriteSplitDataSource(
                master, slave);
        rwDs.getConnection();
        verify(master).getConnection();
        verify(slave).getConnection();
    }

    @Test
    public void testConnectionOpened2() throws Exception {
        Connection dummyConn = mock(Connection.class);
        DataSource master = mock(DataSource.class);
        when(master.getConnection(anyString(), anyString()))
                .thenReturn(dummyConn);
        DataSource slave = mock(DataSource.class);
        when(slave.getConnection(anyString(), anyString()))
                .thenReturn(dummyConn);
        ReadWriteSplitDataSource rwDs = new ReadWriteSplitDataSource(
                master, slave);
        rwDs.getConnection("u", "p");
        verify(master).getConnection(eq("u"), eq("p"));
        verify(slave).getConnection(eq("u"), eq("p"));
    }
}
