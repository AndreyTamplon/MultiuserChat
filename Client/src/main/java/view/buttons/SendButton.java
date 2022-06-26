package view.buttons;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class SendButton extends Button
{
    public SendButton(Controller controller, Rectangle bounds, JTextField inputField)
    {
        super("SEND");
        setBounds(bounds);
        setBackground(Color.GREEN);
        addActionListener(e ->
        {
            String text = inputField.getText();
            if (text != null && text.trim().length() > 0)
            {
                controller.processCommand("message", text);
            }
            inputField.requestFocus();
            inputField.setText("");
        });
    }

}
