package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class CloseOnCompletionConnectionTest {

    @Test
    public void testCloseOnCompletionConnection() throws Exception {
        Connection conn = mock(Connection.class);
        PreparedStatement ps1 = mock(PreparedStatement.class);
        PreparedStatement ps2 = mock(PreparedStatement.class);
        CallableStatement cs1 = mock(CallableStatement.class);
        Statement stmt1 = mock(Statement.class);
        when(conn.prepareStatement(anyString()))
                .thenReturn(ps1)
                .thenReturn(ps2);
        when(conn.prepareCall(anyString()))
                .thenReturn(cs1);
        when(conn.createStatement())
                .thenReturn(stmt1);

        CloseOnCompletionConnection cocConn = new CloseOnCompletionConnection(
                conn);
        cocConn.prepareStatement("sql1");
        cocConn.prepareStatement("sql2");
        cocConn.prepareCall("sql3");
        cocConn.createStatement();
        cocConn.close();

        verify(conn).close();
        verify(ps1).close();
        verify(ps2).close();
        verify(cs1).close();
        verify(stmt1).close();
    }
}
