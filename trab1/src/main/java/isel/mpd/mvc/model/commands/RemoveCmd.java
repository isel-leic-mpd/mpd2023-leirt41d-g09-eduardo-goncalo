package isel.mpd.mvc.model.commands;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.view.configdrawers.ConfigContext;

public class RemoveCmd implements Command{

    private final App app;
    private final ConfigContext ctx;
    private IShape shape;

    public RemoveCmd(App app, ConfigContext ctx) {
        this.app = app;
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        shape = app.shape_pressed(ctx.getCurr());
        app.removeShape(shape);
    }

    @Override
    public void undo() {
        app.addShape(shape);
    }
}