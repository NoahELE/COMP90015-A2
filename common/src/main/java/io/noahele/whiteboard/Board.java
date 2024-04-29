package io.noahele.whiteboard;

import io.noahele.whiteboard.shape.Shape;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

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

    /**
     * Clear all shapes.
     */
    void clearShapes() throws RemoteException;

    /**
     * Update the board.
     *
     * @param shapes the new shapes.
     */
    void updateBoard(List<Shape> shapes) throws RemoteException;
}
