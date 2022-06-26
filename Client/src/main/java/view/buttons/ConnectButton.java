package view.buttons;

import controller.Controller;
import view.ConnectDialog;

import javax.swing.*;
import java.awt.*;

public class ConnectButton extends Button
{
    public ConnectButton(Controller controller, Rectangle bounds, JFrame frame)
    {
        super("CONNECT");
        setBounds(bounds);
        addActionListener(
                e -> {
                    ConnectDialog connectDialog = new ConnectDialog(frame, controller);
                    connectDialog.setVisible(true);
                });
    }
}
