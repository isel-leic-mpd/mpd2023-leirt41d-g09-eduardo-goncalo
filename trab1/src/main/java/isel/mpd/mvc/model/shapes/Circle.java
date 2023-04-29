package isel.mpd.mvc.model.shapes;

import java.awt.*;

public class Circle implements IShape {
    private final Point start;
    private final int radius;
    private final Color color;
    private Rectangle bounds;

    // Used by isel.mpd.mvc.utils.XmlSerializer.fromXml
    public Circle() {
        start = null;
        radius = 0;
        color = null;
    }

    public Circle(Point start, int radius, Color color) {
        this.start = start;
        this.radius = radius;
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
        if (bounds == null)
            bounds =  new Rectangle(start.x, start.y, getRadius(), getRadius());
        return bounds;
    }

    @Override
    public Color getColor() { return color; }

    @Override
    public Point getRef() { return copy(start); }

    public int getRadius() { return radius; }

    @Override
    public String toSvg() {
        int r  = getRadius()/2;
        int cx = getRef().x + r;
        int cy = getRef().y + r;

        return "\t<circle cx=\"" + cx + "\" cy=\"" + cy +
                "\" r=\"" + r + "\" fill=\"rgb(" +
                getColor().getRed() + ", " +
                getColor().getGreen() + ", " +
                getColor().getBlue() + ")\"/>\n";
    }
}
