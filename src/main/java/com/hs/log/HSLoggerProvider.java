package com.hs.log;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/1/17
 */
public interface HSLoggerProvider {

    HSLogger getLogger(Class ctxClazz);

    HSLogger getLogger(String logName);
}
