package io.noahele.whiteboard.shape;

import java.awt.*;

public class Oval implements Shape {
    private static final int THRESHOLD = 1;

    private final Color color;
    private final Point start;
    private Point end;

    public Oval(Color color, Point start) {
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
        g.drawOval(topLeft.x, topLeft.y, width, height);
    }

    @Override
    public void update(Point point) {
        end = point;
    }

    @Override
    public boolean contains(Point point) {
        int width = Math.abs(end.x - start.x);
        int height = Math.abs(end.y - start.y);
        return Math.pow(point.x - start.x, 2) / Math.pow(width, 2)
                + Math.pow(point.y - start.y, 2) / Math.pow(height, 2) < THRESHOLD;
    }
}
