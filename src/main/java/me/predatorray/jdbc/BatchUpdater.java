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
        ps.clearParameters();
        ParameterList parameterList = new ParameterList(parameters);
        ParameterVisitor visitor = new ParameterVisitor(ps);
        parameterList.accept(visitor);
        ps.addBatch();
        return this;
    }

    public int[] doBatch() throws SQLException {
        return ps.executeBatch();
    }
}
