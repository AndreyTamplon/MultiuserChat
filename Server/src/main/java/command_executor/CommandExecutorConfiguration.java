package command_executor;

import exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommandExecutorConfiguration
{
    private final Properties properties;

    public CommandExecutorConfiguration(String configurationFileName) throws ConfigurationException
    {
        properties = new Properties();
        try (InputStream inputStream = CommandExecutorConfiguration.class.getResourceAsStream(configurationFileName))
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

    public void setConfiguration(String configurationFileName) throws IOException
    {
        properties.load(CommandExecutorConfiguration.class.getResourceAsStream(configurationFileName));
    }
}