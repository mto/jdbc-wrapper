package com.hs.sql;

import com.hs.md.Profile;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/3/17
 */
public class XADataSourceWrapper implements XADataSource {

    private XADataSource target;

    private Profile prof;

    public XADataSourceWrapper(XADataSource target) {
        this(target, null);
    }

    public XADataSourceWrapper(XADataSource target, Profile prof) {
        this.target = target;
        this.prof = prof;
    }

    /**
     * This method is needed when instantiate object via JNDI object factory
     *
     * @param prof
     */
    public void injectProfile(Profile prof) {
        this.prof = prof;
    }

    @Override
    public XAConnection getXAConnection() throws SQLException {
        return prof == null ? target.getXAConnection() : new XAConnectionWrapper(target.getXAConnection(), prof);
    }

    @Override
    public XAConnection getXAConnection(String user, String password) throws SQLException {
        return prof == null ? target.getXAConnection(user, password) : new XAConnectionWrapper(target.getXAConnection(user, password), prof);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        target.setLoginTimeout(seconds);
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        target.setLogWriter(out);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return target.getLoginTimeout();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return target.getLogWriter();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return target.getParentLogger();
    }
}
