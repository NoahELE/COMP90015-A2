package io.noahele.whiteboard;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import io.noahele.whiteboard.gui.WhiteBoardGui;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class ManagerClient {
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
            new Thread(() -> new Server(port).run()).start();

            new Thread(() -> {
                try (ServerSocket serverSocket = new ServerSocket(port + 1);
                     ExecutorService executor = Executors.newCachedThreadPool()) {
                    while (true) {
                        Socket socket = serverSocket.accept();
                        executor.submit(() -> {
                            try (Connection connection = new Connection(socket)) {
                                String joinUsername = connection.receiveJoinRequest();
                                System.out.println("Join user: " + joinUsername);
                                int res = JOptionPane.showConfirmDialog(null, "User want to join: " + joinUsername);
                                connection.sendJoinResponse(res == JOptionPane.OK_OPTION);
                            }
                        });
                    }
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }).start();

            Thread.sleep(1000);

            Registry registry = LocateRegistry.getRegistry(host, port);
            Board board = (Board) registry.lookup(Board.class.getName());
            Chat chat = (Chat) registry.lookup(Chat.class.getName());
            UserManager userManager = (UserManager) registry.lookup(UserManager.class.getName());

            WhiteBoardGui whiteBoardGui = new WhiteBoardGui(board, chat, userManager, true, username);
            new Thread(() -> {
                while (true) {
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
            JOptionPane.showMessageDialog(null,
                    "Failed to connect to server: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
