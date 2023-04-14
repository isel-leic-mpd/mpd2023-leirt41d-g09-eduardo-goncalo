package isel.mpd.mvc.view.configdrawers;

import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.model.shapes.Rect;

import java.awt.*;

public class MoveConfig implements ConfigDrawer{

    private ConfigContext ctx;
    private IShape rect;

    public MoveConfig(IShape rect){

        this.rect = rect;
    }

    @Override
    public void setContext(ConfigContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public IShape createShape() {
       return null;
    }

    @Override
    public void draw(Graphics2D gc) {
        if (ctx.getRef() == null) return;
        gc.setColor(ctx.getColor());
        gc.drawRect(rect.getBounds().x, rect.getBounds().y, rect.getBounds().width, rect.getBounds().height);
    }
}
