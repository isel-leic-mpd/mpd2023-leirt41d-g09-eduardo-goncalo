package isel.mpd.mvc.model;

import isel.mpd.mvc.model.shapes.IShape;

public interface PictureChangedListener {
    void newShape(IShape s);

    void removeShape(IShape s);
}
