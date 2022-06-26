package controller;

import client.ClientTerminal;
import exceptions.CommandException;
import exceptions.InvalidCommand;

public abstract class Controller
{
    ClientTerminal clientTerminal;

    protected Controller(ClientTerminal factoryTerminal)
    {
        this.clientTerminal = factoryTerminal;
    }

    protected void executeCommand(String commandName, String... arguments) throws CommandException
    {
        try
        {
            Command command = createCommand(commandName);
            command.execute(clientTerminal, arguments);
        }
        catch (InvalidCommand e)
        {
            throw new CommandException("Couldn't create command");
        }
    }

    public abstract void processCommand(int keycode, String... arguments);

    public abstract void processCommand(String commandName, String... arguments);

    protected abstract Command createCommand(String commandName) throws InvalidCommand;

}