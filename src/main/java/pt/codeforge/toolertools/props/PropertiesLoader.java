package pt.codeforge.toolertools.props;

import java.io.File;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import pt.codeforge.toolertools.pathfinder.EnvPathParser;

/**
 * Utility class for loading properties from various sources, such as files and system properties, with support for
 * environment variable expansion in file paths.
 *
 * <p>Instances of this class provide methods to load properties based on different inputs, including
 * property names, paths, {@code java.nio.file.Path}, and {@code java.io.File}.
 *
 * <p>Environment variable placeholders in file paths are automatically expanded using the {@link EnvPathParser}.
 *
 * @see EnvPathParser
 * @see PropertiesLoader#loadFromPropertyName(String)
 * @see PropertiesLoader#loadFromPath(String)
 * @see PropertiesLoader#loadFromPath(Path)
 * @see PropertiesLoader#loadFromFile(File)
 */
public class PropertiesLoader {

    private PropertiesLoader() {
        throw new AssertionError("PropertiesLoader should not be instantiated.");
    }

    /**
     * Loads properties from the specified property name, expanding environment variable placeholders in the associated
     * file path.
     *
     * @param propName The name of the property. Must not be null.
     * @return The loaded properties.
     * @throws PropertiesLoadingException If an error occurs during loading.
     * @see EnvPathParser#getEnvPath(String)
     */
    public static Properties loadFromPropertyName(String propName) {

        String propertiesFilePath = getOptionalPropertiesFilePath(propName).orElseThrow(
            () -> new PropertiesLoadingException(getPropertiesLoadingErrorMsg(propName)));

        String propsPath = EnvPathParser.getEnvPath(propertiesFilePath);

        return getOptionalProperties(propsPath).orElseThrow(
            () -> new PropertiesLoadingException(getInvalidPathToPropErrorMsg(propName, propsPath)));
    }

    /**
     * Loads properties from the specified path, expanding environment variable placeholders.
     *
     * @param path The path to the properties file. Must not be null.
     * @return The loaded properties.
     * @throws PropertiesLoadingException If an error occurs during loading.
     * @see EnvPathParser#getEnvPath(String)
     */
    public static Properties loadFromPath(String path) {

        String propsPath = EnvPathParser.getEnvPath(path);

        return getOptionalProperties(propsPath).orElseThrow(
            () -> new PropertiesLoadingException(getInvalidPathToPropErrorMsg(propsPath)));
    }

    /**
     * Loads properties from the specified {@code java.nio.file.Path}, expanding environment variable placeholders.
     *
     * @param path The {@code java.nio.file.Path} to the properties file. Must not be null.
     * @return The loaded properties.
     * @throws PropertiesLoadingException If an error occurs during loading.
     * @see EnvPathParser#getEnvPath(String)
     */
    public static Properties loadFromPath(Path path) {

        String propsPath = EnvPathParser.getEnvPath(path.toString());

        return getOptionalProperties(propsPath).orElseThrow(
            () -> new PropertiesLoadingException(getInvalidPathToPropErrorMsg(propsPath)));
    }

    /**
     * Loads properties from the specified {@code java.io.File}, expanding environment variable placeholders.
     *
     * @param file The {@code java.io.File} representing the properties file. Must not be null.
     * @return The loaded properties.
     * @throws PropertiesLoadingException If an error occurs during loading.
     * @see EnvPathParser#getEnvPath(String)
     */
    public static Properties loadFromFile(File file) {

        String propsPath = EnvPathParser.getEnvPath(file.getAbsolutePath());

        return getOptionalProperties(propsPath).orElseThrow(
            () -> new PropertiesLoadingException(getInvalidPathToPropErrorMsg(propsPath)));
    }

    private static Optional<Properties> getOptionalProperties(String filePath) {
        try {
            Configurations configs = new Configurations();
            Configuration config = configs.properties(filePath);
            Properties properties = new Properties();
            Iterator<String> keys = config.getKeys();
            keys.forEachRemaining(key -> {
                String value = config.getString(key);
                properties.setProperty(key, value);
            });
            return Optional.of(properties);
        } catch (ConfigurationException ce) {
            return Optional.empty();
        }
    }

    private static Optional<String> getOptionalPropertiesFilePath(String propName) {
        try {
            return Optional.ofNullable(System.getProperty(propName));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private static String getInvalidPathToPropErrorMsg(String propName, String path) {
        return String.format("Invalid path to %s.properties. Path provided -> %s", propName, path);
    }

    private static String getInvalidPathToPropErrorMsg(String path) {
        return String.format("Invalid path provided. Path provided -> %s", path);
    }

    private static String getPropertiesLoadingErrorMsg(String propName) {
        if (propName == null) {
            return "Error loading properties. Key can't be null.";
        }

        if (propName.isEmpty()) {
            return "Error loading properties. Key can't be empty.";
        }

        return String.format("Error loading %s.properties.", propName);
    }
}

