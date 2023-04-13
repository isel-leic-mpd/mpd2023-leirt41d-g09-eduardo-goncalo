package isel.mpd.mvc.model.commands;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.view.configdrawers.*;

public class CommandFactory {
    public static Command createCommand(String name, App app, ConfigContext ctx) {
        if (name.equals(App.SHAPE_CMD_RECT)) return new addShapeCmd(app, ctx);
        if (name.equals(App.SHAPE_CMD_TRIANGLE)) return new addShapeCmd(app, ctx);
        if (name.equals(App.SHAPE_CMD_OVAL)) return new addShapeCmd(app, ctx);
        if (name.equals(App.SHAPE_CMD_LINE)) return new addShapeCmd(app, ctx);
        if (name.equals(App.SHAPE_CMD_CIRCLE)) return new addShapeCmd(app, ctx);
        if (name.equals(App.CMD_MOVE)) return new moveCmd(app, ctx);
        if (name.equals(App.CMD_REMOVE)) return new removeCmd(app, ctx);
//        // other commands creations
        return null;
    }
}
