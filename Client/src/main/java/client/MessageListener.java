package client;

import common.Message;
import common.Publisher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageListener extends Publisher implements Runnable
{
    private static final Logger logger = Logger.getLogger(MessageListener.class.getName());

    private final MessageStorage messageStorage;
    private ObjectInputStream objectInputStream;
    private boolean connectionTerminated;

    public MessageListener(MessageStorage messageStorage)
    {
        this.messageStorage = messageStorage;
    }

    public void connect(Socket socket) throws IOException
    {
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        connectionTerminated = false;
    }

    private Message receiveMessage()
    {
        Message message = null;
        try
        {
            message = (Message) objectInputStream.readObject();
        }
        catch (ClassNotFoundException e)
        {
            logger.log(Level.SEVERE, "the message could not be received because the class was not found", e);
        }
        catch (IOException e)
        {
            connectionTerminated = true;
            logger.log(Level.SEVERE, "Connection terminated");
        }
        return message;
    }

    @Override
    public void run()
    {
        Message message;
        while (true)
        {
            message = receiveMessage();
            if (connectionTerminated)
            {
                messageStorage.addMessage(new Message("error", "The connection to the server is broken"));
                notifySubscribers();
                break;
            }
            if (message != null)
            {
                messageStorage.addMessage(message);
            }
        }
    }
}
