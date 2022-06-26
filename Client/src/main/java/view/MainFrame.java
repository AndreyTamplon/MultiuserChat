package view;

import client.ClientTerminal;
import common.Publisher;
import common.Subscriber;
import controller.Controller;
import view.buttons.Button;
import view.buttons.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame implements Subscriber
{
    private final Controller controller;
    private final TextArea textArea;

    public MainFrame(ClientTerminal clientTerminal, Controller controller)
    {
        setSize(600, 400);
        setLocationRelativeTo(null);
        this.controller = controller;
        clientTerminal.subscribeOnClosure(this);
        textArea = new TextArea(clientTerminal);
        clientTerminal.subscribe(textArea);
        initUI();
    }

    private void initUI()
    {
        setTitle("Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setResizable(false);
        JScrollPane textPanel = new JScrollPane(textArea);
        textPanel.setBounds(new Rectangle(0, 10, 580, 250));
        getContentPane().add(textPanel);
        JTextField inputField = new InputField();
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(new ConnectButton(controller, new Rectangle(30, 10, 100, 30), this));
        buttons.add(new LoginButton(controller, new Rectangle(140, 10, 100, 30), this));
        buttons.add(new UserListButton(controller, new Rectangle(250, 10, 100, 30)));
        buttons.add(new SendButton(controller, new Rectangle(360, 10, 100, 30), inputField));
        buttons.add(new LogOutButton(controller, new Rectangle(470, 10, 100, 30)));
        ControlPanel controlPanel = new ControlPanel(new Rectangle(0, getHeight() - 90, getWidth(), 50), buttons);
        getContentPane().add(controlPanel);

        Box box = Box.createHorizontalBox();
        box.setBounds(new Rectangle(0, 260, 580, 50));
        box.add(inputField);
        getContentPane().add(box, BorderLayout.SOUTH);
    }

    @Override
    public void update(Publisher model)
    {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
