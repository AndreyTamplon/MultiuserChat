package command_executor.commands;

import command_executor.Command;
import common.Message;
import server.ClientProcessor;
import server.SendingResult;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserListCommand implements Command
{
    private static final Logger logger = Logger.getLogger(UserListCommand.class.getName());

    @Override
    public void execute(ClientProcessor clientProcessor, Message message)
    {
        Message response;
        if (clientProcessor.isLoggedIn())
        {
            String name = clientProcessor.getClientName();
            List<String> userList = clientProcessor.getClientServer().getClientsNamesList();
            String messageText = String.join("\n", userList);
            Message newMessage = new Message("list", messageText);
            if (clientProcessor.getClientServer().sendMessageToCertainClient(newMessage, clientProcessor) == SendingResult.FAILURE)
            {
                logger.log(Level.WARNING, String.format("the list of users was not sent to %s", name));
                return;
            }
            logger.log(Level.INFO, String.format("Send users list to %s", name));
            response = new Message("success", "");
            clientProcessor.getClientServer().sendMessageToCertainClient(response, clientProcessor);
        }
        else
        {
            response = new Message("error", "You cannot get users list, because are not logged in");
            logger.log(Level.WARNING, "Attempt to get users list without logging in");
            clientProcessor.getClientServer().sendMessageToCertainClient(response, clientProcessor);
        }
    }
}
