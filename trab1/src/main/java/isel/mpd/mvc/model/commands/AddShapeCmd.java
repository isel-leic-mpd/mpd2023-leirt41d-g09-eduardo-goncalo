package isel.mpd.mvc.model.commands;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.view.configdrawers.ConfigContext;

public class AddShapeCmd implements Command {
    private final App app;
    private final ConfigContext ctx;
    private IShape shape;

    public AddShapeCmd(App app, ConfigContext ctx) {
        this.app = app;
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        shape = ctx.getConfigurator().createShape();
        app.addShape(shape);
    }

    @Override
    public void undo() {
        app.removeShape(shape);
        for (IShape s : app.getShapes())
            shape = s;
    }
}
