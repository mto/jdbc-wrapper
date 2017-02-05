package com.hs.mysql;

import com.hs.sql.XADataSourceWrapper;

import javax.sql.XADataSource;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/6/17
 */
public class MYSQLXADataSource extends XADataSourceWrapper {

    public MYSQLXADataSource(XADataSource target){
        super(target);
    }
}
