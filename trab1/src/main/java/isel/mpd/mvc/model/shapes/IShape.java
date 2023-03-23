package isel.mpd.mvc.model.shapes;

import java.awt.*;

public interface IShape {
    Point getRef();
    Color getColor();
    boolean intersects(IShape other);
    boolean contains(Point p);
    boolean contains(IShape other);
    void translate(int dx, int dy);
    Rectangle getBounds();
}
