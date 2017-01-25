package com.hs;

import com.hs.md.HSConfig;
import com.hs.md.Profile;
import com.hs.sql.DriverWrapper;

import java.sql.Driver;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/25/17
 */
public class HSDriverManager {

    public static Driver loadDriver(String url) {
        if (!url.startsWith("jdbc:hs:")) {
            throw new IllegalArgumentException("Input " + url + " does not have format 'jdbc:hs:alias'");
        }

        String pname = url.substring("jdbc:hs:".length());
        Profile p = HSConfig.getInstance().getProfile(pname);

        if (p == null) {
            throw new IllegalArgumentException("Alias '" + pname + "' not found in the hs.properties config file");
        }

        try {
            String jdbcDriver = p.getJDBCParam(HSConfig.JDBC_DRIVER_PARAM_NAME);

            Class<?> clazz = Class.forName(jdbcDriver);
            Driver rd = clazz.asSubclass(Driver.class).newInstance();

            return new DriverWrapper(rd, p);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
