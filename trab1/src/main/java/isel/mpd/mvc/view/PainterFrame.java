package isel.mpd.mvc.view;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.commands.CommandFactory;
import isel.mpd.mvc.model.commands.MoveCmd;
import isel.mpd.mvc.model.commands.removeCmd;
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
    private int flag = 0;

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
            if(configContext.getCommand() instanceof MoveCmd)
                ((MoveCmd) configContext.getCommand()).shape_select();
            if(configContext.getCommand() instanceof removeCmd)
                ((removeCmd) configContext.getCommand()).shape_select();
            canvas.repaint();
        }

        public void mouseDragged(MouseEvent me) {
            flag = 1;
            configContext.setCurr(me.getPoint());
            if(configContext.getCommand() instanceof MoveCmd)
                ((MoveCmd) configContext.getCommand()).drag();
            canvas.repaint();
        }

        public void mouseReleased(MouseEvent me) {
            canvas.setPaintMode();
            canvas.setConfigMode(configContext.getConfigurator());
            if (configContext.getCommand() != null && flag == 1){
                configContext.getCommand().execute();
                flag = 0;
            }
            canvas.repaint();
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
            if(!name.equals(app.CMD_MOVE)&& !name.equals(app.CMD_REMOVE) ){
                configContext.setConfigurator(ConfigFactory.createConfigDrawer(name));}
            configContext.setCommand(CommandFactory.createCommand(name, app, configContext));
        });
        menu.add(item);
    }

    private void buildMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu shapeMenu = new JMenu("Shape");
        JMenu creationSel = new JMenu(App.CMD_ADD);
        JMenuItem move = new JMenuItem(App.CMD_MOVE);
        JMenuItem remove = new JMenuItem(App.CMD_REMOVE);

        addItem(App.SHAPE_CMD_RECT, creationSel);
        addItem(App.SHAPE_CMD_TRIANGLE, creationSel);
        addItem(App.SHAPE_CMD_OVAL, creationSel);
        addItem(App.SHAPE_CMD_LINE, creationSel);
        addItem(App.SHAPE_CMD_CIRCLE, creationSel);

        shapeMenu.add(creationSel);
        addItem(App.CMD_MOVE, shapeMenu);
        addItem(App.CMD_REMOVE, shapeMenu);
        addItem(App.SHAPE_CMD_GROUP, shapeMenu);

        JMenuItem undo = new JMenuItem(App.CMD_UNDO);
        undo.addActionListener(evt -> {
            if (configContext.getCommand() != null)
                configContext.getCommand().undo();
        });

        shapeMenu.add(undo);

        JMenu configSel = new JMenu("Config");
        JMenuItem color = new JMenuItem("Color");
        color.addActionListener(evt -> {
            configContext.setColor(
                JColorChooser.showDialog(null,"Choose Color",
                configContext.getColor()));
        });
        configSel.add(color);

        menuBar.add(shapeMenu);
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
