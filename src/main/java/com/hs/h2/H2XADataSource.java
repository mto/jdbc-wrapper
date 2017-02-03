package com.hs.h2;

import com.hs.sql.XADataSourceWrapper;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.XADataSource;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/3/17
 */
public class H2XADataSource extends XADataSourceWrapper {

    public H2XADataSource(JdbcDataSource h2ds){
        super(h2ds);
    }
}
