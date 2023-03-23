package isel.mpd.mvc.view.configdrawers;

import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.model.shapes.Oval;

import java.awt.*;

public class OvalConfig implements ConfigDrawer {
    private ConfigContext ctx;

    @Override
    public void setContext(ConfigContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public IShape createShape() {
        return new Oval(ctx.getRef(), ctx.getWidth(), ctx.getHeight(), ctx.getColor());
    }

    @Override
    public void draw(Graphics2D gc) {
        if (ctx.getRef() == null) return;

        gc.setColor(ctx.getColor());
        gc.drawOval(ctx.getXs(), ctx.getYs(), ctx.getWidth(), ctx.getHeight());
    }
}
