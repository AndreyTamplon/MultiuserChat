package command_executor.commands;

import command_executor.Command;
import common.Message;
import server.ClientProcessor;
import server.SendingResult;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCommand implements Command
{
    private static final Logger logger = Logger.getLogger(LoginCommand.class.getName());
    private static final int MAXIMUM_NAME_LENGTH = 15;

    @Override
    public void execute(ClientProcessor clientProcessor, Message message)
    {
        String name = message.getMessageText();
        Message response = null;
        if (clientProcessor.isLoggedIn())
        {
            response = new Message("error", String.format("You already logged in with name %s", clientProcessor.getClientName()));
        }
        else
        {
            if (!clientProcessor.getClientServer().getClientsNamesList().contains(name) && name.length() <= MAXIMUM_NAME_LENGTH)
            {
                clientProcessor.setClientName(name);
                String messageText = String.format("Server: User %s logged in", name);
                Message newMessage = new Message("message", messageText);
                clientProcessor.setLoggedIn(true);
                if (clientProcessor.getClientServer().sendMessageHistory(clientProcessor) == SendingResult.FAILURE)
                {
                    logger.log(Level.WARNING, String.format("Failed to send previous messages to %s", name));
                    response = new Message("error", "Failed to send out information to other users");
                }
                if (clientProcessor.getClientServer().sendMessageToEverybody(newMessage) == SendingResult.FAILURE)
                {
                    logger.log(Level.WARNING, String.format("Failed to send out information that user with name %s logged in", name));
                    response = new Message("error", "Failed to send out information to other users");
                }
                else
                {
                    logger.log(Level.INFO, String.format("User %s logged in", name));
                    if (response == null)
                    {
                        response = new Message("success", "");
                    }
                }
            }
            else
            {
                logger.log(Level.INFO, String.format("User %s unable to log in because of invalid name", name));
                response = new Message("error", "the name is already taken or too long");
            }
        }
        clientProcessor.getClientServer().sendMessageToCertainClient(response, clientProcessor);
    }
}
