package ru.gb.junior.Task5.Server;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Server server;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            clientName = in.readLine();
            if (clientName == null || clientName.isEmpty()) {
                disconnect();
                return;
            }

            server.broadcast(clientName + " подключился к чату.", "Сервер", null);
            server.log(clientName + " подключился.");

            String message;
            while ((message = in.readLine()) != null) {
                server.broadcast(message, clientName, this);
            }
        } catch (IOException e) {
            if (clientName != null) {
                server.broadcast(clientName + " отключился.", "Сервер", null);
                server.log(clientName + " отключился.");
            }
        } finally {
            disconnect();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public void disconnect() {
        try {
            server.removeClient(this);
            socket.close();
        } catch (IOException ignored) {}
    }
}
