package common;

import java.io.Serial;
import java.io.Serializable;

public class Message implements Serializable
{
    @Serial
    private static final long serialVersionUID = 2260206989408057745L;
    private final String messageType;
    private final String messageText;

    public Message(String messageType, String messageText)
    {
        this.messageType = messageType;
        this.messageText = messageText;
    }

    public String getMessageType()
    {
        return messageType;
    }

    public String getMessageText()
    {
        return messageText;
    }
}
