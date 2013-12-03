package me.predatorray.jdbc;

import me.predatorray.jdbc.datasource.CloseOnCompletionConnection;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JdbcTemplate {

    private final DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        Check.argumentIsNotNull(dataSource, "dataSource cannot be null");
        this.dataSource = dataSource;
    }

    public <E> List<E> query(String sql, PreparedStatementSetter setter,
                             DataMapper<E> dataMapper)
            throws DataAccessException {
        return query(sql, setter, dataMapper, new LinkedList<E>());
    }

    public <E> List<E> query(String sql, List<Object> parameters,
                             DataMapper<E> dataMapper)
            throws DataAccessException {
        return query(sql, parameters, dataMapper, new LinkedList<E>());
    }

    public <E> List<E> query(String sql, List<Object> parameters,
                             DataMapper<E> dataMapper, List<E> resultList)
            throws DataAccessException {
        Check.argumentIsNotNull(parameters, "parameters cannot be null");

        PreparedStatementSetter setter = new SimplePreparedStatementSetter(
                parameters);
        return query(sql, setter, dataMapper, resultList);
    }

    public <E> List<E> query(String sql, PreparedStatementSetter setter,
                             DataMapper<E> dataMapper, List<E> resultList)
            throws DataAccessException {
        Check.argumentIsNotNull(setter, "setter cannot be null");
        Check.argumentIsNotNull(dataMapper, "dataMapper cannot be null");

        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            setter.setPreparedStatement(ps);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                E entity = dataMapper.map(rs);
                resultList.add(entity);
            }

            return resultList;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        } finally {
            closeConnection(connection);
        }
    }

    public <E> E queryOne(String sql, List<Object> parameters,
                          DataMapper<E> dataMapper)
            throws DataAccessException {
        Check.argumentIsNotNull(parameters, "parameters cannot be null");

        PreparedStatementSetter setter = new SimplePreparedStatementSetter(
                parameters);
        return queryOne(sql, setter,dataMapper);
    }

    public <E> E queryOne(String sql, PreparedStatementSetter setter,
                          DataMapper<E> dataMapper)
            throws DataAccessException {
        Check.argumentIsNotNull(setter, "setter cannot be null");
        Check.argumentIsNotNull(dataMapper, "dataMapper cannot be null");

        Connection connection = getConnection();
        try {
            connection.setReadOnly(true);

            PreparedStatement ps = connection.prepareStatement(sql);
            setter.setPreparedStatement(ps);

            ResultSet rs = ps.executeQuery();
            return (rs.next()) ? dataMapper.map(rs) : null;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        } finally {
            closeConnection(connection);
        }
    }

    public int update(String sql, List<Object> parameters)
            throws DataAccessException {
        Check.argumentIsNotNull(parameters, "parameters cannot be null");

        PreparedStatementSetter setter = new SimplePreparedStatementSetter(
                parameters);
        return update(sql, setter);
    }

    public int update(String sql, PreparedStatementSetter setter)
            throws DataAccessException {
        Check.argumentIsNotNull(setter, "setter cannot be null");

        Connection connection = getConnection();
        try {
            connection.setReadOnly(false);

            PreparedStatement ps = connection.prepareStatement(sql);
            setter.setPreparedStatement(ps);

            return ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        } finally {
            closeConnection(connection);
        }
    }

    public <K> List<K> update(String sql, List<Object> parameters,
                              DataMapper<K> keyMapper)
            throws DataAccessException {
        Check.argumentIsNotNull(parameters, "parameters cannot be null");
        Check.argumentIsNotNull(keyMapper, "keyMapper cannot be null");

        PreparedStatementSetter setter = new SimplePreparedStatementSetter(
                parameters);
        return update(sql, setter, keyMapper);
    }

    public <K> List<K> update(String sql, PreparedStatementSetter setter,
                              DataMapper<K> keyMapper)
            throws DataAccessException {
        Check.argumentIsNotNull(setter, "setter cannot be null");
        Check.argumentIsNotNull(keyMapper, "keyMapper cannot be null");

        Connection connection = getConnection();
        try {
            connection.setReadOnly(false);

            PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            setter.setPreparedStatement(ps);

            int row = ps.executeUpdate();
            List<K> keyList = new ArrayList<K>(row);

            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                K key = keyMapper.map(rs);
                keyList.add(key);
            }
            return keyList;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        } finally {
            closeConnection(connection);
        }
    }

    public <K> K updateOne(String sql, List<Object> parameters,
                           DataMapper<K> keyMapper)
            throws DataAccessException {
        Check.argumentIsNotNull(parameters, "parameters cannot be null");
        Check.argumentIsNotNull(keyMapper, "keyMapper cannot be null");

        PreparedStatementSetter setter = new SimplePreparedStatementSetter(
                parameters);
        return updateOne(sql, setter, keyMapper);
    }

    public <K> K updateOne(String sql, PreparedStatementSetter setter,
                           DataMapper<K> keyMapper)
            throws DataAccessException {
        Check.argumentIsNotNull(setter, "setter cannot be null");
        Check.argumentIsNotNull(keyMapper, "keyMapper cannot be null");

        Connection connection = getConnection();
        try {
            connection.setReadOnly(false);

            PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            setter.setPreparedStatement(ps);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            return (rs.next()) ? keyMapper.map(rs) : null;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        } finally {
            closeConnection(connection);
        }
    }

    public BatchUpdater batchUpdater(String sql) {
        Connection connection = getConnection();
        try {
            connection.setReadOnly(false);

            PreparedStatement ps = connection.prepareStatement(sql);
            return new BatchUpdater(ps);
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        } finally {
            closeConnection(connection);
        }
    }

    protected Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(true);
            return new CloseOnCompletionConnection(connection);
        } catch (SQLException ex) {
            throw new DataAccessException(
                    "failed to get a connection from the data source", ex);
        }
    }

    protected void closeConnection(Connection connection)
            throws DataAccessException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new DataAccessException(
                    "failed to close the connection", ex);
        }
    }
}
