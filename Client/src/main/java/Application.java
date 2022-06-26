import client.ClientConfiguration;
import client.ClientTerminal;
import client.MessageStorage;
import controller.BasicController;
import controller.Controller;
import exceptions.ConfigurationException;
import view.MainFrame;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Application
{
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args)
    {
        ClientConfiguration clientConfiguration;
        try
        {
            clientConfiguration = new ClientConfiguration("/ClientConfiguration.properties");
        }
        catch (ConfigurationException e)
        {
            return;
        }

        try
        {
            if (clientConfiguration.isLoggingEnabled())
            {
                LogManager.getLogManager().readConfiguration(Application.class.getResourceAsStream("EnabledLogger.properties"));
            }
            else
            {
                LogManager.getLogManager().readConfiguration(Application.class.getResourceAsStream("DisabledLogger.properties"));
            }
        }
        catch (NullPointerException | IOException e)
        {
            return;
        }
        MessageStorage messageStorage = new MessageStorage();
        ClientTerminal clientTerminal;
        try
        {
            clientTerminal = new ClientTerminal(messageStorage);
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "Unable to establish connection");
            return;
        }

        Controller controller;
        try
        {
            controller = new BasicController("/ControllerConfiguration.properties", clientTerminal);
        }
        catch (ConfigurationException e)
        {
            logger.log(Level.SEVERE, "Unable to create controller");
            return;
        }
        MainFrame mainFrame = new MainFrame(clientTerminal, controller);
        mainFrame.setVisible(true);
    }
}
