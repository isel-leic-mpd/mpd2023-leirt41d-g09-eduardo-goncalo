package isel.mpd.mvc.view.configdrawers;


import isel.mpd.mvc.model.commands.Command;

import java.awt.*;

/**
 * In this class we use the  strategy pattern
 * to draw the bounds of the shape we are create
 * and draw rhe final filled shape
 */
public final  class ConfigContext {

    private  Point ref, curr, mouseCurr;
    private  Color color = Color.BLACK;
    private ConfigDrawer configurator;
    private Command command;

    // accessors
    public ConfigDrawer setConfigurator(ConfigDrawer configurator) {
        this.configurator = configurator;
        configurator.setContext(this);
        return configurator;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public ConfigDrawer getConfigurator() {
       return configurator;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public Color getColor() {
        return color;
    }

    public void start(Point ref) {
        this.ref = ref;
        this.curr = ref;
    }


    public void start(Point ref, Point curr, Point mouseCurr) {
        this.mouseCurr = mouseCurr;
        this.ref = new Point(ref.x, ref.y);
        this.curr =  new Point(curr.x, curr.y);
    }


    public Command getCommand() { return command; }

    public int getWidth() {
        return curr.x - ref.x + 1;
    }

    public int getHeight() {
        return curr.y - ref.y + 1;
    }

    public Point getRef() { return ref; }

    public int getXs() { return ref.x; }
    public int getYs() { return ref.y; }

    public int getXc() { return curr.x; }
    public int getYc() { return curr.y; }

    public void setCurr(Point curr) {
        this.curr = curr;
    }

    public Point getCurr() {
        return curr;
    }
}
