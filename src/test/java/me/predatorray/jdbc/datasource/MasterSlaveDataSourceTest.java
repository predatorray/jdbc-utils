package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collection;

public class MasterSlaveDataSourceTest {

    @Test
    public void testMasterSlaveDataSource() throws Exception {
        DataSource master = mock(DataSource.class);
        DataSource slave1 = mock(DataSource.class);
        DataSource slave2 = mock(DataSource.class);
        Connection connFromMaster = mock(Connection.class);
        Connection connFromSlave1 = mock(Connection.class);
        Connection connFromSlave2 = mock(Connection.class);
        when(master.getConnection()).thenReturn(connFromMaster);
        when(slave1.getConnection()).thenReturn(connFromSlave1);
        when(slave2.getConnection()).thenReturn(connFromSlave2);

        Collection<DataSource> slaves = Arrays.asList(slave1, slave2);

        // route read and write operations to master or slaves
        MasterSlaveDataSource masterSlaveDataSource =
                new MasterSlaveDataSource(master, slaves);

        // a not read-only connection is routed to the master
        Connection conn1 = masterSlaveDataSource.getConnection();
        conn1.prepareStatement("sql1");
        verify(connFromMaster).prepareStatement(eq("sql1"));

        // after `conn.setReadOnly(true);`, the connection will be routed
        // to the read-only replicas using round-robin strategy
        // (master -> slave1 -> slave2 -> ... -> slavesN -> master again),
        // and the first replica (master) returns the connection
        conn1.setReadOnly(true);
        conn1.prepareStatement("sql2");
        verify(connFromMaster).prepareStatement(eq("sql2"));

        // the second time, the slave1 does
        Connection conn2 = masterSlaveDataSource.getConnection();
        conn2.setReadOnly(true);
        conn2.prepareStatement("sql3");
        verify(connFromSlave1).prepareStatement(eq("sql3"));

        // the third time, the slave2 does
        Connection conn3 = masterSlaveDataSource.getConnection();
        conn3.setReadOnly(true);
        conn3.prepareStatement("sql4");
        verify(connFromSlave2).prepareStatement(eq("sql4"));

        // the forth time, the master does again
        Connection conn4 = masterSlaveDataSource.getConnection();
        conn4.setReadOnly(true);
        conn4.prepareStatement("sql5");
        verify(connFromMaster).prepareStatement(eq("sql5"));

        // so the master.getConnection will be invoked 6 times:
        // each time the masterSlaveDataSource.getConnection() and
        // the first and the last time of calling the replicas
        // using round-robin
        verify(master, times(6)).getConnection();
        verify(slave1).getConnection();
        verify(slave2).getConnection();
    }
}
