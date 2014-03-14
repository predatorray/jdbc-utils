package me.predatorray.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A result set interface which extends the native JDBC <code>ResultSet</code>,
 * adding the boxed type (including Boolean, Byte, Short, Integer, Long, Float
 * and Double) retrieve methods, from which the result will be
 * <code>null</code> if the value from the database is SQL <code>NULL</code>.
 *
 * @author Wenhao Ji
 */
public interface ExtendedResultSet extends ResultSet {

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Boolean</code> in the Java programming language.
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Boolean getNullableBoolean(int columnIndex) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Boolean</code> in the Java programming language.
     * @param columnLabel the label for the column specified with the SQL AS
     *                    clause.  If the SQL AS clause was not specified, then
     *                    the label is the name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Boolean getNullableBoolean(String columnLabel) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Byte</code> in the Java programming language.
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Byte getNullableByte(int columnIndex) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Byte</code> in the Java programming language.
     * @param columnLabel the label for the column specified with the SQL AS
     *                    clause.  If the SQL AS clause was not specified, then
     *                    the label is the name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Byte getNullableByte(String columnLabel) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Short</code> in the Java programming language.
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Short getNullableShort(int columnIndex) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Short</code> in the Java programming language.
     * @param columnLabel the label for the column specified with the SQL AS
     *                    clause.  If the SQL AS clause was not specified, then
     *                    the label is the name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Short getNullableShort(String columnLabel) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Integer</code> in the Java programming language.
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Integer getNullableInt(int columnIndex) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Integer</code> in the Java programming language.
     * @param columnLabel the label for the column specified with the SQL AS
     *                    clause.  If the SQL AS clause was not specified, then
     *                    the label is the name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Integer getNullableInt(String columnLabel) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Long</code> in the Java programming language.
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Long getNullableLong(int columnIndex) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Long</code> in the Java programming language.
     * @param columnLabel the label for the column specified with the SQL AS
     *                    clause.  If the SQL AS clause was not specified, then
     *                    the label is the name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Long getNullableLong(String columnLabel) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Float</code> in the Java programming language.
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Float getNullableFloat(int columnIndex) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>Float</code> object as
     * a <code>Long</code> in the Java programming language.
     * @param columnLabel the label for the column specified with the SQL AS
     *                    clause.  If the SQL AS clause was not specified, then
     *                    the label is the name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Float getNullableFloat(String columnLabel) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Double</code> in the Java programming language.
     * @param columnIndex the first column is 1, the second is 2, ...
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Double getNullableDouble(int columnIndex) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Double</code> in the Java programming language.
     * @param columnLabel the label for the column specified with the SQL AS
     *                    clause.  If the SQL AS clause was not specified, then
     *                    the label is the name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    Double getNullableDouble(String columnLabel) throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Enum</code> in the Java programming language.
     * @param columnIndex the first column is 1, the second is 2, ...
     * @param enumClz the class of the enum
     * @param <E> the type of the enum
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    <E extends Enum<E>> E getEnum(int columnIndex, Class<E> enumClz)
            throws SQLException;

    /**
     * Retrieves the value of the designated column in the current row
     * of this <code>ResultSet</code> object as
     * a <code>Enum</code> in the Java programming language.
     * @param columnLabel the label for the column specified with the SQL AS
     *                    clause.  If the SQL AS clause was not specified, then
     *                    the label is the name of the column
     * @param enumClz the class of the enum
     * @param <E> the type of the enum
     * @return the column value; if the value is SQL <code>NULL</code>, the
     *         value returned is <code>null</code>
     * @throws SQLException if the columnIndex is not valid;
     *                       if a database access error occurs or this method
     *                       is called on a closed result set
     */
    <E extends Enum<E>> E getEnum(String columnLabel, Class<E> enumClz)
            throws SQLException;
}
