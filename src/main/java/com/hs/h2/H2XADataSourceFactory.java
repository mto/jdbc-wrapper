package com.hs.h2;

import com.hs.md.HSConfig;
import com.hs.md.Profile;
import org.h2.jdbcx.JdbcDataSource;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/3/17
 */
public class H2XADataSourceFactory implements ObjectFactory {

    static {
        org.h2.Driver.load();
    }

    /**
     * Fork the code of org.h2.jdbcx.JdbcDataSourceFactory and intercept the URL
     */
    @Override
    public synchronized Object getObjectInstance(Object obj, Name name,
                                                 Context nameCtx, Hashtable<?, ?> environment) {
        if (obj instanceof Reference) {
            Reference ref = (Reference) obj;
            if (ref.getClassName().equals(H2XADataSource.class.getName())) {

                String configURL = (String) ref.get("url").getContent();
                String jdbcURL = configURL;
                Profile prof = null;

                if (configURL.startsWith("jdbc:hs:")) {
                    try {
                        String pname = configURL.substring("jdbc:hs:".length());
                        prof = HSConfig.getInstance().getProfile(pname);

                        jdbcURL = prof.getJDBCParam(HSConfig.JDBC_URL_PARAM_NAME);
                    } catch (Exception ex) {
                        //TODO: Add log
                    }
                }

                JdbcDataSource dataSource = new JdbcDataSource();
                dataSource.setURL(jdbcURL);
                dataSource.setUser((String) ref.get("user").getContent());
                dataSource.setPassword((String) ref.get("password").getContent());
                dataSource.setDescription((String) ref.get("description").getContent());
                String s = (String) ref.get("loginTimeout").getContent();
                dataSource.setLoginTimeout(Integer.parseInt(s));

                H2XADataSource h2xaobj = new H2XADataSource(dataSource);
                h2xaobj.injectProfile(prof);

                return h2xaobj;
            }
        }
        return null;
    }

}
