package isel.mpd.mvc.view;

import isel.mpd.mvc.model.shapes.*;

import javax.swing.*;
import java.awt.*;

public class DebugFrame extends JFrame {
    private static DebugPanel debugPanel;
    private static MyJMenuBar menuBar;

    public DebugFrame() {
        buildMenu();
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setTitle("Debug");
        setResizable(false);
    }

    private void initComponents() {
        BorderLayout layout = new BorderLayout();
        Container container = getContentPane();
        container.setLayout(layout);
        debugPanel = new DebugPanel();

        container.add(debugPanel, BorderLayout.CENTER);
    }

    private static class MyJMenuBar extends JMenuBar {
        JMenu details = new JMenu("Details");
        JMenu rect = new JMenu("Rect");
        JMenu triangle = new JMenu("Triangle");
        JMenu oval = new JMenu("Oval");
        JMenu line = new JMenu("Line");
        JMenu circle = new JMenu("Circle");

        public MyJMenuBar() {
            details.add(rect);
            details.add(triangle);
            details.add(oval);
            details.add(line);
            details.add(circle);
            add(details);
        }

        private String createMenuItemText(IShape s) {
            return String.format("(%.0f, %.0f)",s.getRef().getX(), s.getRef().getY());
        }

        private JMenu selectMenuItem(IShape s) {
            if (s instanceof Rect) return rect;
            else if (s instanceof Triangle) return triangle;
            else if (s instanceof Oval) return oval;
            else if (s instanceof Line) return line;
            else if (s instanceof Circle) return circle;
            return null;
        }

        public void addItem(IShape s) {
            JMenu m = selectMenuItem(s);
            if (m != null) {
                JMenuItem item = new JMenuItem(createMenuItemText(s));
                item.addActionListener(evt -> debugPanel.displayShapeDetails(s));
                m.add(item);
            }
        }

        public void removeItem(IShape s) {
            JMenu m = selectMenuItem(s);
            if (m != null) {
                for (int i = 0; i < m.getItemCount(); ++i) {
                    if (m.getItem(i).getText().equals(createMenuItemText(s))) {
                        m.remove(m.getItem(i));
                        break;
                    }
                }
            }
        }
    }

    private void buildMenu() {
        menuBar = new MyJMenuBar();
        setJMenuBar(menuBar);
    }

    public static void menuAddItem(IShape s) {
        menuBar.addItem(s);
        menuBar.updateUI();
    }

    public static void menuRemoveItem(IShape s) {
        menuBar.removeItem(s);
        menuBar.updateUI();
    }
}
