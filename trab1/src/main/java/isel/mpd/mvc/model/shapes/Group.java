package isel.mpd.mvc.model.shapes;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public class Group implements IShape  {
    private final Point start;
    private final List<IShape> shapes;
    private final int w;
    private final int h;
    private Rectangle bounds;

    public Group(List<IShape> shapes) {
        this.shapes = shapes;
        int[] x = {0, 0};
        int[] y = {0, 0};

        if (!shapes.isEmpty()) {
            x[0] = shapes.get(0).getBounds().x;
            y[0] = shapes.get(0).getBounds().y;
            for (var s : shapes) {
                if (s.getBounds().x <= x[0]) x[0] = s.getBounds().x;
                if (s.getBounds().y <= y[0]) y[0] = s.getBounds().y;
            }
            for (var s : shapes) {
                if (s.getBounds().x + s.getBounds().width >= x[1]) x[1] = s.getBounds().x + s.getBounds().width;
                if (s.getBounds().y + s.getBounds().height >= y[1]) y[1] = s.getBounds().y + s.getBounds().height;
            }
        }

        start = new Point(x[0], y[0]);
        this.w = x[1] - x[0];
        this.h = y[1] - y[0];
    }

    protected Point copy(Point p) {
        return new Point(p.x, p.y);
    }

    @Override
    public boolean intersects(IShape other) {
        return getBounds().intersects(other.getBounds());
    }

    @Override
    public boolean contains(Point p) {
        return getBounds().contains(p);
    }

    @Override
    public boolean contains(IShape other) {
        return getBounds().contains(other.getBounds());
    }

    @Override
    public void translate(int dx, int dy) {
        for (var s : shapes) s.translate(dx, dy);
        bounds = null;
    }

    public int getWidth() { return w; }

    public int getHeight() { return h; }

    public Iterable<IShape> getShapes() { return shapes; }

    @Override
    public Rectangle getBounds() {
        if (bounds == null)
            bounds =  new Rectangle(start.x, start.y, getWidth(), getHeight());
        return bounds;
    }

    @Override
    public Color getColor() { return null; }

    @Override
    public Point getRef() { return copy(start); }

    @Override
    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        for (var s : shapes) sb.append(s.toSvg());
        return sb.toString();
    }
}

