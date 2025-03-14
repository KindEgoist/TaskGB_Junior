package ru.gb.junior.Task5;

import ru.gb.junior.Task5.Client.ClientGUI;
import ru.gb.junior.Task5.Server.ServerGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ServerGUI();
            new ClientGUI();
            new ClientGUI();
        });
    }
}

