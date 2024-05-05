// Xinhao Chen 1166113
package io.noahele.whiteboard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatImpl extends UnicastRemoteObject implements Chat {
  private final List<String> usernames = new CopyOnWriteArrayList<>();
  private final List<String> messages = new CopyOnWriteArrayList<>();

  protected ChatImpl() throws RemoteException {}

  @Override
  public void addMessage(String username, String message) throws RemoteException {
    usernames.add(username);
    messages.add(message);
  }

  @Override
  public List<String> getUsernames() throws RemoteException {
    return usernames;
  }

  @Override
  public List<String> getMessages() throws RemoteException {
    return messages;
  }
}
