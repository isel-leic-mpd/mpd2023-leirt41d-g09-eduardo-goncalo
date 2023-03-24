package isel.mpd.mvc.model;

import isel.mpd.mvc.model.shapes.IShape;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Picture implements Iterable<IShape>, Serializable {

    private LinkedList<IShape> shapes;

    public Picture() {
        shapes = new LinkedList<>();
    }
    public void add(IShape shape) {
        shapes.add(shape);
    }

    public void remove(IShape shape) {
        shapes.remove(shape);
    }

    @Override
    public Iterator<IShape> iterator() {
        return shapes.iterator();
    }
}
