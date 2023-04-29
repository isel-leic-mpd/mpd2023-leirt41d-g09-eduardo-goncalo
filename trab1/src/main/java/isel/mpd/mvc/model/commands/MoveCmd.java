package isel.mpd.mvc.model.commands;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.view.DebugFrame;
import isel.mpd.mvc.view.configdrawers.ConfigContext;
import isel.mpd.mvc.view.configdrawers.MoveConfig;

import java.awt.*;

public class MoveCmd implements Command{

    private final App app;
    private final ConfigContext ctx;
    private IShape shape;
    private Point pt = null;

    private int transx;
    private int transy;


    public MoveCmd(App app, ConfigContext ctx) {
        this.app = app;
        this.ctx = ctx;
    }

    public IShape shape_select(Point p){

        shape = app.shape_pressed(p);
        if(shape != null){pt = p;}
        return shape;
    }

    public Rectangle drag(){
        if(shape != null){
            transx = ctx.getCurr().x - pt.x;
            transy = ctx.getCurr().y - pt.y;
            }
        return shape.getBounds();
    }
    @Override
    public void execute() {
        if(shape != null) {
            DebugFrame.menuRemoveItem(shape);
            shape.translate(transx, transy);
            DebugFrame.menuAddItem(shape);
        }
    }

    @Override
    public void undo() {
    shape.translate(-transx,-transy);
    }

}