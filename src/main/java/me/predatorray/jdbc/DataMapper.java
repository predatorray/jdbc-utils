package me.predatorray.jdbc;

import java.sql.SQLException;

public interface DataMapper<E> {

    E map(ExtendedResultSet rs) throws SQLException;
}
