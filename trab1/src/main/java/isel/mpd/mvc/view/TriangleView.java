package isel.mpd.mvc.view;

import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.model.shapes.Triangle;

import java.awt.*;

public class TriangleView implements ShapeView {
    private final Triangle triangle;

    public TriangleView(IShape s) {
        triangle = (Triangle) s;
    }

    @Override
    public void drawOn(Graphics g) {
        Point curr = triangle.getCurr(), ref = triangle.getRef();
        double aux = (curr.getX() - ref.getX()) / 2;

        Point p = new Point();
        p.x = (int) (ref.getX() + aux);
        p.y = (int) (curr.getY() - aux);

        int[] xPoints = { (int) ref.getX(), p.x, (int) curr.getX() };
        int[] yPoints = { (int) ref.getY(), p.y, (int) curr.getY() };

        // Uncomment to see the shape's bounds
//        g.setColor(Color.RED);
//        g.fillRect(triangle.getBounds().x, triangle.getBounds().y,
//                triangle.getBounds().width, triangle.getBounds().height);

        g.setColor(triangle.getColor());
        g.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public IShape getModel() { return triangle; }
}
