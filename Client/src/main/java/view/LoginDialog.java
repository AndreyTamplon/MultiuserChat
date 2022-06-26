package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog
{
    private final JTextField nameInputField;

    public LoginDialog(Frame parent, Controller controller)
    {
        super(parent, "LOGIN", true);
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        loginPanel.add(nameLabel, gridBagConstraints);

        nameInputField = new JTextField(20);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        loginPanel.add(nameInputField, gridBagConstraints);

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            controller.processCommand("login", getUserName());
            dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(loginButton);
        buttonsPanel.add(cancelButton);

        getContentPane().add(loginPanel, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUserName()
    {
        return nameInputField.getText().trim();
    }
}
