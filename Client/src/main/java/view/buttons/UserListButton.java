package view.buttons;

import controller.Controller;

import java.awt.*;

public class UserListButton extends Button
{
    public UserListButton(Controller controller, Rectangle bounds)
    {
        super("USER LIST");
        setBounds(bounds);
        addActionListener(e ->
                controller.processCommand("list"));
    }
}
