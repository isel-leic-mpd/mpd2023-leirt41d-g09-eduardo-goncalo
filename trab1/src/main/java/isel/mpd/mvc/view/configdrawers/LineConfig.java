package isel.mpd.mvc.view.configdrawers;

import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.model.shapes.Line;

import java.awt.*;

public class LineConfig implements ConfigDrawer {
    private ConfigContext ctx;

    @Override
    public void setContext(ConfigContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public IShape createShape() {
        return new Line(ctx.getRef(), ctx.getCurr(), ctx.getColor());
    }

    @Override
    public void draw(Graphics2D gc) {
        if (ctx.getRef() == null) return;

        Point curr = ctx.getCurr(), ref = ctx.getRef();

        gc.setColor(ctx.getColor());
        gc.drawLine((int) ref.getX(), (int) ref.getY(), (int) curr.getX(), (int) curr.getY());
    }
}
