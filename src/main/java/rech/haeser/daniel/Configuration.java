package rech.haeser.daniel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 * @author daniel.rech
 */
@Singleton
public class Configuration {

    private static final String CONFIG_PROPERTIES_FILE_NAME = "config.properties";

    public enum Key {

        DATABASE_HOST("localhost"),
        DATABASE_NAME(null),
        DATABASE_PORT("27017");

        private String value;

        Key(final String aValue) {
            value = aValue;
        }

        private String getValue() {
            return value;
        }

        private void setValue(final String aValue) {
            value = aValue;
        }
    }

    @PostConstruct
    void postConstruct() {

        final URL resource = Configuration.class.getClassLoader().getResource(CONFIG_PROPERTIES_FILE_NAME);

        try (InputStream inputStream = resource.openStream()) {

            final Properties properties = new Properties();
            properties.load(inputStream);
            for (Key key : Key.values()) {
                final String value = properties.getProperty(key.name());
                if (value != null) {
                    key.setValue(value);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not load " + CONFIG_PROPERTIES_FILE_NAME);
        }
    }

    public String get(final Key key) {
        return key.getValue();
    }

    public int getAsInt(final Key key) {
        return Integer.parseInt(get(key));
    }
}
