package isel.mpd.mvc.view;

import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.model.shapes.Oval;

import java.awt.*;

public class OvalView implements ShapeView {
    private final Oval oval;

    public OvalView(IShape s) {
        oval = (Oval) s;
    }

    @Override
    public void drawOn(Graphics g) {
        Point p = oval.getRef();

        // Uncomment to see the shape's bounds
//        g.setColor(Color.RED);
//        g.fillRect(oval.getBounds().x, oval.getBounds().y,
//                oval.getBounds().width, oval.getBounds().height);

        g.setColor(oval.getColor());
        g.fillOval(p.x, p.y, oval.getWidth(), oval.getHeight());
    }

    @Override
    public IShape getModel() { return oval; }
}
