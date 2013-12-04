package me.predatorray.jdbc;

public interface DataMapper<E> {

    E map(ExtendedResultSet rs);
}
