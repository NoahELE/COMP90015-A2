package io.noahele.whiteboard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface UserManager extends Remote {
    void addUser(String username) throws RemoteException;

    void removeUser(String username) throws RemoteException;

    Set<String> getUsers() throws RemoteException;
}
