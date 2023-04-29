package isel.mpd.mvc.view;

import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.model.shapes.Rect;

import java.awt.*;

public class RectView implements ShapeView  {
    private Rect rect;

    public RectView(IShape s) {
        rect = (Rect) s;
    }
    
    @Override
    public void drawOn(Graphics g) {
        Point p = rect.getRef();
        g.setColor(rect.getColor());
        g.fillRect(p.x, p.y, rect.getWidth(), rect.getHeight());
    }

    @Override
    public IShape getModel() { return rect; }
}
