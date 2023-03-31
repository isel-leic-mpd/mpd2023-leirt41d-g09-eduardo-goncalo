package isel.mpd.mvc.view;

import isel.mpd.mvc.model.shapes.IShape;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DebugPanel extends JPanel {
    public static int DEBUG_PANEL_SIZE_X = 300;
    public static int DEBUG_PANEL_SIZE_Y = 250;

    private static class MyTitledBorder extends TitledBorder {
        public MyTitledBorder(String title) {
            super(title);
        }
    }

    private static class MyJTextArea extends JTextArea {
        public MyJTextArea(String borderTitle) {
            super();
            setBorder(new MyTitledBorder(borderTitle));
            setPreferredSize(new Dimension(DEBUG_PANEL_SIZE_X, DEBUG_PANEL_SIZE_Y / 5));
            setEditable(false);
        }
    }

    private final JTextArea shape = new MyJTextArea("Shape");
    private final JTextArea ref = new MyJTextArea("Ref. Point");
    private final JTextArea width = new MyJTextArea("Width");
    private final JTextArea height = new MyJTextArea("Height");
    private final JTextArea color = new MyJTextArea("Color");

    public DebugPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(shape);
        add(ref);
        add(width);
        add(height);
        add(color);
    }

    public void displayShapeDetails(IShape s) {
        shape.setText(s.getClass().getSimpleName());
        ref.setText(String.format("(%.0f, %.0f)", s.getRef().getX(), s.getRef().getY()));
        width.setText(String.format("%.0f", s.getBounds().getWidth()));
        height.setText(String.format("%.0f", s.getBounds().getHeight()));
        color.setBackground(s.getColor());
    }
}
