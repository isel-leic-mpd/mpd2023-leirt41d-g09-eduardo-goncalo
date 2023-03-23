package isel.mpd.mvc.view.configdrawers;

import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.model.shapes.Triangle;

import java.awt.*;

public class TriangleConfig implements ConfigDrawer {
    private ConfigContext ctx;

    @Override
    public void setContext(ConfigContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public IShape createShape() {
        return new Triangle(ctx.getRef(), ctx.getCurr(), ctx.getColor());
    }

    @Override
    public void draw(Graphics2D gc) {
        if (ctx.getRef() == null) return;

        Point curr = ctx.getCurr(), ref = ctx.getRef();
        double aux = (curr.getX() - ref.getX()) / 2;

        Point p = new Point();
        p.x = (int) (ref.getX() + aux);
        p.y = (int) (curr.getY() - aux);

        int[] xPoints = { (int) ref.getX(), p.x, (int) curr.getX() };
        int[] yPoints = { (int) ref.getY(), p.y, (int) curr.getY() };

        gc.setColor(ctx.getColor());
        gc.drawPolygon(xPoints, yPoints, 3);
    }
}
