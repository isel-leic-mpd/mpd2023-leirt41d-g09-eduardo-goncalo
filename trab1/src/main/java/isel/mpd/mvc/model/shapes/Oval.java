package isel.mpd.mvc.model.shapes;

import java.awt.*;

public class Oval implements IShape {
    private final Point start;
    private final int w;
    private final int h;
    private final Color color;
    private Rectangle bounds;

    // Used by isel.mpd.mvc.utils.XmlSerializer.fromXml
    public Oval() {
        start = null;
        w = 0;
        h = 0;
        color = null;
    }

    public Oval(Point start, int w, int h, Color color) {
        this.start = start;
        this.color = color;
        this.w = w;
        this.h = h;
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
            bounds =  new Rectangle(start.x, start.y, getWidth(), getHeight());
        return bounds;
    }

    @Override
    public Color getColor() { return color; }

    @Override
    public Point getRef() { return copy(start); }

    public int getWidth() { return w; }

    public int getHeight() { return h; }

    @Override
    public String toSvg() {
        int rx = w / 2;
        int ry = h / 2;
        int cx = getRef().x + rx;
        int cy = getRef().y + ry;

        return "\t<ellipse cx=\"" + cx + "\" cy=\"" + cy +
                "\" rx=\"" + rx + "\" ry=\"" + ry + "\" fill=\"rgb(" +
                getColor().getRed() + ", " +
                getColor().getGreen() + ", " +
                getColor().getBlue() + ")\"/>\n";
    }
}
