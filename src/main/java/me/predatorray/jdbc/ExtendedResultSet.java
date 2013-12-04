package me.predatorray.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ExtendedResultSet extends ResultSet {

    Boolean getNullableBoolean(int columnIndex) throws SQLException;

    Boolean getNullableBoolean(String columnLabel) throws SQLException;

    Byte getNullableByte(int columnIndex) throws SQLException;

    Byte getNullableByte(String columnLabel) throws SQLException;

    Short getNullableShort(int columnIndex) throws SQLException;

    Short getNullableShort(String columnLabel) throws SQLException;

    Integer getNullableInt(int columnIndex) throws SQLException;

    Integer getNullableInt(String columnLabel) throws SQLException;

    Long getNullableLong(int columnIndex) throws SQLException;

    Long getNullableLong(String columnLabel) throws SQLException;

    Float getNullableFloat(int columnIndex) throws SQLException;

    Float getNullableFloat(String columnLabel) throws SQLException;

    Double getNullableDouble(int columnIndex) throws SQLException;

    Double getNullableDouble(String columnLabel) throws SQLException;
}
