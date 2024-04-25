package io.noahele.whiteboard.gui.shape;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.noahele.whiteboard.shape.Circle;
import io.noahele.whiteboard.shape.FreeDraw;
import io.noahele.whiteboard.shape.Line;
import io.noahele.whiteboard.shape.Oval;
import io.noahele.whiteboard.shape.Rectangle;
import io.noahele.whiteboard.shape.Shape;
import io.noahele.whiteboard.shape.Text;

public class ShapeJsonTest {
    private static final ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());

    @Test
    public void arbitraryShapeTest() throws Exception {
        Shape shape = new Circle(Color.RED, new Point(1, 2));
        byte[] bytes = objectMapper.writeValueAsBytes(shape);
        Shape deserialized = objectMapper.readValue(bytes, Shape.class);
        assertEquals(shape, deserialized);
    }

    @Test
    public void testFreeDraw() throws Exception {
        FreeDraw freeDraw = new FreeDraw(Color.BLACK);
        freeDraw.setPoints(List.of(new Point(3, 4), new Point(1, 1)));
        byte[] bytes = objectMapper.writeValueAsBytes(freeDraw);
        FreeDraw deserialized = objectMapper.readValue(bytes, FreeDraw.class);
        assertEquals(freeDraw, deserialized);
    }

    @Test
    public void testCircle() throws Exception {
        Circle circle = new Circle(Color.BLACK, new Point(1, 2));
        circle.setEnd(new Point(3, 4));
        byte[] bytes = objectMapper.writeValueAsBytes(circle);
        Circle deserialized = objectMapper.readValue(bytes, Circle.class);
        assertEquals(circle, deserialized);
    }

    @Test
    public void testLine() throws Exception {
        Line line = new Line(Color.BLACK, new Point());
        byte[] bytes = objectMapper.writeValueAsBytes(line);
        Line deserialized = objectMapper.readValue(bytes, Line.class);
        assertEquals(line, deserialized);
    }

    @Test
    public void testOval() throws Exception {
        Oval oval = new Oval(Color.BLACK, new Point());
        byte[] bytes = objectMapper.writeValueAsBytes(oval);
        Oval deserialized = objectMapper.readValue(bytes, Oval.class);
        assertEquals(oval, deserialized);
    }

    @Test
    public void testRectangle() throws Exception {
        Rectangle rectangle = new Rectangle(Color.BLACK, new Point());
        byte[] bytes = objectMapper.writeValueAsBytes(rectangle);
        Rectangle deserialized = objectMapper.readValue(bytes, Rectangle.class);
        assertEquals(rectangle, deserialized);
    }

    @Test
    public void testText() throws Exception {
        Text text = new Text(Color.BLACK, new Point(), "");
        byte[] bytes = objectMapper.writeValueAsBytes(text);
        Text deserialized = objectMapper.readValue(bytes, Text.class);
        assertEquals(text, deserialized);
    }
}
