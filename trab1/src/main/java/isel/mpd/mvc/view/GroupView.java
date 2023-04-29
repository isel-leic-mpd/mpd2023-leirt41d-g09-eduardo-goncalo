package isel.mpd.mvc.view;

import isel.mpd.mvc.model.shapes.*;

import java.awt.*;

public class GroupView implements ShapeView  {
    private final Group group;

    public GroupView(IShape s) {
        group = (Group) s;
    }

    private ShapeView selectShapeView(IShape s) {
        if (s.getClass().equals(Rect.class)) return new RectView(s);
        if (s.getClass().equals(Triangle.class)) return new TriangleView(s);
        if (s.getClass().equals(Oval.class)) return new OvalView(s);
        if (s.getClass().equals(Line.class)) return new LineView(s);
        if (s.getClass().equals(Circle.class)) return new CircleView(s);
        if (s.getClass().equals(Group.class)) return new GroupView(s);
        return null;
    }

    @Override
    public void drawOn(Graphics g) {
        // Uncomment to see the group's bounds
//        g.setColor(Color.RED);
//        g.fillRect(group.getBounds().x, group.getBounds().y,
//                group.getBounds().width, group.getBounds().height);

        for (var s : group.getShapes())
            selectShapeView(s).drawOn(g);
    }

    public IShape getModel() { return group; }
}
