// Xinhao Chen 1166113
package io.noahele.whiteboard.gui;

import io.noahele.whiteboard.Board;
import io.noahele.whiteboard.UserManager;
import io.noahele.whiteboard.shape.Shape;
import java.io.*;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.*;

public class FileMenu extends JMenu {
  private final Board board;
  private final UserManager userManager;
  private final String username;
  private File currentFile;

  public FileMenu(Board board, UserManager userManager, String username) {
    super("File");
    this.board = board;
    this.userManager = userManager;
    this.username = username;

    JMenuItem newMenuItem = newMenuItem();
    add(newMenuItem);

    JMenuItem openMenuItem = openMenuItem();
    add(openMenuItem);

    JMenuItem saveMenuItem = saveMenuItem();
    add(saveMenuItem);

    JMenuItem saveAsMenuItem = saveAsMenuItem();
    add(saveAsMenuItem);

    JMenuItem closeMenuItem = closeMenuItem();
    add(closeMenuItem);
  }

  private JMenuItem newMenuItem() {
    JMenuItem newMenuItem = new JMenuItem("New");
    newMenuItem.addActionListener(
        e -> {
          try {
            board.clearShapes();
            currentFile = null;
          } catch (RemoteException ex) {
            throw new RuntimeException(ex);
          }
        });
    return newMenuItem;
  }

  @SuppressWarnings("unchecked")
  private JMenuItem openMenuItem() {
    JMenuItem openMenuItem = new JMenuItem("Open");
    openMenuItem.addActionListener(
        e -> {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
          int r = fileChooser.showDialog(this, "Open");
          if (r == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
              List<Shape> newShapes = (List<Shape>) ois.readObject();
              board.updateBoard(newShapes);
              currentFile = file;
            } catch (Exception ex) {
              JOptionPane.showMessageDialog(this, "Failed to load board from file");
            }
          }
        });
    return openMenuItem;
  }

  private JMenuItem saveMenuItem() {
    JMenuItem saveMenuItem = new JMenuItem("Save");
    saveMenuItem.addActionListener(
        e -> {
          if (currentFile == null) {
            JOptionPane.showMessageDialog(this, "No current file");
            return;
          }
          try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentFile))) {
            oos.writeObject(board.getShapes());
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save board to file");
          }
        });
    return saveMenuItem;
  }

  private JMenuItem saveAsMenuItem() {
    JMenuItem saveMenuItem = new JMenuItem("Save As");
    saveMenuItem.addActionListener(
        e -> {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
          int r = fileChooser.showDialog(this, "Save");
          if (r == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
              oos.writeObject(board.getShapes());
              currentFile = file;
            } catch (IOException ex) {
              JOptionPane.showMessageDialog(this, "Failed to save board to file");
            }
          }
        });
    return saveMenuItem;
  }

  private JMenuItem closeMenuItem() {
    JMenuItem closeMenuItem = new JMenuItem("Close");
    closeMenuItem.addActionListener(
        e -> {
          try {
            board.clearShapes();
            for (String other : userManager.getUsers()) {
              if (!username.equals(other)) {
                userManager.removeUser(other);
              }
            }
          } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Failed to close the board");
          }
        });
    return closeMenuItem;
  }
}
