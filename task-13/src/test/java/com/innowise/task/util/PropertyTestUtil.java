package com.innowise.task.util;

import lombok.SneakyThrows;

import java.util.Properties;

public class PropertyTestUtil {
    private static final Properties PROPERTIES = createPropertiesMap();

    private PropertyTestUtil() {
    }

    public static String getProperty(String name){
        return PROPERTIES.getProperty(name);
    }

    @SneakyThrows
    private static Properties createPropertiesMap() {
        final Properties properties = new Properties();
        properties.load(PropertyTestUtil.class.getClassLoader().getResourceAsStream("application-test.properties"));
        return properties;
    }
}