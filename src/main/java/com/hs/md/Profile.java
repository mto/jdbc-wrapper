package com.hs.md;

import com.hs.log.HSLogger;
import com.hs.log.HSLoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/25/17
 */
public class Profile {

    private String name;

    private Map<String, String> jdbcParams = new HashMap<>();

    private List<SQLRewriter> sqlRewriters = new LinkedList<>();

    private static HSLogger log = HSLoggerFactory.getLogger(Profile.class);

    public String getName() {
        return name;
    }

    public String getJDBCParam(String key) {
        return jdbcParams.get(key);
    }

    public void addSQLRewriter(String regex, String replacement) {
        sqlRewriters.add(new SQLRewriter(regex, replacement));
    }

    public void addJDBCParam(String key, String value) {
        jdbcParams.put(key, value);
    }

    public List<SQLRewriter> getSQLRewriters() {
        return Collections.unmodifiableList(sqlRewriters);
    }

    public String rewriteSQL(String sql) {
        log.trace("Start rewriting SQL query [ " + sql + " ] via the profile " + getName());
        String tmp = sql;

        for (SQLRewriter rw : sqlRewriters) {
            Matcher m = rw.pattern.matcher(tmp);
            boolean result = m.find();
            if (result) {
                log.debug("Incremental SQL query [ " + tmp + " ] matches with the pattern [ " + rw.getRegex() + " ] from the profile " + getName());
                StringBuffer buf = new StringBuffer();

                do {
                    m.appendReplacement(buf, rw.replacement);
                    result = m.find();
                } while (result);

                m.appendTail(buf);
                tmp = buf.toString();
                log.debug("Subsequences matching the pattern [ " + rw.getRegex() + " ] were replaced with [ " + rw.getReplacement() + " ]. The new incremental query is [ " + tmp + " ]");
            }
        }

        return tmp;
    }

    public static class Builder {

        private final static Pattern SQLK_REGEX = Pattern.compile("sql.([0-9]+).pattern");

        private String name;

        private List<String> lines = new LinkedList<>();

        public Builder(String name) {
            this.name = name;
        }

        public Builder addLine(String l) {
            lines.add(l);

            return this;
        }

        public Profile build() {
            final Profile p = new Profile();
            p.name = name;

            Iterator<String> rd = lines.iterator();
            while (rd.hasNext()) {
                String[] kv = rd.next().split("=");

                if (kv.length != 2) {
                    continue;
                }

                String k = kv[0].trim();
                String v = kv[1].trim();

                if (SQLK_REGEX.matcher(k).matches()) {
                    //We expect the replacement sequence and read for next line
                    String replSeq = "";

                    try {
                        String[] nkv = rd.next().split("=");
                        if (nkv[0].trim().endsWith(".substitution")) {
                            replSeq = nkv[1].trim();
                        }
                    } catch (Exception ex) {
                    }

                    if (!replSeq.isEmpty()) {
                        p.addSQLRewriter(v, replSeq);
                    }
                } else {
                    p.addJDBCParam(k, v);
                }
            }

            return p;
        }
    }
}
