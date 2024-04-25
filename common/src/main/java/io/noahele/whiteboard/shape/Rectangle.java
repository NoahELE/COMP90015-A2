package io.noahele.whiteboard.shape;

import java.awt.*;

public class Rectangle implements Shape {
    private final Color color;
    private final Point start;
    private Point end;

    public Rectangle(Color color, Point start) {
        this.color = color;
        this.start = start;
        this.end = start;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void draw(Graphics g) {
        Point topLeft = new Point(Math.min(start.x, end.x), Math.min(start.y, end.y));
        int width = Math.abs(end.x - start.x);
        int height = Math.abs(end.y - start.y);
        g.setColor(color);
        g.drawRect(topLeft.x, topLeft.y, width, height);
    }

    @Override
    public void update(Point point) {
        end = point;
    }

    @Override
    public boolean contains(Point point) {
        Point topLeft = new Point(Math.min(start.x, end.x), Math.min(start.y, end.y));
        Point bottomRight = new Point(Math.max(start.x, end.x), Math.max(start.y, end.y));
        return topLeft.x <= point.x && point.x <= bottomRight.x
                && topLeft.y <= point.y && point.y <= bottomRight.y
                && (point.x == topLeft.x
                || point.x == bottomRight.x
                || point.y == topLeft.y
                || point.y == bottomRight.y);
    }
}
