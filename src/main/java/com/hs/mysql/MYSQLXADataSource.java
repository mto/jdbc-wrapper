package com.hs.mysql;

import com.hs.md.Profile;
import com.hs.sql.DataSourceWrapper;
import com.hs.sql.XAConnectionWrapper;

import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import java.sql.SQLException;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/6/17
 */
public class MYSQLXADataSource extends DataSourceWrapper implements XADataSource {

    //Use trick here as Java does not allow multiple class inheritance
    private XADataSource xaTarget;

    public MYSQLXADataSource(DataSource target, XADataSource xaTarget, Profile prof){
        super(target, prof);
        this.xaTarget = xaTarget;
    }

    @Override
    public XAConnection getXAConnection() throws SQLException {
        return new XAConnectionWrapper(xaTarget.getXAConnection(), prof);
    }

    @Override
    public XAConnection getXAConnection(String user, String password) throws SQLException {
        return new XAConnectionWrapper(xaTarget.getXAConnection(user, password), prof);
    }
}
