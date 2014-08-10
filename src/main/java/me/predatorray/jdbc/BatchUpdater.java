package me.predatorray.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BatchUpdater {

    private final PreparedStatement ps;

    BatchUpdater(PreparedStatement ps) {
        Check.argumentIsNotNull(ps, "PreparedStatement must not be null");
        this.ps = ps;
    }

    public BatchUpdater addBatch(Object ...parameters) {
        return addBatch(Arrays.asList(parameters));
    }

    public BatchUpdater addBatch(List<?> parameters)
            throws DataAccessException {
        Check.argumentIsNotNull(parameters, "parameters cannot be null");

        PreparedStatementSetter setter =
                new SimplePreparedStatementSetter(parameters);
        return addBatch(setter);
    }

    public BatchUpdater addBatch(PreparedStatementSetter setter)
            throws DataAccessException {
        Check.argumentIsNotNull(setter, "setter must not be null");

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

    public <K> List<K> doBatch(DataMapper<K> keyMapper)
            throws DataAccessException {
        Check.argumentIsNotNull(keyMapper, "keyMapper must not be null");

        try {
            int initArraySize = 0;
            int[] rows = ps.executeBatch();
            for (int row : rows) {
                if (row > 0) {
                    initArraySize += row;
                }
            }

            ResultSet keyRs = ps.getGeneratedKeys();
            List<K> keys = new ArrayList<K>(initArraySize);
            while (keyRs.next()) {
                keys.add(keyMapper.map(new ExtendedResultSetImpl(keyRs)));
            }
            return keys;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
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
