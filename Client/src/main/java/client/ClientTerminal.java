package client;

import common.Message;
import common.Publisher;
import common.Subscriber;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientTerminal extends Publisher implements Subscriber
{
    MessageListener messageListener;
    MessageSender messageSender;
    MessageStorage messageStorage;
    private boolean isConnected;
    private boolean isLoggedIn;


    public ClientTerminal(MessageStorage messageStorage) throws IOException
    {
        isConnected = false;
        isLoggedIn = false;
        this.messageStorage = messageStorage;
        messageSender = new MessageSender();
        messageListener = new MessageListener(messageStorage);
        messageStorage.subscribe(this);
    }

    public void connect(InetAddress inetAddress, int port) throws IOException
    {
        Socket socket = new Socket(inetAddress, port);
        messageSender.connect(socket);
        messageListener.connect(socket);
        isConnected = true;
        Thread listenerThread = new Thread(messageListener);
        listenerThread.start();
    }

    public void sendMessage(Message message)
    {
        messageSender.sendMessage(message);
    }

    public boolean isConnected()
    {
        return isConnected;
    }

    public boolean isLoggedIn()
    {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        isLoggedIn = loggedIn;
    }

    public MessageStorage getMessageStorage()
    {
        return messageStorage;
    }

    public void subscribeOnClosure(Subscriber subscriber)
    {
        messageListener.subscribe(subscriber);
    }

    @Override
    public void update(Publisher model)
    {
        notifySubscribers();
    }
}
