package isel.mpd.mvc.view.configdrawers;

import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.model.shapes.Rect;

import java.awt.*;


    public class MoveConfig implements ConfigDrawer{

        private ConfigContext ctx;
        private IShape rect;
        private Point p;



        public MoveConfig(IShape rect, Point p){
            this.rect = rect;
            this.p = p;
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
            if (ctx.getRef() != null){
                gc.setColor(ctx.getColor());
                gc.drawRect(rect.getBounds().x - ctx.getRef().x + p.x, rect.getBounds().y - ctx.getRef().y + p.y, rect.getBounds().width, rect.getBounds().height);}
        }
    }
