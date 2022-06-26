package command_executor.commands;

import command_executor.Command;
import common.Message;
import server.ClientProcessor;
import server.SendingResult;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TextCommand implements Command
{
    private static final Logger logger = Logger.getLogger(TextCommand.class.getName());

    @Override
    public void execute(ClientProcessor clientProcessor, Message message)
    {
        Message response;
        String name = clientProcessor.getClientName();
        if (clientProcessor.isLoggedIn())
        {
            String messageText = String.format("%s: %s", name, message.getMessageText());
            Message newMessage = new Message("message", messageText);
            if (clientProcessor.getClientServer().sendMessageToEverybody(newMessage) == SendingResult.FAILURE)
            {
                logger.log(Level.WARNING, String.format("Failed to send out %s message", name));
                response = new Message("error", "Failed to send out message to other users");

            }
            else
            {
                response = new Message("success", "");
            }
            logger.log(Level.INFO, String.format("User %s wrote %s", name, message.getMessageText()));
        }
        else
        {
            logger.log(Level.WARNING, String.format("Failed to send out %s message because this user is not logged in", name));
            response = new Message("error", "You have to log in before sending a messages");
        }
        clientProcessor.getClientServer().sendMessageToCertainClient(response, clientProcessor);
    }
}
