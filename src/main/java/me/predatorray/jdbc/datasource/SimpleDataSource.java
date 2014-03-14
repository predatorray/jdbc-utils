package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleDataSource extends AbstractDataSource {

    private final String url;
    private final String username;
    private final String password;

    public SimpleDataSource(String className, String url)
            throws ClassNotFoundException {
        this(className, url, null, null);
    }

    public SimpleDataSource(String className, String url, String username)
            throws ClassNotFoundException {
        this(className, url, username, null);
    }

    public SimpleDataSource(String className, String url, String username,
                            String password) throws ClassNotFoundException {
        Check.argumentIsNotNull(className, "className must not be null");
        Check.argumentIsNotNull(url , "url must not be null");

        Class.forName(className);

        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }
}
