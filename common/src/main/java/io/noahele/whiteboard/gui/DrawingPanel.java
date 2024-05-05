// Xinhao Chen 1166113
package io.noahele.whiteboard.gui;

import io.noahele.whiteboard.Board;
import io.noahele.whiteboard.shape.*;
import io.noahele.whiteboard.shape.Rectangle;
import io.noahele.whiteboard.shape.Shape;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.swing.*;
import lombok.Getter;
import lombok.Setter;

public class DrawingPanel extends JPanel {
  private final Board board;
  @Getter @Setter private Color color = Color.BLACK;
  @Getter @Setter private int eraserSize = 16;
  private Shape currentShape;

  public DrawingPanel(Board board) {
    this.board = board;
    setDoubleBuffered(true);
  }

  /** Set drawing mode to none. */
  public void noneMode() {
    removeAllMouseListeners();
  }

  /** Set drawing mode to circle. */
  public void circleMode() {
    removeAllMouseListeners();
    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            currentShape = new Circle(color, e.getPoint());
            repaint();
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            currentShape.update(e.getPoint());
            try {
              board.addShape(currentShape);
            } catch (RemoteException ex) {
              throw new RuntimeException(ex);
            }
            currentShape = null;
            repaint();
          }
        });
    addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
            currentShape.update(e.getPoint());
            repaint();
          }
        });
  }

  /** Set drawing mode to line. */
  public void lineMode() {
    removeAllMouseListeners();
    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            currentShape = new Line(color, e.getPoint());
            repaint();
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            currentShape.update(e.getPoint());
            try {
              board.addShape(currentShape);
            } catch (RemoteException ex) {
              throw new RuntimeException(ex);
            }
            currentShape = null;
            repaint();
          }
        });
    addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
            currentShape.update(e.getPoint());
            repaint();
          }
        });
  }

  /** Set drawing mode to oval. */
  public void ovalMode() {
    removeAllMouseListeners();
    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            currentShape = new Oval(color, e.getPoint());
            repaint();
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            currentShape.update(e.getPoint());
            try {
              board.addShape(currentShape);
            } catch (RemoteException ex) {
              throw new RuntimeException(ex);
            }
            currentShape = null;
            repaint();
          }
        });
    addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
            currentShape.update(e.getPoint());
            repaint();
          }
        });
  }

  /** Set drawing mode to rectangle. */
  public void rectangleMode() {
    removeAllMouseListeners();
    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            currentShape = new Rectangle(color, e.getPoint());
            repaint();
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            currentShape.update(e.getPoint());
            try {
              board.addShape(currentShape);
            } catch (RemoteException ex) {
              throw new RuntimeException(ex);
            }
            currentShape = null;
            repaint();
          }
        });
    addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
            currentShape.update(e.getPoint());
            repaint();
          }
        });
  }

  /** Set drawing mode to text. */
  public void textMode() {
    removeAllMouseListeners();
    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            String text = JOptionPane.showInputDialog("Enter text");
            if (text == null) {
              return;
            }
            try {
              board.addShape(new Text(color, e.getPoint(), text));
            } catch (RemoteException ex) {
              throw new RuntimeException(ex);
            }
            repaint();
          }
        });
  }

  /** Set drawing mode to free draw. */
  public void freeDrawMode() {
    removeAllMouseListeners();
    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            currentShape = new FreeDraw(color);
            repaint();
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            currentShape.update(e.getPoint());
            try {
              board.addShape(currentShape);
            } catch (RemoteException ex) {
              throw new RuntimeException(ex);
            }
            currentShape = null;
            repaint();
          }
        });
    addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
            currentShape.update(e.getPoint());
            repaint();
          }
        });
  }

  /** Set drawing mode to erase. */
  public void eraseMode() {
    removeAllMouseListeners();
    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            currentShape = new Eraser(eraserSize);
            repaint();
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            currentShape.update(e.getPoint());
            try {
              board.addShape(currentShape);
            } catch (RemoteException ex) {
              throw new RuntimeException(ex);
            }
            currentShape = null;
            repaint();
          }
        });
    addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
            currentShape.update(e.getPoint());
            repaint();
          }
        });
  }

  private void removeAllMouseListeners() {
    for (MouseListener listener : getMouseListeners()) {
      removeMouseListener(listener);
    }
    for (MouseMotionListener listener : getMouseMotionListeners()) {
      removeMouseMotionListener(listener);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setStroke(new BasicStroke(2));
    try {
      board.getShapes().forEach(shape -> shape.draw(g));
    } catch (RemoteException e) {
      throw new RuntimeException(e);
    }
    if (currentShape != null) {
      currentShape.draw(g);
    }
  }
}
