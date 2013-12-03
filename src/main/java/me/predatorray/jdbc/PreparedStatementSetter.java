package me.predatorray.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementSetter {

    void setPreparedStatement(PreparedStatement ps) throws SQLException;
}
