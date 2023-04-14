package isel.mpd.mvc.model.commands;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.view.configdrawers.ConfigContext;
import isel.mpd.mvc.view.configdrawers.MoveConfig;

import java.awt.*;

public class MoveCmd implements Command{

    private final App app;
    private final ConfigContext ctx;
    private IShape shape;

    private int transx;
    private int transy;

    private  Point oldpoint;
    private Point newpoint;

    public MoveCmd(App app, ConfigContext ctx) {
        this.app = app;
        this.ctx = ctx;
    }

    public void shape_select(){
        for (IShape s : app.getShapes()) {
            if (s.contains(ctx.getCurr())) {
                shape = s;
                ctx.setConfigurator(new MoveConfig(shape));
            }
        }
    }

    public void drag(){
        if(shape != null){
            int centerx = shape.getRef().x;
            int centery = shape.getRef().y;
            transx = ctx.getCurr().x - centerx;
            transy = ctx.getCurr().y - centery;
            }
    }
    @Override
    public void execute() {
        if(shape != null){
            shape.translate(transx,transy);
            shape = null;
        }
    }

    @Override
    public void undo() {
    //Not done
    }

}