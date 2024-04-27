package io.noahele.whiteboard.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Line implements Shape {
    private Color color;
    private Point start;
    private Point end;

    public Line(Color color, Point start) {
        this.color = color;
        this.start = start;
        this.end = start;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    @Override
    public void update(Point point) {
        end = point;
    }
}
