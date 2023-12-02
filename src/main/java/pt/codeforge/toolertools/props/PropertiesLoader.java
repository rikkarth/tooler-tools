package pt.codeforge.toolertools.props;

import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import pt.codeforge.toolertools.internal.exceptions.PropertiesGenerationException;
import pt.codeforge.toolertools.internal.exceptions.PropertiesLoadingException;
import pt.codeforge.toolertools.pathfinder.SystemEnvParser;

public class PropertiesLoader {

    public static PropertiesLoader createPropertiesLoader() {
        return new PropertiesLoader();
    }

    public Properties loadProperties(String propName) throws PropertiesGenerationException {

        String filePath = this.getOptionalFilePath(propName).orElseThrow(
            () -> new PropertiesLoadingException(getPropertiesLoadingErrorMsg(propName)));

        String propsPath = SystemEnvParser.getEnvPath(filePath);

        return this.getOptionalProperties(propsPath).orElseThrow(
            () -> new PropertiesGenerationException(getInvalidPathToPropErrorMsg(propName, propsPath)));
    }

    private String getInvalidPathToPropErrorMsg(String propName, String propsPath) {
        return String.format("Invalid path to %s.properties. Path provided -> %s", propName, propsPath);
    }

    private String getPropertiesLoadingErrorMsg(String propName) {
        return String.format("Error loading %s.properties.", propName);
    }

    private Optional<Properties> getOptionalProperties(String filePath) {
        try {
            Configurations configs = new Configurations();
            Configuration config = configs.properties(filePath);
            Properties properties = new Properties();
            Iterator<String> keys = config.getKeys();
            keys.forEachRemaining((key) -> {
                String value = config.getString(key);
                properties.setProperty(key, value);
            });
            return Optional.of(properties);
        } catch (ConfigurationException ce) {
            return Optional.empty();
        }
    }

    private Optional<String> getOptionalFilePath(String propName) {
        return Optional.ofNullable(System.getProperty(propName));
    }
}

