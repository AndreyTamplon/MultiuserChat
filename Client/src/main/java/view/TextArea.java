package view;

import client.ClientTerminal;
import common.Message;
import common.Publisher;
import common.Subscriber;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextArea extends JTextPane implements Subscriber
{
    private static final Logger logger = Logger.getLogger(TextArea.class.getName());
    private final SimpleAttributeSet textMessage;
    private final SimpleAttributeSet errorMessage;
    private final SimpleAttributeSet infoMessage;
    private final SimpleAttributeSet successMessage;
    ClientTerminal clientTerminal;
    StyledDocument styledDocument;

    public TextArea(ClientTerminal clientTerminal)
    {
        setText("""
                In order to start communicating, you need to connect to the server and log in.\s
                To do this, click CONNECT button, and then LOGIN button\s
                Don't know the address and port? Try "localhost" and "205"\s
                """);
        this.clientTerminal = clientTerminal;
        setEditable(false);
        styledDocument = getStyledDocument();

        textMessage = new SimpleAttributeSet();
        StyleConstants.setAlignment(textMessage, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(textMessage, Color.BLACK);

        infoMessage = new SimpleAttributeSet();
        StyleConstants.setAlignment(infoMessage, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(infoMessage, Color.GRAY);

        errorMessage = new SimpleAttributeSet();
        StyleConstants.setAlignment(errorMessage, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(errorMessage, Color.RED);

        successMessage = new SimpleAttributeSet();
        StyleConstants.setAlignment(successMessage, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(successMessage, Color.GREEN);
    }

    private void writeToChat(String messageText, SimpleAttributeSet simpleAttributeSet)
    {
        styledDocument.setParagraphAttributes(styledDocument.getLength(), 1, simpleAttributeSet, false);
        try
        {
            styledDocument.insertString(styledDocument.getLength(), messageText, simpleAttributeSet);
        }
        catch (BadLocationException e)
        {
            logger.log(Level.WARNING, "Unable to write message to chat", e);
        }
    }


    @Override
    public void update(Publisher model)
    {
        Message message = clientTerminal.getMessageStorage().getLastMessage();
        if (Objects.equals(message.getMessageType(), "error"))
        {
            writeToChat("\nError: " + message.getMessageText(), errorMessage);
        }
        else if (Objects.equals(message.getMessageType(), "list"))
        {
            writeToChat("\nUser list:\n" + message.getMessageText(), infoMessage);
        }
        else if (Objects.equals(message.getMessageType(), "message"))
        {
            writeToChat("\n" + message.getMessageText(), textMessage);
        }
        else if (Objects.equals(message.getMessageType(), "success") && message.getMessageText().length() > 0)
        {
            writeToChat("\n" + message.getMessageText(), successMessage);
        }
        if (!clientTerminal.getMessageStorage().isMessagesValid())
        {
            try
            {
                styledDocument.remove(0, styledDocument.getLength());
                clientTerminal.getMessageStorage().setMessagesValid(true);
            }
            catch (BadLocationException e)
            {
                logger.log(Level.WARNING, "Unable to clear messages from display", e);
            }
        }
    }
}

