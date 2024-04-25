package io.noahele.whiteboard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Chat extends Remote {
    void addMessage(String username, String message) throws RemoteException;
    List<String> getUsernames() throws RemoteException;
    List<String> getMessages() throws RemoteException;
}
