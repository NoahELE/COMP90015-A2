package io.noahele.whiteboard.gui;

import io.noahele.whiteboard.*;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

@Slf4j
public class WhiteBoardGui extends JFrame {
    private final DrawingPanel drawingPanel;
    private final ChatPanel chatPanel;
    private final UserPanel userPanel;

    /**
     * Construct a new WhiteBoardGui.
     *
     * @param board the remote board.
     */
    public WhiteBoardGui(
            Board board,
            Chat chat,
            UserManager userManager,
            Connection connection,
            boolean manager,
            String username) {

        if (manager) {
            setTitle("WhiteBoard - Manager");
        } else {
            setTitle("WhiteBoard - Client");
        }
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingPanel = new DrawingPanel(board, connection);
        ControlPanel controlPanel = new ControlPanel(drawingPanel);
        add(drawingPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);

        chatPanel = new ChatPanel(chat, connection, username);
        add(chatPanel, BorderLayout.EAST);

        userPanel = new UserPanel(userManager, connection, username, manager);
        add(userPanel, BorderLayout.WEST);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    userManager.removeUser(username);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                connection.close();
            }
        });

        updateBoard();
        updateChat();
        updateUserManager();
    }

    /**
     * Update the GUI based on current board.
     */
    public void updateBoard() {
        drawingPanel.repaint();
    }

    /**
     * Update the GUI based on current chat.
     */
    public void updateChat() {
        chatPanel.updateChat();
    }

    /**
     * Update the GUI base on current user manager.
     */
    public void updateUserManager() {
        userPanel.updateUsers();
    }
}
