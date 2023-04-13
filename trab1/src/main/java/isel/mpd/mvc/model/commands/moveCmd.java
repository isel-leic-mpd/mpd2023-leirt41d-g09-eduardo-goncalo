package isel.mpd.mvc.model.commands;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.view.configdrawers.ConfigContext;

import java.awt.*;

public class moveCmd implements Command{

    private final App app;
    private final ConfigContext ctx;
    private IShape shape;

    private Point point;


    public moveCmd(App app, ConfigContext ctx) {
        this.app = app;
        this.ctx = ctx;
    }

    public void shape_select(){
        for (IShape s : app.getShapes()) {
            if (s.contains(ctx.getCurr())) {
                shape = s;
            }
        }
    }

    public void drag(){
        if(shape != null){
            int centerx = (shape.getRef().x + shape.getBounds().width/2);
            int centery = (shape.getRef().y + shape.getBounds().height/2);
            if(ctx.getCurr().x  -shape.getRef().x < 0) centerx = (shape.getRef().x - shape.getBounds().width/2);
            if(ctx.getCurr().y  -shape.getRef().y < 0)centery = (shape.getRef().y - shape.getBounds().height/2);
            int transx = ctx.getCurr().x - centerx;
            int transy = ctx.getCurr().y - centery;
            shape.translate(transx, transy);}
    }
    @Override
    public void execute() {
        shape = null;
    }

    @Override
    public void undo() {
    //Not done
    }

}