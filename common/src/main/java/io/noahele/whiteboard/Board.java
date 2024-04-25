package io.noahele.whiteboard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import io.noahele.whiteboard.shape.Shape;

public interface Board extends Remote {
    /**
     * Add a shape to the board.
     *
     * @param shape the shape to add.
     */
    void addShape(Shape shape) throws RemoteException;

    /**
     * Get the shapes.
     */
    List<Shape> getShapes() throws RemoteException;
}
