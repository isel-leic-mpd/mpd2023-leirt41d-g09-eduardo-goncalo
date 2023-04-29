package isel.mpd.mvc.model.commands;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.view.configdrawers.*;

public class CommandFactory {

    public static Command createCommand(String name, App app, ConfigContext ctx) {
        if (name.equals(App.SHAPE_CMD_RECT)) return new AddShapeCmd(app, ctx);
        if (name.equals(App.SHAPE_CMD_TRIANGLE)) return new AddShapeCmd(app, ctx);
        if (name.equals(App.SHAPE_CMD_OVAL)) return new AddShapeCmd(app, ctx);
        if (name.equals(App.SHAPE_CMD_LINE)) return new AddShapeCmd(app, ctx);
        if (name.equals(App.SHAPE_CMD_CIRCLE)) return new AddShapeCmd(app, ctx);
        if (name.equals(App.CMD_MOVE)) return new MoveCmd(app, ctx);
        if (name.equals(App.CMD_REMOVE)) return new RemoveCmd(app, ctx);
        if (name.equals(App.SHAPE_CMD_GROUP)) return new GroupShapeCmd(app, ctx);
//        // other commands creations
        return null;
    }
}
