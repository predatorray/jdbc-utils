package me.predatorray.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

class ParameterVisitor {

    private final PreparedStatement ps;
    private int paramIndex = 1;
    
    ParameterVisitor(PreparedStatement ps) {
        this.ps = ps;
    }

    public void visit(Integer i) throws SQLException {
        ps.setInt(paramIndex++, i);
    }

    public void visit(Long l) throws SQLException {
        ps.setLong(paramIndex++, l);
    }

    public void visit(String s) throws SQLException {
        ps.setString(paramIndex++, s);
    }

    public void visit(Short s) throws SQLException {
        ps.setShort(paramIndex++, s);
    }

    public void visit(Enum<?> e) throws SQLException {
        ps.setString(paramIndex++, e == null ? null : e.toString());
    }

    public void visit(Object o) throws SQLException {
        ps.setObject(paramIndex++, o);
    }
}
