package ru.gb.junior.Task5.Server;

import javax.swing.*;
import java.awt.*;

public class ServerGUI {
    private JFrame frame;
    private JTextArea logArea;
    private JButton startButton, stopButton;
    private Server server;
    private Thread serverThread;
    private final int PORT = 12345;

    public ServerGUI() {
        frame = new JFrame("Сервер чата");
        frame.setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        frame.add(new JScrollPane(logArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("СТАРТ");
        stopButton = new JButton("СТОП");
        stopButton.setEnabled(false);

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startServer());
        stopButton.addActionListener(e -> stopServer());

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void startServer() {
        server = new Server(PORT, logArea);
        serverThread = new Thread(server::start);
        serverThread.start();
        logArea.append("Сервер запущен на порту " + PORT + "\n");

        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    private void stopServer() {
        if (server != null) {
            try {
                server.stop();
                logArea.append("Сервер остановлен.\n");
            } catch (Exception e) {
                logArea.append("Ошибка при остановке сервера: " + e.getMessage() + "\n");
            }
        }
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }
}
