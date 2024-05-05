// Xinhao Chen 1166113
package io.noahele.whiteboard.gui;

import io.noahele.whiteboard.Board;
import io.noahele.whiteboard.Chat;
import io.noahele.whiteboard.UserManager;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import javax.swing.*;

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
      Board board, Chat chat, UserManager userManager, boolean manager, String username) {

    if (manager) {
      setTitle("WhiteBoard - Manager");
    } else {
      setTitle("WhiteBoard - Client");
    }
    setSize(1200, 800);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    drawingPanel = new DrawingPanel(board);
    add(drawingPanel, BorderLayout.CENTER);

    ControlPanel controlPanel = new ControlPanel(drawingPanel);
    add(controlPanel, BorderLayout.SOUTH);

    if (manager) {
      JMenuBar menuBar = new JMenuBar();
      FileMenu fileMenu = new FileMenu(board, userManager, username);
      menuBar.add(fileMenu);
      setJMenuBar(menuBar);
    }

    chatPanel = new ChatPanel(chat, username);
    add(chatPanel, BorderLayout.EAST);

    userPanel = new UserPanel(userManager, username, manager);
    add(userPanel, BorderLayout.WEST);

    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            try {
              if (manager) {
                // remove all users if manager quits
                for (String u : userManager.getUsers()) {
                  userManager.removeUser(u);
                }
              } else {
                userManager.removeUser(username);
              }
            } catch (RemoteException ex) {
              throw new RuntimeException(ex);
            }
          }
        });

    updateBoard();
    updateChat();
    updateUserManager();
  }

  /** Update the GUI based on current board. */
  public void updateBoard() {
    drawingPanel.repaint();
  }

  /** Update the GUI based on current chat. */
  public void updateChat() {
    chatPanel.updateChat();
  }

  /** Update the GUI base on current user manager. */
  public void updateUserManager() {
    userPanel.updateUsers();
  }
}
