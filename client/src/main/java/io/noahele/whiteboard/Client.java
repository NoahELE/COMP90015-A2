package io.noahele.whiteboard;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import io.noahele.whiteboard.gui.WhiteBoardGui;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

@RequiredArgsConstructor
public class Client {
    private static final int UPDATE_INTERVAL = 1000;

    @SuppressWarnings({"DuplicatedCode", "BusyWait"})
    public static void main(String[] args) {
        // set look and feel
        FlatMacLightLaf.setup();

        // parse command line arguments
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 9090;
        String username = args.length > 2 ? args[2] : UUID.randomUUID().toString().substring(0, 5);

        try {
            System.out.println("Waiting for manager to approve...");
            try (Connection connection = new Connection(new Socket(host, port + 1))) {
                connection.sendJoinRequest(username);
                boolean res = connection.receiveJoinResponse();
                if (!res) {
                    System.out.println("You are rejected!");
                    System.exit(0);
                } else {
                    System.out.println("You are accepted!");
                }
            }

            Registry registry = LocateRegistry.getRegistry(host, port);
            Board board = (Board) registry.lookup(Board.class.getName());
            Chat chat = (Chat) registry.lookup(Chat.class.getName());
            UserManager userManager = (UserManager) registry.lookup(UserManager.class.getName());

            WhiteBoardGui whiteBoardGui = new WhiteBoardGui(board, chat, userManager, false, username);
            new Thread(() -> {
                while (true) {
                    try {
                        if (!userManager.getUsers().contains(username)) {
                            JOptionPane.showMessageDialog(null, "You are kicked out");
                            System.exit(0);
                        }
                    } catch (RemoteException e) {
                        JOptionPane.showMessageDialog(null, "Failed to connect to server: " + e.getMessage());
                    }
                    whiteBoardGui.updateBoard();
                    whiteBoardGui.updateChat();
                    whiteBoardGui.updateUserManager();
                    try {
                        Thread.sleep(UPDATE_INTERVAL);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
            userManager.addUser(username);
            whiteBoardGui.setVisible(true);
        } catch (IOException | NotBoundException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to server: " + e.getMessage());
        }
    }
}
