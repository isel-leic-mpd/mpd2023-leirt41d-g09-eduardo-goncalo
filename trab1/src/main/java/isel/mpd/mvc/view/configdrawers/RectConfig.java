package isel.mpd.mvc.view.configdrawers;

import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.model.shapes.Rect;

import java.awt.*;

public class RectConfig implements ConfigDrawer {
    private ConfigContext ctx;

    @Override
    public void setContext(ConfigContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public IShape createShape() {
        return new Rect(ctx.getRef(), ctx.getWidth(), ctx.getHeight(), ctx.getColor());
    }

    @Override
    public void draw(Graphics2D gc) {
        if (ctx.getRef() == null) return;

        gc.setColor(ctx.getColor());
        gc.drawRect(ctx.getXs(), ctx.getYs(), ctx.getWidth(), ctx.getHeight());
    }
}
