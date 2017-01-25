package com.hs.sql;

import com.hs.md.Profile;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import static com.hs.md.HSConfig.*;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/25/17
 */
public class DriverWrapper implements Driver {

    private Driver target;

    private Profile prof;

    public DriverWrapper(Driver target, Profile prof) {
        this.target = target;
        this.prof = prof;
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        Connection rc = target.connect(url, info);

        return new ConnectionWrapper(rc, prof);
    }

    @Override
    public int getMajorVersion() {
        int majorVersion = -1;
        try {
            majorVersion = Integer.parseInt(prof.getJDBCParam(JDBC_DB_MAJOR_VERSION_NAME));
        } catch (Exception ex) {
        }
        return majorVersion;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return target.getPropertyInfo(url, info);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return target.acceptsURL(url);
    }

    @Override
    public boolean jdbcCompliant() {
        return target.jdbcCompliant();
    }

    @Override
    public int getMinorVersion() {
        return target.getMinorVersion();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return target.getParentLogger();
    }
}
