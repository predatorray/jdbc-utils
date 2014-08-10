package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A simple DataSource implementation. All connections from this DataSource
 * will be generated by {@link java.sql.DriverManager}.git stauts
 */
public class DriverManagerDataSource extends AbstractDataSource {

    private final String url;
    private final String username;
    private final String password;

    /**
     * Construct a SimpleDataSource with JDBC Driver class name and a database
     * url
     * @param className the name of a {@link java.sql.Driver} implementation
     *                  class
     * @param url the URL to the database
     * @throws ClassNotFoundException if the class name is not found
     */
    public DriverManagerDataSource(String className, String url)
            throws ClassNotFoundException {
        this(className, url, null, null);
    }

    /**
     * Construct a SimpleDataSource with JDBC Driver class name, a database url
     * and a username of the database instance
     * @param className the name of a {@link java.sql.Driver} implementation
     *                  class
     * @param url the URL to the database
     * @param username the username to the database instance
     * @throws ClassNotFoundException if the class name is not found
     */
    public DriverManagerDataSource(String className, String url, String username)
            throws ClassNotFoundException {
        this(className, url, username, null);
    }

    /**
     * Construct a SimpleDataSource with JDBC Driver class name, a database url
     * and username/password pairs of the
     * database instance
     * @param className the name of a {@link java.sql.Driver} implementation
     *                  class
     * @param url the URL to the database
     * @param username the username to the database instance
     * @param password the password of the username
     * @throws ClassNotFoundException
     */
    public DriverManagerDataSource(String className, String url, String username,
                                   String password) throws ClassNotFoundException {
        Check.argumentIsNotNull(className, "className must not be null");
        Check.argumentIsNotNull(url , "url must not be null");

        Class.forName(className);

        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Attempts to establish a connection with the data source using the url,
     * username, password passed during the construction.
     * @return the connection with the data source
     * @throws SQLException when there is any exception occurred when
     *                      establishing the connection
     */
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Attempts to establish a connection with the data source using the url
     * which is passed during the construction, and username/password pairs
     * overriding the original configuration.
     * @param username the username to the database instance
     * @param password the password of the username
     * @return the connection with the data source
     * @throws SQLException when there is any exception occurred when
     *                      establishing the connection
     */
    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }
}