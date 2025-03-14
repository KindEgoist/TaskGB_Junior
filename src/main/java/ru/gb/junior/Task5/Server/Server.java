package ru.gb.junior.Task5.Server;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements ChatServer, Runnable {
    private final int port;
    private final JTextArea logArea;
    private ServerSocket serverSocket;
    private boolean running = false;
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public Server(int port, JTextArea logArea) {
        this.port = port;
        this.logArea = logArea;
    }

    @Override
    public void run() {
        start();
    }

    @Override
    public void start() {
        running = true;
        try {
            serverSocket = new ServerSocket(port);
            log("Сервер запущен на порту " + port);

            while (running) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            log("Ошибка сервера: " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        running = false;
        try {
            synchronized (clients) {
                for (ClientHandler client : clients) {
                    client.disconnect();
                }
                clients.clear();
            }

            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }

            log("Сервер остановлен.");
        } catch (IOException e) {
            log("Ошибка при остановке сервера: " + e.getMessage());
        }
    }

    @Override
    public void broadcast(String message, String senderName, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client == sender) {
                    client.sendMessage("ВЫ: " + message);
                } else {
                    client.sendMessage(senderName + ": " + message);
                }
            }
        }
        log(senderName + ": " + message); // Логируем каждое сообщение в сервере!
    }

    @Override
    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }
}
