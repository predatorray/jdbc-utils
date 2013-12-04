package me.predatorray.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

class ExtendedResultSetImpl extends ResultSetWrapper
        implements ExtendedResultSet {

    ExtendedResultSetImpl(ResultSet resultSet) {
        super(resultSet);
    }

    @Override
    public Boolean getNullableBoolean(int columnIndex) throws SQLException {
        boolean value = getBoolean(columnIndex);
        return wasNull() ? null : value;
    }

    @Override
    public Boolean getNullableBoolean(String columnLabel) throws SQLException {
        boolean value = getBoolean(columnLabel);
        return wasNull() ? null : value;
    }

    @Override
    public Byte getNullableByte(int columnIndex) throws SQLException {
        byte value = getByte(columnIndex);
        return wasNull() ? null : value;
    }

    @Override
    public Byte getNullableByte(String columnLabel) throws SQLException {
        byte value = getByte(columnLabel);
        return wasNull() ? null : value;
    }

    @Override
    public Short getNullableShort(int columnIndex) throws SQLException {
        short value = getShort(columnIndex);
        return wasNull() ? null : value;
    }

    @Override
    public Short getNullableShort(String columnLabel) throws SQLException {
        short value = getShort(columnLabel);
        return wasNull() ? null : value;
    }

    @Override
    public Integer getNullableInt(int columnIndex) throws SQLException {
        int value = getInt(columnIndex);
        return wasNull() ? null : value;
    }

    @Override
    public Integer getNullableInt(String columnLabel) throws SQLException {
        int value = getInt(columnLabel);
        return wasNull() ? null : value;
    }

    @Override
    public Long getNullableLong(int columnIndex) throws SQLException {
        long value = getLong(columnIndex);
        return wasNull() ? null : value;
    }

    @Override
    public Long getNullableLong(String columnLabel) throws SQLException {
        long value = getLong(columnLabel);
        return wasNull() ? null : value;
    }

    @Override
    public Float getNullableFloat(int columnIndex) throws SQLException {
        float value = getFloat(columnIndex);
        return wasNull() ? null : value;
    }

    @Override
    public Float getNullableFloat(String columnLabel) throws SQLException {
        float value = getFloat(columnLabel);
        return wasNull() ? null : value;
    }

    @Override
    public Double getNullableDouble(int columnIndex) throws SQLException {
        double value = getDouble(columnIndex);
        return wasNull() ? null : value;
    }

    @Override
    public Double getNullableDouble(String columnLabel) throws SQLException {
        double value = getDouble(columnLabel);
        return wasNull() ? null : value;
    }
}
