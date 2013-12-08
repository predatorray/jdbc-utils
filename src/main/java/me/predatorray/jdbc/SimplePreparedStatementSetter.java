package me.predatorray.jdbc;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class SimplePreparedStatementSetter implements PreparedStatementSetter {

    private final List<?> argList;
    private int argIndex = 0;

    public SimplePreparedStatementSetter(List<?> argList) {
        this.argList = argList;
    }

    @Override
    public void setPreparedStatement(PreparedStatement ps)
            throws SQLException {
        for (Object arg : argList) {
            ++argIndex;

            if (arg == null) {
                ps.setNull(argIndex, Types.NULL);
            } else if (arg instanceof Boolean) {
                ps.setBoolean(argIndex, (Boolean) arg);
            } else if (arg instanceof Byte) {
                ps.setByte(argIndex, (Byte) arg);
            } else if (arg instanceof Short) {
                ps.setShort(argIndex, (Short) arg);
            } else if (arg instanceof Integer) {
                ps.setInt(argIndex, (Integer) arg);
            } else if (arg instanceof Long)  {
                ps.setLong(argIndex, (Long) arg);
            } else if (arg instanceof Float) {
                ps.setFloat(argIndex, (Float) arg);
            } else if (arg instanceof Double) {
                ps.setDouble(argIndex, (Double) arg);
            } else if (arg instanceof BigDecimal) {
                ps.setBigDecimal(argIndex, (BigDecimal) arg);
            } else if (arg instanceof String) {
                ps.setString(argIndex, (String) arg);
            } else if (arg instanceof byte[]) {
                ps.setBytes(argIndex, (byte[]) arg);
            } else if (arg instanceof Date) {
                ps.setDate(argIndex, (Date) arg);
            } else if (arg instanceof Time) {
                ps.setTime(argIndex, (Time) arg);
            } else if (arg instanceof Timestamp) {
                ps.setTimestamp(argIndex, (Timestamp) arg);
            } else {
                ps.setObject(argIndex, arg);
            }
        }
    }
}
