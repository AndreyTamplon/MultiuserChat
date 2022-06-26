package command_executor;

import common.Message;
import exceptions.CommandException;
import exceptions.ConfigurationException;
import exceptions.InvalidCommand;
import server.ClientProcessor;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BasicCommandExecutor extends CommandExecutor
{
    private static final Logger logger = Logger.getLogger(BasicCommandExecutor.class.getName());
    private final CommandExecutorConfiguration commandExecutorConfiguration;

    public BasicCommandExecutor(String configurationFileName)
            throws ConfigurationException
    {
        commandExecutorConfiguration = new CommandExecutorConfiguration(configurationFileName);
    }

    public void processCommand(ClientProcessor clientProcessor, Message message)
    {
        try
        {
            executeCommand(clientProcessor, message);
        }
        catch (CommandException e)
        {
            logger.log(Level.WARNING, String.format("there is no command named %s", message.getMessageType()), e);
        }
    }

    @Override
    protected Command createCommand(String commandName) throws InvalidCommand
    {
        try
        {
            return (Command) Class.forName(commandExecutorConfiguration.getCommandClassPath(commandName)).getDeclaredConstructor().newInstance();
        }
        catch (ClassNotFoundException | NullPointerException e)
        {
            throw new InvalidCommand("Could not find an command with the name " + commandName);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            throw new InvalidCommand("Could not create an command with the name " + commandName, e);
        }
    }
}
