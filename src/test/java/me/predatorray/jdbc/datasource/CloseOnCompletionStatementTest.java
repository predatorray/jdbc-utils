package me.predatorray.jdbc.datasource;

import org.junit.Assert;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CloseOnCompletionStatementTest {

    @Test
    public void testCloseOnCompletionAfterExecuteQueryString()
            throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(ps.executeQuery(anyString())).thenReturn(rs);

        CloseOnCompletionStatement cs = new CloseOnCompletionStatement(ps);
        cs.executeQuery("sql");
        cs.close();

        verify(rs).close();
    }

    @Test
    public void testCloseOnCompletionAfterGetResultSet() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(ps.getResultSet()).thenReturn(rs);

        CloseOnCompletionStatement cs = new CloseOnCompletionStatement(ps);
        cs.getResultSet();
        cs.close();

        verify(rs).close();
    }

    @Test
    public void testCloseOnCompletionAfterGetGeneratedKeys()
            throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(ps.getGeneratedKeys()).thenReturn(rs);

        CloseOnCompletionStatement cs = new CloseOnCompletionStatement(ps);
        cs.getGeneratedKeys();
        cs.close();

        verify(rs).close();
    }

    @Test
    public void testIsCloseOnCompletion() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(ps.getGeneratedKeys()).thenReturn(rs);

        CloseOnCompletionStatement cs = new CloseOnCompletionStatement(ps);
        Assert.assertTrue(cs.isCloseOnCompletion());
    }
}
