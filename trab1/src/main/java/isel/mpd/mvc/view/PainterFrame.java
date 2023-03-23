package isel.mpd.mvc.view;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.IShape;
import isel.mpd.mvc.view.configdrawers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PainterFrame extends JFrame {
    public static int CANVAS_SIZE_X = 1024;
    public static int CANVAS_SIZE_Y = 600;

    private App app;
    private ConfigContext configContext;
    // components
    private JTextArea mouseHistory;
    private PaintPanel canvas;

    public  PainterFrame(App app){
        this.app = app;
        this.configContext = new ConfigContext();
        buildMenu();
        getContentPane().setBackground(Color.ORANGE);
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        app.setListener(canvas);
        pack();
        setResizable(false);
    }

    private  class PaintMouselistener extends  MouseAdapter {
        public void mousePressed(MouseEvent me) {
            canvas.setConfigMode(configContext.getConfigurator());
            configContext.start(me.getPoint());
            canvas.repaint();
        }

        public void mouseDragged(MouseEvent me) {
            configContext.setCurr(me.getPoint());
            canvas.repaint();
        }

        public void mouseReleased(MouseEvent me) {
            app.addShape(configContext.getConfigurator().createShape());
            canvas.setPaintMode();
        }
    }

    private void initComponents() {
        BorderLayout layout = new BorderLayout();
        Container container = getContentPane();
        container.setLayout(layout);
        canvas = new PaintPanel(CANVAS_SIZE_X, CANVAS_SIZE_Y, Color.WHITE);
        PaintMouselistener mlistener = new PaintMouselistener();
        canvas.addMouseMotionListener(mlistener);
        canvas.addMouseListener(mlistener);
        mouseHistory = new JTextArea(6, 80);
        mouseHistory.setBackground(new Color(240,240,240));
        mouseHistory.setEditable(false); // set textArea non-editable
        var scroll = new JScrollPane(mouseHistory);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        container.add(canvas, BorderLayout.CENTER);
        container.add(scroll, BorderLayout.SOUTH);
    }

    private void addItem(String name, JMenu menu) {
        var item = new JMenuItem(name);
        item.addActionListener(evt -> {
            configContext.setConfigurator(ConfigFactory.createConfigDrawer(name));
        });
        menu.add(item);
    }

    private void buildMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu creationSel = new JMenu("Add Shape");

        addItem(App.SHAPE_CMD_RECT, creationSel);
        addItem(App.SHAPE_CMD_TRIANGLE, creationSel);
        addItem(App.SHAPE_CMD_OVAL, creationSel);
        addItem(App.SHAPE_CMD_LINE, creationSel);
        addItem(App.SHAPE_CMD_CIRCLE, creationSel);
        addItem(App.SHAPE_CMD_GROUP, creationSel);

        JMenu configSel = new JMenu("Config");
        JMenuItem color = new JMenuItem("Color");
        color.addActionListener(evt -> {
            configContext.setColor(
                JColorChooser.showDialog(null,"Choose Color",
                configContext.getColor()));
        });
        configSel.add(color);

        menuBar.add(creationSel);
        menuBar.add(configSel);
        setJMenuBar(menuBar);
    }

    private void showShapes() {
        mouseHistory.append("Shapes\n");
        for(IShape s : app.getShapes()) {
            mouseHistory.append(s.getClass().getName() + "\n");
        }

        mouseHistory.append("Views\n");
        for(ShapeView sv : canvas.getViews()) {
            mouseHistory.append(sv.getClass().getName() + "\n");
        }
    }
}
