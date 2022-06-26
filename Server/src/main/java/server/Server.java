package server;

import command_executor.BasicCommandExecutor;
import command_executor.CommandExecutor;
import common.Message;
import exceptions.ConfigurationException;
import exceptions.ServerException;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private static final int NUMBER_OF_PAST_MESSAGES = 10;
    private final List<ClientProcessor> clients;
    private final ListenerForNewUsers listenerForNewUsers;
    private final Object clientsSynchronisation = new Object();
    private final IdGenerator idGenerator;
    private final CommandExecutor commandExecutor;
    private final MessageStorage messageStorage;
    private ExecutorService executor;

    public Server(int port) throws ServerException
    {
        messageStorage = new MessageStorage();
        clients = new ArrayList<>();
        try
        {
            listenerForNewUsers = new ListenerForNewUsers(port, this);
        }
        catch (IOException e)
        {
            throw new ServerException("Unable to create listener for new users", e);
        }
        idGenerator = new IdGenerator();
        try
        {
            commandExecutor = new BasicCommandExecutor("/CommandExecutorConfiguration.properties");
        }
        catch (ConfigurationException e)
        {
            throw new ServerException("Unable to create commandExecutor for server", e);
        }
    }

    private static ExecutorService newCachedThreadPool()
    {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
    }

    public void start()
    {
        executor = newCachedThreadPool();
        executor.execute(listenerForNewUsers);
    }

    public void addNewClient(Socket socket)
    {
        ClientProcessor clientProcessor;
        try
        {
            clientProcessor = new ClientProcessor(idGenerator.getNewId(), socket, this, commandExecutor);
        }
        catch (IOException e)
        {
            logger.log(Level.WARNING, "Client failed to join due to socket failure", e);
            return;
        }
        logger.log(Level.INFO, String.format("New user from %s connected", socket.getInetAddress()));
        synchronized (clientsSynchronisation)
        {
            clients.add(clientProcessor);
            executor.execute(clientProcessor);
            Message successConnectionMessage = new Message("success", "You have successfully connected to the server");
            sendMessageToCertainClient(successConnectionMessage, clientProcessor);
        }
    }

    public List<String> getClientsNamesList()
    {
        List<String> clientsList = new ArrayList<>();
        synchronized (clientsSynchronisation)
        {
            for (ClientProcessor client : clients)
            {
                clientsList.add(client.getClientName());
            }
        }
        return clientsList;
    }

    public SendingResult sendMessageToEverybody(Message message)
    {
        boolean mistakesOccurred = false;
        synchronized (clientsSynchronisation)
        {
            messageStorage.addMessage(message);
            for (ClientProcessor clientProcessor : clients)
            {
                if (clientProcessor.sendMessage(message) == SendingResult.FAILURE)
                {
                    mistakesOccurred = true;
                }
            }
        }
        if (mistakesOccurred)
        {
            return SendingResult.FAILURE;
        }
        return SendingResult.SUCCESS;
    }

    public SendingResult sendMessageToCertainClient(Message message, ClientProcessor recipient)
    {
        synchronized (clientsSynchronisation)
        {
            if (recipient.sendMessage(message) == SendingResult.FAILURE)
            {
                return SendingResult.FAILURE;
            }
            return SendingResult.SUCCESS;
        }
    }

    public SendingResult sendMessageHistory(ClientProcessor recipient)
    {
        boolean mistakesOccurred = false;
        synchronized (clientsSynchronisation)
        {
            for (Message message : messageStorage.getLastNMessages(NUMBER_OF_PAST_MESSAGES))
            {
                if (recipient.sendMessage(message) == SendingResult.FAILURE)
                {
                    mistakesOccurred = true;
                }
            }
        }
        if (mistakesOccurred)
        {
            return SendingResult.FAILURE;
        }
        return SendingResult.SUCCESS;
    }


    public void removeClient(ClientProcessor clientProcessor)
    {
        synchronized (clientsSynchronisation)
        {
            clients.remove(clientProcessor);
        }
    }
}

