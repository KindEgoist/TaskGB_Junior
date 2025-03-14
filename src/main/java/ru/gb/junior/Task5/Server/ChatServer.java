package ru.gb.junior.Task5.Server;

public interface ChatServer {
    void start();
    void stop();
    void broadcast(String message, String sender, ClientHandler senderClient);
    void removeClient(ClientHandler clientHandler);
}
