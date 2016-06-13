package utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResourceLoader;
import play.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static java.lang.String.format;

@Slf4j
public final class ConfigurationAccessor {

    private ConfigurationAccessor() {
    }

    /**
     * Convenience method for accessing the value of a String property, configured in one or many
     * applicationX.conf file(s).
     */
    public static String s(String propertyName) {
        return Configuration.root().getString(propertyName);
    }

    /**
     * Same as {@link #s(String)} but parsing the retrieved value as an Integer.
     */
    public static Integer i(String propertyName) {
        return Configuration.root().getInt(propertyName);
    }

    /**
     * Same as {@link #s(String)} but parsing the retrieved value as a Long.
     */
    public static Long l(String propertyName) {
        return Configuration.root().getLong(propertyName);
    }

    /**
     * Same as {@link #s(String)} but parsing the retrieved value as an Boolean.
     */
    public static Boolean b(String propertyName) {
        return Configuration.root().getBoolean(propertyName);
    }

    /**
     * Convenience method for accessing the value of a configuration property, configured in a
     * properties file
     */
    public static String s(String propertyName, Properties props) {
        return props.getProperty(propertyName);
    }

    public static String s(String propertyName, String defaultValue) {
        return Configuration.root().getString(propertyName, defaultValue);
    }

    /**
     * Same as above but with a default value in case the property is not defined
     */
    public static String s(String propertyName, String defaultValue, Properties props) {
        return props.getProperty(propertyName, defaultValue);
    }

    public static List<String> stringList(String propertyName){
        return Configuration.root().getStringList(propertyName);
    }

    public static Long milliseconds(String propertyName) {
        return Configuration.root().getMilliseconds(propertyName);
    }

    @SneakyThrows(IOException.class)
    public static Properties loadConfigPropertiesFrom(String filePath) {
        InputStream is = null;
        String path = (filePath.startsWith("/") ? "file:" : "") + filePath;
        try {
            is = new FileSystemResourceLoader().getResource(path).getInputStream();
        } catch (IOException e) {
            log.error("Could not load configuration properties file from " + filePath, e);
        }
        if (is == null) {
            throw new IllegalStateException(format("properties file %s not found in this project", filePath));
        }
        Properties properties = new Properties();
        properties.load(is);

        return properties;
    }
}
