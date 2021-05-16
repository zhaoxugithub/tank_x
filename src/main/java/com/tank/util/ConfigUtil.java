package com.tank.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public static Object getObject(String key) {
        String string = getString(key);
        try {
            return Class.forName(string).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Object> getObjectList(String key) {
        List<Object> list = new ArrayList<>();
        String string = getString(key);
        String[] split = string.split(",");
        for (int i = 0; i < split.length; i++) {
            Object o = null;
            try {
                o = Class.forName(split[i]).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            list.add(o);
        }
        return list;
    }
}
