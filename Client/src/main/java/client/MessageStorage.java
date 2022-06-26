package client;

import common.Message;
import common.Publisher;

import java.util.ArrayList;
import java.util.List;

public class MessageStorage extends Publisher
{
    List<Message> storage;
    private boolean isMessagesValid;

    public MessageStorage()
    {
        storage = new ArrayList<>();
        isMessagesValid = true;
    }

    public boolean isMessagesValid()
    {
        return isMessagesValid;
    }

    public void setMessagesValid(boolean messagesValid)
    {
        isMessagesValid = messagesValid;
    }

    public void addMessage(Message message)
    {
        storage.add(message);
        notifySubscribers();
    }

    public Message getLastMessage()
    {
        return storage.get(storage.size() - 1);
    }

    public List<Message> getLastNMessages(int n)
    {
        if (storage.size() < n)
        {
            return storage;
        }
        return storage.subList(storage.size() - n, storage.size());
    }
}
