package ru.gb.junior.Task5.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ClientGUI {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField, nameField;
    private JButton connectButton, disconnectButton, sendButton;
    private Client client;
    private String userName;

    public ClientGUI() {
        frame = new JFrame("Чат");
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        nameField = new JTextField(10);
        connectButton = new JButton("ПОДКЛЮЧИТЬСЯ");
        disconnectButton = new JButton("ОТКЛЮЧИТЬСЯ");
        disconnectButton.setEnabled(false);

        topPanel.add(new JLabel("Имя:"));
        topPanel.add(nameField);
        topPanel.add(connectButton);
        topPanel.add(disconnectButton);
        frame.add(topPanel, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setEnabled(false);
        sendButton = new JButton("ОТПРАВИТЬ");
        sendButton.setEnabled(false);
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        connectButton.addActionListener(e -> connect());
        disconnectButton.addActionListener(e -> disconnect());
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void connect() {
        userName = nameField.getText().trim();
        if (userName.isEmpty()) return;

        try {
            client = new Client("localhost", 12345, this);
            client.sendMessage(userName);
            chatArea.append("Вы подключились к чату.\n");

            connectButton.setEnabled(false);
            disconnectButton.setEnabled(true);
            inputField.setEnabled(true);
            sendButton.setEnabled(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Ошибка подключения!");
        }
    }

    private void sendMessage() {
        String message = inputField.getText();
        inputField.setText("");
        client.sendMessage(message);
    }

    private void disconnect() {
        client.disconnect();
        chatArea.append("Вы отключились.\n");
        connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
        inputField.setEnabled(false);
        sendButton.setEnabled(false);
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }
}
