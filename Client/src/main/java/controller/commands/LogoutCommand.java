package controller.commands;

import client.ClientTerminal;
import common.Message;
import controller.Command;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogoutCommand implements Command
{
    private static final Logger logger = Logger.getLogger(LogoutCommand.class.getName());

    @Override
    public void execute(ClientTerminal clientTerminal, String... arguments)
    {
        if (clientTerminal.isConnected() && clientTerminal.isLoggedIn())
        {
            logger.log(Level.INFO, "logging out from the server");
            Message logoutMessage = new Message("logout", "");
            clientTerminal.sendMessage(logoutMessage);
            clientTerminal.setLoggedIn(false);
            clientTerminal.getMessageStorage().setMessagesValid(false);
        }
        else if (!clientTerminal.isConnected())
        {
            logger.log(Level.WARNING, "attempt to communicate with the server without establishing a connection");
            Message errorMessage = new Message("error", "You are not connected");
            clientTerminal.getMessageStorage().addMessage(errorMessage);
        }
    }
}
