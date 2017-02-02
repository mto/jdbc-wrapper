package com.hs.log;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 2/2/17
 */
public class HSLoggerFactory {

    private final HSLoggerProvider prov;

    private static HSLoggerFactory instance;

    HSLoggerFactory(HSLoggerProvider _prov) {
        this.prov = _prov;
    }

    private HSLogger _getLogger(Class ctxClazz) {
        return prov.getLogger(ctxClazz);
    }

    private HSLogger _getLogger(String clzName) {
        return prov.getLogger(clzName);
    }

    public static HSLoggerFactory getInstance() {
        if (instance == null) {
            synchronized (HSLoggerFactory.class) {
                if (instance == null) {
                    Iterator<HSLoggerProvider> lps = ServiceLoader.load(HSLoggerProvider.class).iterator();
                    HSLoggerFactory tmp = new HSLoggerFactory(lps.hasNext() ? lps.next() : new Log4jHSLoggerProvider());

                    instance = tmp;
                }
            }
        }

        return instance;
    }


    public static HSLogger getLogger(Class ctxClazz) {
        return getInstance()._getLogger(ctxClazz);
    }

    public static HSLogger getLogger(String clzName) {
        return getInstance()._getLogger(clzName);
    }
}
