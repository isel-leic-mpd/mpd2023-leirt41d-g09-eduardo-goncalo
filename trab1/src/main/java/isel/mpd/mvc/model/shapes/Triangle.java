package isel.mpd.mvc.model.shapes;

import java.awt.*;
import java.util.Arrays;

public class Triangle implements IShape {
    private final Point start;
    private final Point curr;
    private final Color color;
    private Rectangle bounds;

    // Used by isel.mpd.mvc.utils.XmlSerializer.fromXml
    public Triangle() {
        start = null;
        curr = null;
        color = null;
    }

    public Triangle(Point start, Point curr, Color color) {
        this.start = start;
        this.color = color;
        this.curr = curr;
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
        curr.translate(dx,dy);
        bounds = null;
    }

    @Override
    public Rectangle getBounds() {
        if (bounds == null) {
            Point curr = getCurr(), ref = getRef();
            double aux = (curr.getX() - ref.getX()) / 2;

            Point p = new Point();
            p.x = (int) (ref.getX() + aux);
            p.y = (int) (curr.getY() - aux);

            int[] xPoints = { (int) ref.getX(), p.x, (int) curr.getX() };
            int[] yPoints = { (int) ref.getY(), p.y, (int) curr.getY() };

            int x = Arrays.stream(xPoints).min().getAsInt();
            int y = Arrays.stream(yPoints).min().getAsInt();

            int w = Arrays.stream(xPoints).max().getAsInt() - x;
            int h = Arrays.stream(yPoints).max().getAsInt() - y;

            bounds = new Rectangle(x, y, w, h);
        }
        return bounds;
    }

    @Override
    public Color getColor() { return color; }

    @Override
    public Point getRef() { return copy(start); }

    public Point getCurr() { return copy(curr); }

    @Override
    public String toSvg() {
        Point curr = getCurr(), ref = getRef();
        double aux = (curr.getX() - ref.getX()) / 2;

        Point p = new Point();
        p.x = (int) (ref.getX() + aux);
        p.y = (int) (curr.getY() - aux);

        String p1 = ref.x + "," + ref.y;
        String p2 = p.x + "," + p.y;
        String p3 = curr.x + "," + curr.y;

        return "\t<polygon points=\"" +
                p1 + " " + p2 + " " + p3 +
                "\" fill=\"rgb(" +
                getColor().getRed() + ", " +
                getColor().getGreen() + ", " +
                getColor().getBlue() + ")\"/>\n";
    }
}
