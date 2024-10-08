// Xinhao Chen 1166113
package io.noahele.whiteboard.gui;

import io.noahele.whiteboard.UserManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.*;

public class UserPanel extends JPanel {
  private final DefaultListModel<String> listModel = new DefaultListModel<>();
  private final UserManager userManager;
  private final Set<String> currentUsers = ConcurrentHashMap.newKeySet();

  public UserPanel(UserManager userManager, String username, boolean manager) {
    this.userManager = userManager;
    JList<String> list = new JList<>(listModel);
    if (manager) {
      list.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              if (e.getClickCount() == 2) {
                int index = list.locationToIndex(e.getPoint());
                if (index != -1) {
                  String selectedUser = listModel.get(index);
                  if (selectedUser.equals(username)) {
                    JOptionPane.showMessageDialog(null, "You cannot kick out yourself!");
                    return;
                  }
                  try {
                    userManager.removeUser(selectedUser);
                  } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                  }
                }
              }
            }
          });
    }
    add(list);
    updateUsers();
  }

  public void updateUsers() {
    try {
      if (userManager.getUsers().equals(currentUsers)) {
        return;
      }
    } catch (RemoteException e) {
      throw new RuntimeException(e);
    }
    updateCurrentUsers();
    addAllUsers();
  }

  private void updateCurrentUsers() {
    currentUsers.clear();
    try {
      currentUsers.addAll(userManager.getUsers());
    } catch (RemoteException e) {
      throw new RuntimeException(e);
    }
  }

  private void addAllUsers() {
    listModel.clear();
    for (String username : currentUsers) {
      listModel.addElement(username);
    }
  }
}
