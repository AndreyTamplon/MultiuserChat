package command_executor;

import common.Message;
import exceptions.CommandException;
import exceptions.InvalidCommand;
import server.ClientProcessor;

public abstract class CommandExecutor
{

    protected void executeCommand(ClientProcessor clientProcessor, Message message) throws CommandException
    {
        try
        {
            Command command = createCommand(message.getMessageType());
            command.execute(clientProcessor, message);
        }
        catch (InvalidCommand e)
        {
            throw new CommandException("Couldn't create command");
        }
    }


    public abstract void processCommand(ClientProcessor clientProcessor, Message message);

    protected abstract Command createCommand(String commandName) throws InvalidCommand;

}