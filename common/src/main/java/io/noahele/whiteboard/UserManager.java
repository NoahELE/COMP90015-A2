// Xinhao Chen 1166113
package io.noahele.whiteboard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface UserManager extends Remote {
  /**
   * Add a user.
   *
   * @param username the username.
   * @return whether the username already exists.
   */
  boolean addUser(String username) throws RemoteException;

  /**
   * Remove a user.
   *
   * @param username the username.
   */
  void removeUser(String username) throws RemoteException;

  /**
   * Get the set of users.
   *
   * @return the set of users.
   */
  Set<String> getUsers() throws RemoteException;
}
