package io.noahele.whiteboard.shape;

import java.awt.*;

public class Text implements Shape {
    private final Color color;
    private final Point start;
    private final String text;

    public Text(Color color, Point start, String text) {
        this.color = color;
        this.start = start;
        this.text = text;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawString(text, start.x, start.y);
    }

    @Override
    public void update(Point point) {
    }
}
