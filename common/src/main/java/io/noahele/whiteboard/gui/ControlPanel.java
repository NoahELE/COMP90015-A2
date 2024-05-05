// Xinhao Chen 1166113
package io.noahele.whiteboard.gui;

import java.awt.Color;
import javax.swing.*;

public class ControlPanel extends JPanel {
  /**
   * Construct a new ControlPanel.
   *
   * @param drawingPanel the drawing panel to control.
   */
  @SuppressWarnings("ExtractMethodRecommender")
  public ControlPanel(DrawingPanel drawingPanel) {
    JButton noneModeButton = new JButton("None");
    noneModeButton.addActionListener(e -> drawingPanel.noneMode());
    add(noneModeButton);

    JButton circleModeButton = new JButton("Circle");
    circleModeButton.addActionListener(e -> drawingPanel.circleMode());
    add(circleModeButton);

    JButton lineModeButton = new JButton("Line");
    lineModeButton.addActionListener(e -> drawingPanel.lineMode());
    add(lineModeButton);

    JButton ovalModeButton = new JButton("Oval");
    ovalModeButton.addActionListener(e -> drawingPanel.ovalMode());
    add(ovalModeButton);

    JButton rectangleModeButton = new JButton("Rectangle");
    rectangleModeButton.addActionListener(e -> drawingPanel.rectangleMode());
    add(rectangleModeButton);

    JButton textModeButton = new JButton("Text");
    textModeButton.addActionListener(e -> drawingPanel.textMode());
    add(textModeButton);

    JButton freeModeButton = new JButton("Free Draw");
    freeModeButton.addActionListener(e -> drawingPanel.freeDrawMode());
    add(freeModeButton);

    JButton eraseModeButton = new JButton("Erase");
    eraseModeButton.addActionListener(e -> drawingPanel.eraseMode());
    add(eraseModeButton);

    JButton eraserSizeButton = new JButton("Eraser Size: " + drawingPanel.getEraserSize());
    eraserSizeButton.addActionListener(
        e -> {
          Object[] options = {16, 32, 64};
          int choice =
              JOptionPane.showOptionDialog(
                  null,
                  "Choose the eraser size: ",
                  "Eraser Size",
                  JOptionPane.DEFAULT_OPTION,
                  JOptionPane.PLAIN_MESSAGE,
                  null,
                  options,
                  options[0]);
          if (choice == -1) {
            return;
          }
          drawingPanel.setEraserSize((int) options[choice]);
          eraserSizeButton.setText("Eraser Size: " + options[choice]);
        });
    add(eraserSizeButton);

    JButton colorButton = new JButton("Color");
    JPanel colorHint = new JPanel();
    colorHint.setBackground(drawingPanel.getColor());
    colorButton.addActionListener(
        e -> {
          Color color = JColorChooser.showDialog(this, "Choose Color", Color.BLACK);
          if (color == null) {
            return;
          }
          drawingPanel.setColor(color);
          colorHint.setBackground(color);
        });
    add(colorButton);
    add(colorHint);
  }
}
