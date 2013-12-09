package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Test;

import java.sql.Connection;

public class ReplicationConnectionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithNullMaster() {
        Connection slave = mock(Connection.class);
        new ReplicationConnection(null, slave);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithNullSlave() {
        Connection master = mock(Connection.class);
        new ReplicationConnection(master, null);
    }

    @Test
    public void testConnectionClose() throws Exception {
        Connection master = mock(Connection.class);
        Connection slave = mock(Connection.class);
        ReplicationConnection rwConn = new ReplicationConnection(
                master, slave);
        rwConn.close();

        verify(master).close();
        verify(slave).close();
    }

    @Test
    public void testMasterConnection() throws Exception {
        Connection master = mock(Connection.class);
        Connection slave = mock(Connection.class);
        ReplicationConnection rwConn = new ReplicationConnection(
                master, slave);
        rwConn.setReadOnly(false);
        rwConn.prepareStatement("sql");

        verify(master).prepareStatement(eq("sql"));
        verify(slave, never()).prepareStatement(anyString());
    }

    @Test
    public void testSlaveConnection() throws Exception {
        Connection master = mock(Connection.class);
        Connection slave = mock(Connection.class);
        ReplicationConnection rwConn = new ReplicationConnection(
                master, slave);
        rwConn.setReadOnly(true);
        rwConn.prepareStatement("sql");

        verify(slave).prepareStatement(eq("sql"));
        verify(master, never()).prepareStatement(anyString());
    }
}
