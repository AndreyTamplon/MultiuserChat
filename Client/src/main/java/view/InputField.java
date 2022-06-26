package view;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class InputField extends JTextField
{
    private static final String ENTERING_TEXT = "Enter your message...";

    public InputField()
    {
        setText(ENTERING_TEXT);
        addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                if (getText().trim().equals(ENTERING_TEXT))
                {
                    setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if (getText().trim().length() == 0)
                {
                    setText(ENTERING_TEXT);
                }
            }
        });
    }
}
