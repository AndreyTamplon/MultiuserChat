package controller.commands;

import client.ClientTerminal;
import common.Message;
import controller.Command;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TextCommand implements Command
{
    private static final Logger logger = Logger.getLogger(TextCommand.class.getName());

    @Override
    public void execute(ClientTerminal clientTerminal, String... arguments)
    {
        if (clientTerminal.isConnected())
        {
            if (arguments.length > 0 && arguments[0].length() > 0)
            {
                logger.log(Level.INFO, "sending a text message to the server");
                Message textMessage = new Message("message", arguments[0]);
                clientTerminal.sendMessage(textMessage);
            }
            else
            {
                logger.log(Level.WARNING, "attempt to send an empty message");
                Message errorMessage = new Message("error", "Empty message");
                clientTerminal.getMessageStorage().addMessage(errorMessage);
            }
        }
        else
        {
            logger.log(Level.WARNING, "attempt to communicate with the server without establishing a connection");
            Message errorMessage = new Message("error", "You are not connected");
            clientTerminal.getMessageStorage().addMessage(errorMessage);
        }
    }
}
