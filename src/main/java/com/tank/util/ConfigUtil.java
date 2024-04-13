package com.tank.util;

import com.google.common.base.MoreObjects;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Objects;
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
        String property = props.getProperty(key, "0");
        return Integer.parseInt(property);
    }

    public static String getString(String key) {
        return props.getProperty(key, "");
    }
}
