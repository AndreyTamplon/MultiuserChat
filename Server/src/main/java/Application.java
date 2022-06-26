import exceptions.ConfigurationException;
import exceptions.ServerException;
import server.Server;
import server.ServerConfiguration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Application
{
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args)
    {
        ServerConfiguration serverConfiguration;
        try
        {
            serverConfiguration = new ServerConfiguration("/ServerConfiguration.properties");
        }
        catch (ConfigurationException e)
        {
            return;
        }
        try
        {
            if (serverConfiguration.isLoggingEnabled())
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
        Server server;
        try
        {
            server = new Server(205);
        }
        catch (ServerException e)
        {
            logger.log(Level.SEVERE, "Unable to launch server", e);
            return;
        }
        server.start();
    }
}
