package server;

import common.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageStorage
{
    List<Message> storage;

    public MessageStorage()
    {
        storage = new ArrayList<>();
    }

    public void addMessage(Message message)
    {
        storage.add(message);
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
