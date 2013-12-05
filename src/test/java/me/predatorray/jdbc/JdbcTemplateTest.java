package me.predatorray.jdbc;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.InOrder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collections;

public class JdbcTemplateTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithNullArg() {
        new JdbcTemplate(null);
    }

    @Test
    public void testQuery() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        Connection connection = mock(Connection.class);
        DataSource dataSource = mock(DataSource.class);
        DataMapper dataMapper = mock(DataMapper.class);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(dataSource.getConnection()).thenReturn(connection);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.query("sql", Collections.emptyList(), dataMapper);

        verify(dataMapper, times(2)).map(any(ExtendedResultSet.class));
    }

    @Test
    public void testQueryWithDynamicSqlArgs() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        Connection connection = mock(Connection.class);
        DataSource dataSource = mock(DataSource.class);
        DataMapper dataMapper = mock(DataMapper.class);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(dataSource.getConnection()).thenReturn(connection);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.query("sql", Arrays.<Object>asList(1L, "str", 2),
                dataMapper);

        InOrder psSetArgsOrder = inOrder(preparedStatement);
        psSetArgsOrder.verify(preparedStatement).setLong(eq(1), eq(1L));
        psSetArgsOrder.verify(preparedStatement).setString(eq(2), eq("str"));
        psSetArgsOrder.verify(preparedStatement).setInt(eq(3), eq(2));
        psSetArgsOrder.verify(preparedStatement).executeQuery();

        verify(dataMapper, times(2)).map(any(ExtendedResultSet.class));
    }

    @Test
    public void testUpdateWithDynamicSqlArgs() throws Exception {
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        Connection connection = mock(Connection.class);
        DataSource dataSource = mock(DataSource.class);

        when(preparedStatement.executeBatch()).thenReturn(new int[0]);
        when(connection.prepareStatement(anyString()))
                .thenReturn(preparedStatement);
        when(dataSource.getConnection()).thenReturn(connection);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("sql", Arrays.<Object>asList(1L, "str", 2));

        InOrder psSetArgsOrder = inOrder(preparedStatement);
        psSetArgsOrder.verify(preparedStatement).setLong(eq(1), eq(1L));
        psSetArgsOrder.verify(preparedStatement).setString(eq(2), eq("str"));
        psSetArgsOrder.verify(preparedStatement).setInt(eq(3), eq(2));
        psSetArgsOrder.verify(preparedStatement).executeUpdate();
    }
}
