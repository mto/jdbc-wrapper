package com.hs.mysql;

import com.hs.md.HSConfig;
import com.hs.md.Profile;
import com.mysql.jdbc.NonRegisteringDriver;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/6/17
 */
public class MYSQLXADataSourceFactory implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        Reference ref = (Reference) obj;
        String className = ref.getClassName();

        if ("com.hs.mysql.MYSQLXADataSource".equals(className)) {

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

            MysqlXADataSource dataSource;
            try {
                String mysqlDsClazz = "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource";
                dataSource = (MysqlXADataSource) Class.forName(mysqlDsClazz)
                        .newInstance();
            } catch (Exception ex) {
                throw new RuntimeException("Unable to create DataSource of "
                        + "class '" + className + "', reason: " + ex.toString());
            }

            int portNumber = 3306;

            String portNumberAsString = nullSafeRefAddrStringGet("port", ref);

            if (portNumberAsString != null) {
                portNumber = Integer.parseInt(portNumberAsString);
            }

            dataSource.setPort(portNumber);

            String user = nullSafeRefAddrStringGet(NonRegisteringDriver.USER_PROPERTY_KEY, ref);

            if (user != null) {
                dataSource.setUser(user);
            }

            String password = nullSafeRefAddrStringGet(NonRegisteringDriver.PASSWORD_PROPERTY_KEY, ref);

            if (password != null) {
                dataSource.setPassword(password);
            }

            String serverName = nullSafeRefAddrStringGet("serverName", ref);

            if (serverName != null) {
                dataSource.setServerName(serverName);
            }

            String databaseName = nullSafeRefAddrStringGet("databaseName", ref);

            if (databaseName != null) {
                dataSource.setDatabaseName(databaseName);
            }

            /*
            String explicitUrlAsString = nullSafeRefAddrStringGet("explicitUrl", ref);

            if (explicitUrlAsString != null) {
                if (Boolean.valueOf(explicitUrlAsString).booleanValue()) {
                    dataSource.setUrl(nullSafeRefAddrStringGet("url", ref));
                }
            }
            */

            dataSource.setPropertiesViaRef(ref);
            dataSource.setUrl(jdbcURL);

            MYSQLXADataSource msqlDs = new MYSQLXADataSource(dataSource, dataSource, prof);

            return msqlDs;
        }

        // We can't create an instance of the reference
        return null;

    }

    private String nullSafeRefAddrStringGet(String referenceName, Reference ref) {
        RefAddr refAddr = ref.get(referenceName);

        String asString = refAddr != null ? (String) refAddr.getContent() : null;

        return asString;
    }

}
