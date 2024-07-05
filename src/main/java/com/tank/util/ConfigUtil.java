package com.tank.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * 单利模式
 */
@Slf4j
public class ConfigUtil {
    private static Properties props = new Properties();

    static {
        try {
            props.load(ResourceUtil.getStream("config.ini"));
        } catch (IOException e) {
            log.error("读取配置文件失败", e);
        }
    }

    private ConfigUtil() {
    }

    public static Object get(String key, Object defaultValue) {
        return Optional.ofNullable(props.get(key))
                .orElse(defaultValue);
    }

    public static Integer getInteger(String key, Integer defaultValue) {
        return Convert.toInt(get(key, defaultValue));
    }

    public static String getString(String key, String defaultValue) {
        return Convert.toStr(get(key, defaultValue));
    }
}
