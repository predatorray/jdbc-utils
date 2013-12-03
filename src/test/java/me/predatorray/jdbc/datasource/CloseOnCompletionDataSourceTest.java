package me.predatorray.jdbc.datasource;

import static org.mockito.Mockito.*;

import org.junit.Test;

import javax.sql.DataSource;

public class CloseOnCompletionDataSourceTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullArg()
            throws Exception {
        new CloseOnCompletionDataSource(null);
    }

    @Test
    public void testGetConnection() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        CloseOnCompletionDataSource cDs = new CloseOnCompletionDataSource(
                dataSource);
        cDs.getConnection();
        verify(dataSource).getConnection();
    }

    @Test
    public void testGetConnectionWithUsernameAndPassword() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        CloseOnCompletionDataSource cDs = new CloseOnCompletionDataSource(
                dataSource);
        cDs.getConnection("u", "p");
        verify(dataSource).getConnection(eq("u"), eq("p"));
    }

}
