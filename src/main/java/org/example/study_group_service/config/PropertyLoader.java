package org.example.study_group_service.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private final Properties props = new Properties();

    public PropertyLoader(String resource) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
            if (is == null) throw new IllegalStateException("Cannot find " + resource);
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String key) { return props.getProperty(key); }
    public String getOrDefault(String key, String def) { return props.getProperty(key, def); }
    public int getInt(String key, int def) { String v = props.getProperty(key); return v==null?def:Integer.parseInt(v); }
    public long getLong(String key, long def) { String v = props.getProperty(key); return v==null?def:Long.parseLong(v); }
    public boolean getBool(String key, boolean def) { String v = props.getProperty(key); return v==null?def:Boolean.parseBoolean(v); }
}