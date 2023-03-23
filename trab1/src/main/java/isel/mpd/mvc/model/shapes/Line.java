package isel.mpd.mvc.model.shapes;

import java.awt.*;

public class Line implements IShape {
    private final Point start;
    private final Point curr;
    private final Color color;
    private Rectangle bounds;


    public Line(Point start, Point curr, Color color) {
        this.start = start;
        this.curr = curr;
        this.color = color;
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
        start.translate(dx, dy);
        bounds = null;
    }

    @Override
    public Rectangle getBounds() {
        if (bounds == null) {
            int x = start.x;
            int y = start.y;
            int w = (int) getCurr().getX() - x;
            int h = (int) getCurr().getY() - y;
            if (w < 0) {
                x = (int) getCurr().getX();
                w *= -1;
            }
            if (h < 0) {
                y = (int) getCurr().getY();
                h *= -1;
            }
            bounds = new Rectangle(x, y, w, h);
        }
        return bounds;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Point getRef() { return copy(start); }

    public Point getCurr() { return copy(curr); }

}
