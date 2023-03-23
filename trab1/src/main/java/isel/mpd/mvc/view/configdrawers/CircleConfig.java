package isel.mpd.mvc.view.configdrawers;

import isel.mpd.mvc.model.shapes.Circle;
import isel.mpd.mvc.model.shapes.IShape;

import java.awt.*;

public class CircleConfig implements ConfigDrawer {
    private ConfigContext ctx;

    @Override
    public void setContext(ConfigContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public IShape createShape() {
        return new Circle(ctx.getRef(), ctx.getWidth(), ctx.getColor());
    }

    @Override
    public void draw(Graphics2D gc) {
        if (ctx.getRef() == null) return;

        gc.setColor(ctx.getColor());
        gc.drawOval(ctx.getXs(), ctx.getYs(), ctx.getWidth(), ctx.getWidth());
    }
}
