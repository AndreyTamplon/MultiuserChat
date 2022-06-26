package view;

import view.buttons.Button;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ControlPanel extends JPanel
{
    public ControlPanel(Rectangle bounds, List<Button> buttons)
    {
        setBackground(Color.WHITE);
        setBounds(bounds);
        setLayout(null);
        placeButtons(buttons);
    }

    private void placeButtons(List<Button> buttons)
    {
        for (JButton button : buttons)
        {
            add(button);
        }
    }
}
