package io.noahele.whiteboard.shape;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

public class Text implements Shape {
    private final Color color;
    private final Point start;
    private final String text;
    private int textWidth;
    private int textHeight;

    public Text(Color color, Point start, String text) {
        this.color = color;
        this.start = start;
        this.text = text;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawString(text, start.x, start.y);
        textWidth = g.getFontMetrics().stringWidth(text);
        textHeight = g.getFontMetrics().getHeight();
    }

    @Override
    public void update(Point point) {
    }

    @Override
    public boolean contains(Point point) {
        Point end = new Point(start.x + textWidth, start.y + textHeight);
        return start.x <= point.x && point.x <= end.x && start.y <= point.y && point.y <= end.y;
    }
}
