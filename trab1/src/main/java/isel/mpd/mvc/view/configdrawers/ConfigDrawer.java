package isel.mpd.mvc.view.configdrawers;

import isel.mpd.mvc.model.shapes.IShape;

import java.awt.*;

public interface ConfigDrawer {
    void setContext(ConfigContext ctx);
    void draw(Graphics2D gc);

    IShape createShape();
}
