package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.InOrder;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class RoundRobinDataSourceTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullArg1() {
        new RoundRobinDataSource((Collection<? extends DataSource >) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullArg2() {
        new RoundRobinDataSource((DataSource[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidArg1() {
        new RoundRobinDataSource(Collections.<DataSource>emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidArg2() {
        new RoundRobinDataSource(new DataSource[0]);
    }

    @Test
    public void testRoundRobin1() throws Exception {
        DataSource ds1 = mock(DataSource.class);
        RoundRobinDataSource rDs = new RoundRobinDataSource(
                Collections.singleton(ds1));

        rDs.getConnection();
        verify(ds1).getConnection();

        rDs.getConnection("u", "p");
        verify(ds1).getConnection(eq("u"), eq("p"));
    }

    @Test
    public void testRoundRobin2() throws Exception {
        DataSource ds1 = mock(DataSource.class);
        DataSource ds2 = mock(DataSource.class);
        DataSource ds3 = mock(DataSource.class);
        RoundRobinDataSource rDs = new RoundRobinDataSource(
                Arrays.asList(ds1, ds2, ds3));

        final int times = 4;
        for (int i = 0; i < times; ++i) {
            rDs.getConnection();
        }

        InOrder dataSourceCalledOrder = inOrder(ds1, ds2, ds3);
        dataSourceCalledOrder.verify(ds1).getConnection();
        dataSourceCalledOrder.verify(ds2).getConnection();
        dataSourceCalledOrder.verify(ds3).getConnection();
        dataSourceCalledOrder.verify(ds1).getConnection();
    }
}
