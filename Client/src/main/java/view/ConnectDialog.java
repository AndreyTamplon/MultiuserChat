package view;

import controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ConnectDialog extends JDialog
{

    private final JTextField hostInputField;
    private final JTextField portInputField;

    public ConnectDialog(Frame parent, Controller controller)
    {
        super(parent, "CONNECT", true);
        JPanel connectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel hostLabel = new JLabel("Host: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        connectionPanel.add(hostLabel, gridBagConstraints);

        hostInputField = new JTextField(20);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        connectionPanel.add(hostInputField, gridBagConstraints);

        JLabel portLabel = new JLabel("Port: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        connectionPanel.add(portLabel, gridBagConstraints);

        portInputField = new JTextField(20);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        connectionPanel.add(portInputField, gridBagConstraints);
        connectionPanel.setBorder(new LineBorder(Color.GRAY));

        JButton connectButton = new JButton("Connect");

        connectButton.addActionListener(e -> {
            controller.processCommand("connect", getHost(), getPort());
            dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(connectButton);
        buttonsPanel.add(cancelButton);

        getContentPane().add(connectionPanel, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getHost()
    {
        return hostInputField.getText().trim();
    }

    public String getPort()
    {
        return portInputField.getText().trim();
    }
}
