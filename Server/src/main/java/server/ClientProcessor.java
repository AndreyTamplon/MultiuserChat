package server;

import command_executor.CommandExecutor;
import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientProcessor implements Runnable
{
    private static final Logger logger = Logger.getLogger(ClientProcessor.class.getName());
    private final long id;
    private final Socket socket;
    private final Server server;
    private final CommandExecutor commandExecutor;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final Object writingSynchronisation = new Object();
    private String clientName;
    private boolean isLoggedIn;
    private boolean clientDisconnected;

    public ClientProcessor(long id, Socket socket, Server server, CommandExecutor commandExecutor) throws IOException
    {
        this.id = id;
        clientName = String.format("User%d", id);
        this.socket = socket;
        socket.setSoTimeout(300000);
        this.commandExecutor = commandExecutor;
        this.server = server;
        isLoggedIn = false;
        clientDisconnected = false;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void disconnect()
    {
        server.removeClient(this);
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            logger.log(Level.WARNING, "Error occurred while closing socket", e);
        }
        clientDisconnected = true;
    }

    public SendingResult sendMessage(Message message)
    {
        synchronized (writingSynchronisation)
        {
            try
            {
                if (isLoggedIn
                        || Objects.equals(message.getMessageType(), "error")
                        || Objects.equals(message.getMessageType(), "success"))
                {
                    objectOutputStream.writeObject(message);
                }
            }
            catch (IOException e)
            {
                logger.log(Level.WARNING, String.format("Failed to send message to %s", clientName), e);
                return SendingResult.FAILURE;
            }
        }
        return SendingResult.SUCCESS;
    }

    public void setDefaultName()
    {
        clientName = String.format("User%d", id);
    }

    public boolean isLoggedIn()
    {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        isLoggedIn = loggedIn;
    }

    private Message receiveMessage()
    {
        Message message = null;
        try
        {
            message = (Message) objectInputStream.readObject();
        }
        catch (SocketTimeoutException | SocketException e)
        {
            logger.log(Level.INFO, String.format("Client %s disconnected", clientName));
            disconnect();
            Message disconnectMessage = new Message("message", String.format("Server: User %s disconnected", clientName));
            server.sendMessageToEverybody(disconnectMessage);
        }
        catch (ClassNotFoundException e)
        {
            logger.log(Level.SEVERE, "the message could not be received because the class was not found", e);
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "the message could not be received due to io problem:", e);
        }
        return message;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String name)
    {
        clientName = name;
    }

    public Server getClientServer()
    {
        return server;
    }

    @Override
    public void run()
    {
        Message message;
        do
        {
            message = receiveMessage();
            if (message != null)
            {
                commandExecutor.processCommand(this, message);
            }

        } while (!clientDisconnected);
    }
}
