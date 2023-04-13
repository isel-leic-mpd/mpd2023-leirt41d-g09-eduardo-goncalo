package isel.mpd.mvc.model.commands;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.view.configdrawers.ConfigContext;

import java.awt.*;

public class moveCmd implements Command{

    private final App app;
    private final ConfigContext ctx;
    private IShape shape;


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
            int transx = ctx.getCurr().x - shape.getRef().x;
            int transy = ctx.getCurr().y - shape.getRef().y;
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