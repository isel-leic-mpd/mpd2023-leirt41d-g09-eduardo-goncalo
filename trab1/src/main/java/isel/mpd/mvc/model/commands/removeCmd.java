package isel.mpd.mvc.model.commands;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.view.configdrawers.ConfigContext;

import java.awt.*;

public class removeCmd implements Command{

    private final App app;
    private final ConfigContext ctx;
    private IShape shape;

    public removeCmd(App app, ConfigContext ctx) {
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


    @Override
    public void execute() {
        app.removeShape(shape);
    }

    @Override
    public void undo() {
        app.addShape(shape);
    }
}