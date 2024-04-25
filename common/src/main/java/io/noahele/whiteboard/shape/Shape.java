package io.noahele.whiteboard.shape;

import java.awt.*;
import java.io.Serializable;

public interface Shape extends Serializable {
    void draw(Graphics g);

    void update(Point point);
}
