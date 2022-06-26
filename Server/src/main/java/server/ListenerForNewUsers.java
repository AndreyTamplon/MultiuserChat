package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ListenerForNewUsers implements Runnable
{
    private static final Logger logger = Logger.getLogger(ListenerForNewUsers.class.getName());
    private final Server server;
    ServerSocket serverSocket;

    public ListenerForNewUsers(int port, Server server) throws IOException
    {
        serverSocket = new ServerSocket(port);
        this.server = server;
    }


    @Override
    public void run()
    {
        Socket newClient;
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                newClient = serverSocket.accept();
                server.addNewClient(newClient);
            }
            catch (IOException e)
            {
                logger.log(Level.WARNING,
                        "An error occurred while trying to accept a connection in the accept() method:", e);
            }
        }
    }
}
