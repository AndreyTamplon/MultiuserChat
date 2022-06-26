package command_executor.commands;

import command_executor.Command;
import common.Message;
import server.ClientProcessor;
import server.SendingResult;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogoutCommand implements Command
{
    private static final Logger logger = Logger.getLogger(LogoutCommand.class.getName());

    @Override
    public void execute(ClientProcessor clientProcessor, Message message)
    {
        if (clientProcessor.isLoggedIn())
        {
            String name = clientProcessor.getClientName();
            String messageText = String.format("Server: User %s disconnected", name);
            Message newMessage = new Message("message", messageText);
            if (clientProcessor.getClientServer().sendMessageToEverybody(newMessage) == SendingResult.FAILURE)
            {
                logger.log(Level.WARNING, String.format("Failed to send out information that user with name %s disconnected", name));
            }
            clientProcessor.setLoggedIn(false);
            clientProcessor.setDefaultName();
            logger.log(Level.INFO, String.format("User %s disconnected", name));
        }
        else
        {
            Message response = new Message("error", "You cannot log out from the server if you are not logged in");
            logger.log(Level.WARNING, "Attempt to log out from the server without logging in");
            clientProcessor.getClientServer().sendMessageToCertainClient(response, clientProcessor);
        }
    }
}
