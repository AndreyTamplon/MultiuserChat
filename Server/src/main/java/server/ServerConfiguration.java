package server;

import exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfiguration
{
    private final Properties properties;

    public ServerConfiguration(String configurationFileName) throws ConfigurationException
    {
        properties = new Properties();
        try (InputStream inputStream = ServerConfiguration.class.getResourceAsStream(configurationFileName))
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