package client;

import common.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageSender
{
    private static final Logger logger = Logger.getLogger(MessageSender.class.getName());
    private ObjectOutputStream objectOutputStream;


    public void connect(Socket socket) throws IOException
    {
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void sendMessage(Message message)
    {
        try
        {
            objectOutputStream.writeObject(message);
        }
        catch (SocketException e)
        {
            logger.log(Level.WARNING, "Socket has been closed", e);
        }
        catch (IOException e)
        {
            logger.log(Level.WARNING, "Failed to send message to server", e);
        }
    }
}
