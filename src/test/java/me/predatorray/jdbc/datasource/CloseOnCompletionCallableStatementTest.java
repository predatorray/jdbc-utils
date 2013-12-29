package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CloseOnCompletionCallableStatementTest {

    @Test
    public void testCloseOnCompletionAfterExecuteQuery() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.executeQuery()).thenReturn(rs);

        CloseOnCompletionCallableStatement ccs =
                new CloseOnCompletionCallableStatement(callableStatement);
        ccs.executeQuery();
        ccs.close();

        verify(rs).close();
    }

    @Test
    public void testCloseOnCompletionAfterExecuteQueryString()
            throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.executeQuery(anyString())).thenReturn(rs);

        CloseOnCompletionCallableStatement ccs =
                new CloseOnCompletionCallableStatement(callableStatement);
        ccs.executeQuery("sql");
        ccs.close();

        verify(rs).close();
    }

    @Test
    public void testCloseOnCompletionAfterGetResultSet() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.getResultSet()).thenReturn(rs);

        CloseOnCompletionCallableStatement ccs =
                new CloseOnCompletionCallableStatement(callableStatement);
        ccs.getResultSet();
        ccs.close();

        verify(rs).close();
    }

    @Test
    public void testCloseOnCompletionAfterGetGeneratedKeys()
            throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.getGeneratedKeys()).thenReturn(rs);

        CloseOnCompletionCallableStatement ccs =
                new CloseOnCompletionCallableStatement(callableStatement);
        ccs.getGeneratedKeys();
        ccs.close();

        verify(rs).close();
    }

    @Test
    public void testIsCloseOnCompletion() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.isClosed()).thenReturn(false);
        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.getGeneratedKeys()).thenReturn(rs);

        CloseOnCompletionCallableStatement ccs =
                new CloseOnCompletionCallableStatement(callableStatement);
        Assert.assertTrue(ccs.isCloseOnCompletion());
    }
}
