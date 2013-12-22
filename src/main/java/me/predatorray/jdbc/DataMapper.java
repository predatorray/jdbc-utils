package me.predatorray.jdbc;

import java.sql.SQLException;

/**
 *
 * @param <E> the type of the entity object
 * @author Wenhao Ji
 */
public interface DataMapper<E> {

    /**
     *
     * @param rs
     * @see me.predatorray.jdbc.ExtendedResultSet
     * @return
     * @throws SQLException
     */
    E map(ExtendedResultSet rs) throws SQLException;
}
