// Xinhao Chen 1166113
package io.noahele.whiteboard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Chat extends Remote {
  /**
   * Add a message.
   *
   * @param username the username message is from.
   * @param message the message text.
   */
  void addMessage(String username, String message) throws RemoteException;

  /**
   * Get the list of messages' usernames.
   *
   * @return the list of current chat messages' usernames.
   */
  List<String> getUsernames() throws RemoteException;

  /**
   * Get the list of messages' text.
   *
   * @return the list of messages' text.
   */
  List<String> getMessages() throws RemoteException;
}
