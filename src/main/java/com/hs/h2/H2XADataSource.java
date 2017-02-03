package com.hs.h2;

import com.hs.sql.XADataSourceWrapper;

import javax.sql.XADataSource;

/**
 * This class is necessary for JNDI configuration
 *
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/3/17
 */
public class H2XADataSource extends XADataSourceWrapper {

    public H2XADataSource(XADataSource target){
        super(target);
    }
}
