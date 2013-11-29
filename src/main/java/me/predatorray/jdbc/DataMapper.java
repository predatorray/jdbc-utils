package me.predatorray.jdbc;

import java.sql.ResultSet;

public interface DataMapper<E> {

    E map(ResultSet rs);
}
