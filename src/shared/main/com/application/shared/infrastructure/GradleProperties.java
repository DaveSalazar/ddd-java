package com.application.shared.infrastructure;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class GradleProperties {

    private final Properties properties;

    public GradleProperties() throws IOException {
        this.properties = new Properties();
        InputStream input = new FileInputStream("./settings.gradle");
        properties.load(input);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
