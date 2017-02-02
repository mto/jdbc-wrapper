package com.hs.log;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/1/17
 */
public interface HSLogger {

    void trace(Object msg);

    void trace(Object msg, Throwable t);

    void debug(Object msg);

    void debug(Object msg, Throwable t);

    void info(Object msg);

    void info(Object msg, Throwable t);

    void warn(Object msg);

    void warn(Object msg, Throwable t);

    void error(Object msg);

    void error(Object msg, Throwable t);
}
