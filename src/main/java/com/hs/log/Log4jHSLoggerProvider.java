package com.hs.log;

import org.apache.log4j.Logger;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/2/17
 */
public class Log4jHSLoggerProvider implements HSLoggerProvider {

    @Override
    public HSLogger getLogger(Class ctxClazz) {
        return new Log4jHSLogger(Logger.getLogger(ctxClazz));
    }

    @Override
    public HSLogger getLogger(String logName) {
        return new Log4jHSLogger(Logger.getLogger(logName));
    }
}
