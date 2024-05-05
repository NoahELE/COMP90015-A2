// Xinhao Chen 1166113
package io.noahele.whiteboard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UserManagerImpl extends UnicastRemoteObject implements UserManager {
  private final Set<String> users = ConcurrentHashMap.newKeySet();

  protected UserManagerImpl() throws RemoteException {}

  @Override
  public boolean addUser(String username) throws RemoteException {
    return users.add(username);
  }

  @Override
  public void removeUser(String username) throws RemoteException {
    users.remove(username);
  }

  @Override
  public Set<String> getUsers() throws RemoteException {
    return users;
  }
}
