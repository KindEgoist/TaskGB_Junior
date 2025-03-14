package ru.gb.junior.Task5.Client;

import java.io.*;
import java.net.*;

public class Client implements ChatClient {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final ClientGUI gui;

    public Client(String serverAddress, int port, ClientGUI gui) throws IOException {
        this.socket = new Socket(serverAddress, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.gui = gui;

        new Thread(this::listenForMessages).start();
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                gui.appendMessage(message);
            }
        } catch (IOException e) {
            gui.appendMessage("Ошибка соединения с сервером.");
        }
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void disconnect() {
        try {
            sendMessage("exit");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
