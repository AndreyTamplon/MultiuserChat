package controller.commands;

import client.ClientTerminal;
import common.Message;
import controller.Command;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCommand implements Command
{
    private static final Logger logger = Logger.getLogger(LoginCommand.class.getName());

    @Override
    public void execute(ClientTerminal clientTerminal, String... arguments)
    {
        if (clientTerminal.isConnected())
        {
            if (arguments.length > 0 && arguments[0].length() > 0)
            {
                logger.log(Level.INFO, "trying to log in onto server");
                Message loginMessage = new Message("login", arguments[0]);
                clientTerminal.sendMessage(loginMessage);
                clientTerminal.setLoggedIn(true);
            }
            else
            {
                logger.log(Level.WARNING, "attempt to log in with an empty name");
                Message errorMessage = new Message("error", "You can't log in with an empty name");
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
