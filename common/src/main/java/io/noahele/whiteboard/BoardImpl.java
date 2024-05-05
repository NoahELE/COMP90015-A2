// Xinhao Chen 1166113
package io.noahele.whiteboard;

import io.noahele.whiteboard.shape.Shape;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BoardImpl extends UnicastRemoteObject implements Board {
  private final List<Shape> shapes = new CopyOnWriteArrayList<>();

  protected BoardImpl() throws RemoteException {
    super();
  }

  @Override
  public void addShape(Shape shape) {
    shapes.add(shape);
  }

  @Override
  public List<Shape> getShapes() {
    return shapes;
  }

  @Override
  public void clearShapes() throws RemoteException {
    shapes.clear();
  }

  @Override
  public void updateBoard(List<Shape> newShapes) throws RemoteException {
    shapes.clear();
    shapes.addAll(newShapes);
  }
}
