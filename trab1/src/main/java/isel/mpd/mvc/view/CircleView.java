package isel.mpd.mvc.view;

import isel.mpd.mvc.model.shapes.Circle;
import isel.mpd.mvc.model.shapes.IShape;

import java.awt.*;

public class CircleView implements ShapeView {
    private final Circle circle;

    public CircleView(IShape s) {
        circle = (Circle) s;
    }

    @Override
    public void drawOn(Graphics g) {
        Point p = circle.getRef();

        // Uncomment to see the shape's bounds
//        g.setColor(Color.RED);
//        g.fillRect(circle.getBounds().x, circle.getBounds().y,
//                circle.getBounds().width, circle.getBounds().height);

        g.setColor(circle.getColor());
        g.fillOval(p.x, p.y, circle.getRadius(), circle.getRadius());
    }

    public IShape getModel() { return circle; }
}
