package me.predatorray.jdbc;

import static org.mockito.Mockito.*;

import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class BatchUpdaterTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBatchUpdaterConstructorWithNullArg() {
        new BatchUpdater(null);
    }

    @Test
    public void testBatchUpdater() throws SQLException {
        PreparedStatement ps = mock(PreparedStatement.class);
        BatchUpdater batchUpdater = new BatchUpdater(ps);

        batchUpdater.addBatch(Arrays.asList(2)).addBatch(
                new PreparedStatementSetter() {
                    @Override
                    public void setPreparedStatement(PreparedStatement ps)
                            throws SQLException {
                        ps.setInt(1, 1);
                        ps.setString(2, "s");
                    }
                }).doBatch();

        verify(ps).setInt(eq(1), eq(2));
        verify(ps).setInt(eq(1), eq(1));
        verify(ps).setString(eq(2), eq("s"));
        verify(ps).executeBatch();
    }
}
