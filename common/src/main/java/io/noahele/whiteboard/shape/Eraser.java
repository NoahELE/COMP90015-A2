package io.noahele.whiteboard.shape;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Eraser implements Shape {
    private final int stroke;
    private final List<Point> points = new ArrayList<>();

    public Eraser(int stroke) {
        this.stroke = stroke;
    }

    @SuppressWarnings("DuplicatedCode")
    public void draw(Graphics g) {
        g.setColor(new JPanel().getBackground());
        Graphics2D g2d = (Graphics2D) g;
        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(stroke));
        for (int i = 0; i < points.size() - 1; i++) {
            Point start = points.get(i);
            Point end = points.get(i + 1);
            g.drawLine(start.x, start.y, end.x, end.y);
        }
        g2d.setStroke(originalStroke);
    }

    @Override
    public void update(Point point) {
        points.add(point);
    }
}
