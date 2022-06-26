package controller;

import client.ClientTerminal;
import exceptions.CommandException;
import exceptions.ConfigurationException;
import exceptions.InvalidCommand;

import java.lang.reflect.InvocationTargetException;

public class BasicController extends Controller
{
    private final ControllerConfiguration controllerConfiguration;

    public BasicController(String configurationFileName, ClientTerminal clientTerminal)
            throws ConfigurationException
    {
        super(clientTerminal);
        controllerConfiguration = new ControllerConfiguration(configurationFileName);
    }

    public void processCommand(int keyCode, String... arguments)
    {
        if (controllerConfiguration.getCommandNameByKeyCode(keyCode) != null)
        {
            processCommand(controllerConfiguration.getCommandNameByKeyCode(keyCode), arguments);
        }

    }

    public void processCommand(String commandName, String... arguments)
    {
        try
        {
            executeCommand(commandName, arguments);
        }
        catch (CommandException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected Command createCommand(String commandName) throws InvalidCommand
    {
        try
        {
            return (Command) Class.forName(controllerConfiguration.getCommandClassPath(commandName)).getDeclaredConstructor().newInstance();
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
