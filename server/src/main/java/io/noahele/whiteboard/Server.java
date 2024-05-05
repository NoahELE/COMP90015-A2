// Xinhao Chen 1166113
package io.noahele.whiteboard;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Server {
  private final int port;

  public void run() {
    try {
      Registry registry = LocateRegistry.createRegistry(port);
      Board board = new BoardImpl();
      registry.bind(Board.class.getName(), board);
      Chat chat = new ChatImpl();
      registry.bind(Chat.class.getName(), chat);
      UserManager userManager = new UserManagerImpl();
      registry.bind(UserManager.class.getName(), userManager);
      log.info("Registry ready on port {}", port);
    } catch (RemoteException | AlreadyBoundException e) {
      throw new RuntimeException(e);
    }
  }
}
