package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CloseOnCompletionPreparedStatementTest {

    @Test
    public void testCloseOnCompletionAfterExecuteQuery() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(ps.executeQuery()).thenReturn(rs);

        CloseOnCompletionPreparedStatement cps =
                new CloseOnCompletionPreparedStatement(ps);
        cps.executeQuery();
        cps.close();

        verify(rs).close();
    }

    @Test
    public void testCloseOnCompletionAfterExecuteQueryString()
            throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(ps.executeQuery(anyString())).thenReturn(rs);

        CloseOnCompletionPreparedStatement cps =
                new CloseOnCompletionPreparedStatement(ps);
        cps.executeQuery("sql");
        cps.close();

        verify(rs).close();
    }

    @Test
    public void testCloseOnCompletionAfterGetResultSet() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(ps.getResultSet()).thenReturn(rs);

        CloseOnCompletionPreparedStatement cps =
                new CloseOnCompletionPreparedStatement(ps);
        cps.getResultSet();
        cps.close();

        verify(rs).close();
    }

    @Test
    public void testCloseOnCompletionAfterGetGeneratedKeys()
            throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(ps.getGeneratedKeys()).thenReturn(rs);

        CloseOnCompletionPreparedStatement cps =
                new CloseOnCompletionPreparedStatement(ps);
        cps.getGeneratedKeys();
        cps.close();

        verify(rs).close();
    }

    @Test
    public void testIsCloseOnCompletion() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(ps.getGeneratedKeys()).thenReturn(rs);

        CloseOnCompletionPreparedStatement cps =
                new CloseOnCompletionPreparedStatement(ps);

        Assert.assertTrue(cps.isCloseOnCompletion());
    }
}
