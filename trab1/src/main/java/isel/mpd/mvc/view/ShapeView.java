package isel.mpd.mvc.view;

import isel.mpd.mvc.model.shapes.IShape;

import java.awt.*;

public interface ShapeView {
    void drawOn(Graphics g);
    IShape getModel();
}
