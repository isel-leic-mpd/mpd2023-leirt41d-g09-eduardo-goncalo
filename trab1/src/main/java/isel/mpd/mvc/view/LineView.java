package isel.mpd.mvc.view;

import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.model.shapes.Line;

import java.awt.*;

public class LineView implements ShapeView {
    private final Line line;

    public LineView(IShape s) {
        line = (Line) s;
    }

    @Override
    public void drawOn(Graphics g) {
        Point curr = line.getCurr(), ref = line.getRef();

        // Uncomment to see the shape's bounds
//        g.setColor(Color.RED);
//        g.fillRect(line.getBounds().x, line.getBounds().y,
//                line.getBounds().width, line.getBounds().height);

        g.setColor(line.getColor());
        g.drawLine((int) ref.getX(), (int) ref.getY(), (int) curr.getX(), (int) curr.getY());
    }

    @Override
    public IShape getModel() { return line; }
}
