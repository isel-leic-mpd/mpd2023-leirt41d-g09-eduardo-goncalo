package isel.mpd.mvc.view;

import isel.mpd.mvc.model.PictureChangedListener;

import isel.mpd.mvc.model.shapes.*;
import isel.mpd.mvc.view.configdrawers.ConfigDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PaintPanel extends JComponent implements PictureChangedListener
{

    private final LinkedList<ShapeView> shapes;
    private ConfigDrawer configurator;
    private boolean configMode;

    // strokes
    private BasicStroke dashedStroke = createDashed();
    BasicStroke lineWith = new BasicStroke(4);

    private static BasicStroke createDashed() {
        float[] dash1 = { 2f, 0f, 2f };
        BasicStroke dashed = new BasicStroke(2,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_BEVEL,
            1.0f,
            dash1,
            2f);
        return dashed;
    }


    public PaintPanel(int w, int h, Color backColor) {

        setPreferredSize(new Dimension(w,h));
        setBackground(backColor);
        shapes = new LinkedList<>();
    }


    public void setConfigMode(ConfigDrawer configurator) {

        this.configurator = configurator;
        this.configMode = true;
    }

    public void setPaintMode() {
        this.configurator  = null;
        this.configMode = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gc = (Graphics2D)  g;
        //super.paintComponent(g);
        g.setColor(getBackground());

        var w = getWidth();
        var h = getHeight();

        g.fillRect(0,0, w, h);


        for(ShapeView sv : shapes) {
            sv.drawOn(g);
        }
        if (configurator != null) {
            gc.setStroke(dashedStroke);
            configurator.draw(gc);
        }

    }

    public static ShapeView buildFrom(IShape s) {
        Class c = s.getClass();
        if (c == Rect.class) return new RectView(s);
        if (c == Triangle.class) return new TriangleView(s);
        if (c == Oval.class) return new OvalView(s);
        if (c == Line.class) return new LineView(s);
        if (c == Circle.class) return new CircleView(s);

        return null;
    }

    @Override
    public void newShape(IShape s) {
        shapes.add(buildFrom(s));
        repaint();
    }

    public Iterable<ShapeView> getViews() {
        return shapes;
    }

}
