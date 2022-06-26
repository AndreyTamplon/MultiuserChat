package controller.commands;

import client.ClientTerminal;
import common.Message;
import controller.Command;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ListCommand implements Command
{
    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());

    @Override
    public void execute(ClientTerminal clientTerminal, String... arguments)
    {
        if (clientTerminal.isConnected())
        {
            logger.log(Level.INFO, "sending a request for user list to the server");
            Message listRequestMessage = new Message("list", "");
            clientTerminal.sendMessage(listRequestMessage);
        }
        else
        {
            logger.log(Level.WARNING, "attempt to communicate with the server without establishing a connection");
            Message errorMessage = new Message("error", "You are not connected");
            clientTerminal.getMessageStorage().addMessage(errorMessage);
        }
    }
}
