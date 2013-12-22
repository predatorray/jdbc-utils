package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Assert;
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

    @Test(timeout = 1000)
    public void testInfinityLoopDetection() throws SQLException {
        DataSource ds1 = mock(DataSource.class);
        SQLException ex1 = new SQLException();
        when(ds1.getConnection()).thenThrow(ex1);

        DataSource ds2 = mock(DataSource.class);
        SQLException ex2 = new SQLException();
        when(ds2.getConnection()).thenThrow(ex2);

        DataSource ds3 = mock(DataSource.class);
        SQLException ex3 = new SQLException();
        when(ds3.getConnection()).thenThrow(ex3);

        SubstitutableDataSource sDs1 = new SubstitutableDataSource(ds1);
        SubstitutableDataSource sDs2 = new SubstitutableDataSource(ds2);
        SubstitutableDataSource sDs3 = new SubstitutableDataSource(ds3);
        sDs1.setSubstitution(sDs2);
        sDs2.setSubstitution(sDs3);
        sDs3.setSubstitution(sDs1);

        try {
            sDs1.getConnection();

            Assert.fail("SQLException is not thrown by the dataSource" +
                    ".getConnection()");
        } catch (SQLException ex) {
            Assert.assertEquals(ex1, ex);
        } finally {
            verify(ds1, times(2)).getConnection();
            verify(ds2).getConnection();
            verify(ds3).getConnection();
        }
    }
}
