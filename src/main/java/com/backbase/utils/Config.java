package com.backbase.utils;

import java.io.IOException;
import java.util.Properties;

public class Config {
    protected static Properties props = new Properties();

    static {
        try {
            props.load(Config.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static Properties getProps() {
        return props;
    }

    public static String getProperty(String name) {
        if (!props.containsKey(name)) {
            throw new RuntimeException("Config option with name - " + name + "does not exists.");
        }
        return props.getProperty(name);
    }
}
