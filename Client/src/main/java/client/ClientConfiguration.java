package client;

import exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientConfiguration
{
    private final Properties properties;

    public ClientConfiguration(String configurationFileName) throws ConfigurationException
    {
        properties = new Properties();
        try (InputStream inputStream = ClientConfiguration.class.getResourceAsStream(configurationFileName))
        {
            properties.load(inputStream);
        }
        catch (NullPointerException | IOException e)
        {
            throw new ConfigurationException("Failed to load configuration file");
        }
    }

    public boolean isLoggingEnabled()
    {
        return Boolean.parseBoolean(properties.getProperty("Log"));
    }
}