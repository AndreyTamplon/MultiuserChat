package view.buttons;

import controller.Controller;

import java.awt.*;

public class LogOutButton extends Button
{
    public LogOutButton(Controller controller, Rectangle bounds)
    {
        super("LOGOUT");
        setBounds(bounds);
        addActionListener(e ->
                controller.processCommand("logout"));
    }
}
