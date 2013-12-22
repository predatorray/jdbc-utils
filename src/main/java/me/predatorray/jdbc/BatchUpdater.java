package me.predatorray.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BatchUpdater {

    private final PreparedStatement ps;

    BatchUpdater(PreparedStatement ps) {
        Check.argumentIsNotNull(ps, "PreparedStatement must not be null");
        this.ps = ps;
    }

    public BatchUpdater addBatch(List<?> parameters)
            throws DataAccessException {
        Check.argumentIsNotNull(parameters, "parameters cannot be null");

        try {
            ps.clearParameters();
            PreparedStatementSetter setter =
                    new SimplePreparedStatementSetter(parameters);
            return addBatch(setter);
        } catch (SQLException ex) {
            closePreparedStatement(ps);
            throw new DataAccessException(ex);
        }
    }

    public BatchUpdater addBatch(PreparedStatementSetter setter)
            throws DataAccessException {
        Check.argumentIsNotNull(setter, "setter cannot be null");

        try {
            ps.clearParameters();
            setter.setPreparedStatement(ps);
            ps.addBatch();
            return this;
        } catch (SQLException ex) {
            closePreparedStatement(ps);
            throw new DataAccessException(ex);
        }
    }

    public int[] doBatch() throws DataAccessException {
        try {
            return ps.executeBatch();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        } finally {
            closePreparedStatement(ps);
        }
    }

    private void closePreparedStatement(PreparedStatement ps)
            throws DataAccessException {
        try {
            ps.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }
}
