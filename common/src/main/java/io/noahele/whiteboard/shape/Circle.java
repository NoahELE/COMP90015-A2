// Xinhao Chen 1166113
package io.noahele.whiteboard.shape;

import java.awt.*;

public class Circle implements Shape {
  private final Color color;
  private final Point start;
  private Point end;

  public Circle(Color color, Point start) {
    this.color = color;
    this.start = start;
    this.end = start;
  }

  @Override
  public void draw(Graphics g) {
    int radius = (int) start.distance(end);
    Point topLeft = new Point(start.x - radius, start.y - radius);
    g.setColor(color);
    g.drawOval(topLeft.x, topLeft.y, radius * 2, radius * 2);
  }

  @Override
  public void update(Point point) {
    end = point;
  }
}
