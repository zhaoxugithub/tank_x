package com.tank.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 单利模式
 */
public class ConfigUtil {
    static Properties props = new Properties();

    static {
        try {
            props.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config.ini"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ConfigUtil() {
    }

    public static Object get(String key) {
        if (props == null) return null;
        return props.get(key);
    }

    public static int getInteger(String key) {
        return Integer.parseInt((String) get(key));
    }

    public static String getString(String key) {
        return (String) get(key);
    }
}
