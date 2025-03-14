package ru.gb.junior.Task5.Client;

public interface ChatClient {
    void sendMessage(String message);
    void disconnect();
}