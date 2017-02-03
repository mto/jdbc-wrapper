package com.hs.sql;

import com.hs.md.Profile;

import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;
import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/3/17
 */
public class XAConnectionWrapper implements XAConnection {

    private final XAConnection target;

    private Profile prof;

    public XAConnectionWrapper(XAConnection target, Profile prof){
        this.target = target;
        this.prof = prof;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new ConnectionWrapper(target.getConnection(), prof);
    }

    @Override
    public XAResource getXAResource() throws SQLException {
        return target.getXAResource();
    }

    @Override
    public void addConnectionEventListener(ConnectionEventListener listener) {
        target.addConnectionEventListener(listener);
    }

    @Override
    public void removeConnectionEventListener(ConnectionEventListener listener) {
        target.removeConnectionEventListener(listener);
    }

    @Override
    public void addStatementEventListener(StatementEventListener listener) {
        target.addStatementEventListener(listener);
    }

    @Override
    public void removeStatementEventListener(StatementEventListener listener) {
        target.removeStatementEventListener(listener);
    }

    @Override
    public void close() throws SQLException {
        target.close();
    }

}
