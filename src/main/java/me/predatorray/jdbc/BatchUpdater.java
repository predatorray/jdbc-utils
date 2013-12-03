package me.predatorray.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BatchUpdater {

    private final PreparedStatement ps;

    BatchUpdater(PreparedStatement ps) {
        this.ps = ps;
    }

    public BatchUpdater addBatch(List<Object> parameters) throws SQLException {
        Check.argumentIsNotNull(parameters, "parameters cannot be null");

        ps.clearParameters();
        PreparedStatementSetter setter =
                new SimplePreparedStatementSetter(parameters);
        return addBatch(setter);
    }

    public BatchUpdater addBatch(PreparedStatementSetter setter)
            throws SQLException {
        Check.argumentIsNotNull(setter, "setter cannot be null");

        ps.clearParameters();
        setter.setPreparedStatement(ps);
        ps.addBatch();
        return this;
    }

    public int[] doBatch() throws SQLException {
        return ps.executeBatch();
    }
}
