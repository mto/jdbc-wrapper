package com.hs.log;

import org.apache.log4j.Logger;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/2/17
 */
public class Log4jHSLogger implements HSLogger {

    private final Logger log4j;

    public Log4jHSLogger(Logger _log4j){
        this.log4j = _log4j;
    }

    @Override
    public void trace(Object msg) {
        log4j.trace(msg);
    }

    @Override
    public void trace(Object msg, Throwable t) {
        log4j.trace(msg, t);
    }

    @Override
    public void debug(Object msg) {
        log4j.debug(msg);
    }

    @Override
    public void debug(Object msg, Throwable t) {
        log4j.debug(msg, t);
    }

    @Override
    public void info(Object msg) {
        log4j.info(msg);
    }

    @Override
    public void info(Object msg, Throwable t) {
        log4j.info(msg, t);
    }

    @Override
    public void warn(Object msg) {
        log4j.warn(msg);
    }

    @Override
    public void warn(Object msg, Throwable t) {
        log4j.warn(msg, t);
    }

    @Override
    public void error(Object msg) {
        log4j.error(msg);
    }

    @Override
    public void error(Object msg, Throwable t) {
        log4j.error(msg, t);
    }
}
