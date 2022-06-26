package command_executor;

import common.Message;
import exceptions.CommandException;
import server.ClientProcessor;

public interface Command
{
    void execute(ClientProcessor clientProcessor, Message message) throws CommandException;
}
