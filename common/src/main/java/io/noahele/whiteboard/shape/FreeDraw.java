package io.noahele.whiteboard.shape;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class FreeDraw implements Shape {
    private Color color;
    private List<Point> points = new ArrayList<>();

    public FreeDraw(Color color) {
        this.color = color;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        for (int i = 0; i < points.size() - 1; i++) {
            Point start = points.get(i);
            Point end = points.get(i + 1);
            g.drawLine(start.x, start.y, end.x, end.y);
        }
    }

    @Override
    public void update(Point point) {
        points.add(point);
    }
}
