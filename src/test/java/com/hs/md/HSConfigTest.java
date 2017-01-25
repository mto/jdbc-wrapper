package com.hs.md;

import junit.framework.TestCase;

import java.util.List;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/25/17
 */
public class HSConfigTest extends TestCase {

    public void testLoadConfig(){
        HSConfig config = HSConfig.getInstance();

        assertNotNull(config.getProfile("alias1"));

        Profile p1 = config.getProfile("alias1");

        assertEquals("jdbc:h2:mem", p1.getJDBCParam("jdbcURL"));
        assertEquals("org.h2.Driver", p1.getJDBCParam("jdbcDriver"));
        assertEquals("DB2/NT64", p1.getJDBCParam("DatabaseProductName"));
        assertEquals("10", p1.getJDBCParam("DatabaseMajorVersion"));

        List<SQLRewriter> sqlRws = p1.getSQLRewriters();

        assertEquals(2, sqlRws.size());

        assertEquals("alter table (.*) activate not logged initially with empty table", sqlRws.get(0).getRegex());
        assertEquals("truncate table $1", sqlRws.get(0).getReplacement());

        assertEquals("(^[create|alter].*)(with default)(.*)", sqlRws.get(1).getRegex());
        assertEquals("$1default$3", sqlRws.get(1).getReplacement());
    }
}
