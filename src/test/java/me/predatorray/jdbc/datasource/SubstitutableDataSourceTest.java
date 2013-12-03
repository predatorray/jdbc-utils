package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

public class SubstitutableDataSourceTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullArg() throws Exception {
        new SubstitutableDataSource(null);
    }

    @Test
    public void testWhenThereIsNoSubstitutionAndDbException()
            throws Exception {
        DataSource dataSource = mock(DataSource.class);
        SubstitutableDataSource sDs = new SubstitutableDataSource(dataSource);

        sDs.getConnection();
        verify(dataSource).getConnection();

        sDs.getConnection("u", "p");
        verify(dataSource).getConnection(eq("u"), eq("p"));
    }

    @Test(expected = SQLException.class)
    public void testWhenThereIsNoSubstitutionButDbException()
            throws Exception {
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        SubstitutableDataSource sDs = new SubstitutableDataSource(dataSource);

        sDs.getConnection();
    }

    @Test
    public void testWhenThereIsSubstitutionButNoDbException()
            throws Exception {
        DataSource dataSource = mock(DataSource.class);
        DataSource substitution = mock(DataSource.class);
        SubstitutableDataSource sDs = new SubstitutableDataSource(dataSource);
        sDs.setSubstitution(substitution);

        sDs.getConnection();
        verify(dataSource).getConnection();
    }

    @Test
    public void testWhenThereIsSubstitutionAndDbException()
            throws Exception {
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        DataSource substitution = mock(DataSource.class);
        SubstitutableDataSource sDs = new SubstitutableDataSource(dataSource);
        sDs.setSubstitution(substitution);

        sDs.getConnection();
        verify(substitution).getConnection();
    }

    @Test(expected = SQLException.class, timeout = 1000)
    public void testInfinityLoopDetection() throws SQLException {
        DataSource ds1 = mock(DataSource.class);
        when(ds1.getConnection()).thenThrow(new SQLException());
        DataSource ds2 = mock(DataSource.class);
        when(ds2.getConnection()).thenThrow(new SQLException());
        DataSource ds3 = mock(DataSource.class);
        when(ds3.getConnection()).thenThrow(new SQLException());

        SubstitutableDataSource sDs1 = new SubstitutableDataSource(ds1);
        SubstitutableDataSource sDs2 = new SubstitutableDataSource(ds2);
        SubstitutableDataSource sDs3 = new SubstitutableDataSource(ds3);
        sDs1.setSubstitution(sDs2);
        sDs2.setSubstitution(sDs3);
        sDs3.setSubstitution(sDs1);

        sDs1.getConnection();
    }
}
