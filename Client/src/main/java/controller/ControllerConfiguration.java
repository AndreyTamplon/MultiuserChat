package controller;

import exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ControllerConfiguration
{
    private final Properties properties;

    public ControllerConfiguration(String configurationFileName) throws ConfigurationException
    {
        properties = new Properties();
        try (InputStream inputStream = ControllerConfiguration.class.getResourceAsStream(configurationFileName))
        {
            properties.load(inputStream);
        }
        catch (NullPointerException | IOException e)
        {
            throw new ConfigurationException("Failed to load configuration file");
        }
    }

    public String getCommandClassPath(String commandName)
    {
        return properties.getProperty(commandName);
    }

    public String getCommandNameByKeyCode(int keyCode)
    {
        return properties.getProperty(Integer.toString(keyCode));
    }

    public void setConfiguration(String configurationFileName) throws IOException
    {
        properties.load(ControllerConfiguration.class.getResourceAsStream(configurationFileName));
    }
}