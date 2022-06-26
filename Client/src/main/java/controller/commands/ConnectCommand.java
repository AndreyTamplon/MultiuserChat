package controller.commands;

import client.ClientTerminal;
import common.Message;
import controller.Command;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectCommand implements Command
{
    private static final Logger logger = Logger.getLogger(ConnectCommand.class.getName());

    @Override
    public void execute(ClientTerminal clientTerminal, String... arguments)
    {
        Message errorMessage;
        InetAddress address;
        if (arguments.length != 2)
        {
            logger.log(Level.WARNING, "Attempt to establish a connection with fewer arguments than necessary");
            errorMessage = new Message("error", "Not enough arguments to establish a connection. Enter the address and port");
            clientTerminal.getMessageStorage().addMessage(errorMessage);
            return;
        }
        try
        {
            address = InetAddress.getByName(arguments[0]);
        }
        catch (UnknownHostException e)
        {
            logger.log(Level.WARNING, "Attempt to connect to a non-existent or unavailable host");
            errorMessage = new Message("error", "This host does not exist or it is unavailable");
            clientTerminal.getMessageStorage().addMessage(errorMessage);
            return;
        }
        String portValidation = "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$";
        if (arguments[1].matches(portValidation))
        {
            try
            {
                clientTerminal.connect(address, Integer.parseInt(arguments[1]));
            }
            catch (IOException e)
            {
                logger.log(Level.WARNING, "Connection could not be established", e);
                errorMessage = new Message("error", "Connection could not be established");
                clientTerminal.getMessageStorage().addMessage(errorMessage);
            }
        }
        else
        {
            logger.log(Level.WARNING, "Attempt to connect with port not matching 0-65535");
            errorMessage = new Message("error", "The port value ranges from zero to 65535");
            clientTerminal.getMessageStorage().addMessage(errorMessage);
        }

    }
}
