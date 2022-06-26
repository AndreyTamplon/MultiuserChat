package view.buttons;

import controller.Controller;
import view.LoginDialog;

import javax.swing.*;
import java.awt.*;

public class LoginButton extends Button
{
    public LoginButton(Controller controller, Rectangle bounds, JFrame frame)
    {
        super("LOGIN");
        setBounds(bounds);
        addActionListener(
                e -> {
                    LoginDialog loginDialog = new LoginDialog(frame, controller);
                    loginDialog.setVisible(true);
                });
    }
}
