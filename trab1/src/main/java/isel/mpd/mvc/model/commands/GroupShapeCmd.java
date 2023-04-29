package isel.mpd.mvc.model.commands;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.Group;
import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.view.configdrawers.ConfigContext;

import java.util.ArrayList;
import java.util.List;

public class GroupShapeCmd implements Command {
    private Group group;
    private final App app;
    private final ConfigContext ctx;
    private List<IShape> shapes;

    public GroupShapeCmd(App app, ConfigContext ctx) {
        this.app = app;
        this.ctx = ctx;
    }

    @Override
    public void execute() {
        shapes = new ArrayList<>();
        shapes.addAll(app.shapeSelect(ctx.getConfigurator().createShape()));
        if (!shapes.isEmpty()) {
            for (var s : shapes) app.removeShape(s);
            group = new Group(shapes);
            app.addShape(group);
        }
    }

    @Override
    public void undo() {
        for (var s : shapes) app.addShape(s);
        app.removeShape(group);
    }
}
