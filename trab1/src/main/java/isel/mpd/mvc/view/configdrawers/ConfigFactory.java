package isel.mpd.mvc.view.configdrawers;

import isel.mpd.mvc.app.App;

import java.util.Map;

public class ConfigFactory {
    private static Map<String, ConfigDrawer> configMap = Map.ofEntries (
        Map.entry(App.SHAPE_CMD_RECT, new RectConfig()),
        Map.entry(App.SHAPE_CMD_TRIANGLE, new TriangleConfig()),
        Map.entry(App.SHAPE_CMD_OVAL, new OvalConfig()),
        Map.entry(App.SHAPE_CMD_LINE, new LineConfig()),
        Map.entry(App.SHAPE_CMD_CIRCLE, new CircleConfig())
        // other entries...
    );

    public static ConfigDrawer createConfigDrawer(String name) {
        if (name.equals(App.SHAPE_CMD_RECT)) return new RectConfig();
        if (name.equals(App.SHAPE_CMD_TRIANGLE)) return new TriangleConfig();
        if (name.equals(App.SHAPE_CMD_OVAL)) return new OvalConfig();
        if (name.equals(App.SHAPE_CMD_LINE)) return new LineConfig();
        if (name.equals(App.SHAPE_CMD_CIRCLE)) return new CircleConfig();
        // other drawers creations
        return null;
    }

    // alternative using a ConfigDrawer map by name
    public static ConfigDrawer createConfigDrawer2(String name) {
        return configMap.get(name);
    }

}
