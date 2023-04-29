/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package isel.mpd.mvc.app;

import isel.mpd.mvc.model.Picture;
import isel.mpd.mvc.model.PictureChangedListener;
import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.view.DebugFrame;
import isel.mpd.mvc.view.PainterFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    // Command names
    public static final String SHAPE_CMD_RECT = "Rect";
    public static final String SHAPE_CMD_TRIANGLE = "Triangle";
    public static final String SHAPE_CMD_OVAL = "Oval";
    public static final String SHAPE_CMD_CIRCLE = "Circle";
    public static final String SHAPE_CMD_LINE= "Line";
    public static final String SHAPE_CMD_GROUP = "Group";
    public static final String CMD_UNDO = "Undo";
    public static final String CMD_ADD = "Add";
    public static final String CMD_MOVE = "Move";
    public static final String CMD_REMOVE = "Remove";

    private final Picture picture;

    private PictureChangedListener listener;

    private App() {
        picture = new Picture();
    }

    private void fireAddShape(IShape s) {
        if (listener != null)
            listener.newShape(s);
    }

    private void fireRemoveShape(IShape s) {
        if (listener != null)
            listener.removeShape(s);
    }

    public void setListener(PictureChangedListener listener) {
        this.listener = listener;
    }

    public void addShape(IShape s) {
        picture.add(s);
        fireAddShape(s);
        DebugFrame.menuAddItem(s);
    }

    public void removeShape(IShape s) {
        picture.remove(s);
        fireRemoveShape(s);
        DebugFrame.menuRemoveItem(s);
    }

    public Iterable<IShape> getShapes() {
        return picture;
    }

    public IShape shapeselect(Point p){
        IShape shape = null;
        for (IShape s : getShapes()){
            if (s.contains(p)) {
                shape = s;
            }
        }
        return shape;
    }

    public List<IShape> shapeSelect(IShape select) {
        List<IShape> shapes = new ArrayList<>();
        for (IShape s : getShapes()) {
            if (select.contains(s))
                shapes.add(s);
        }
        return shapes;
    }

    public static void main(String[] args) {
        App app = new App();

        PainterFrame painter = new PainterFrame(app);
        DebugFrame debug = new DebugFrame();

        painter.setVisible(true);
        debug.setVisible(true);
        debug.setLocation(painter.getX() + PainterFrame.CANVAS_SIZE_X, painter.getY());
    }
}
