package isel.mpd.painter_mvc.view;

import isel.mpd.painter_mvc.App;

import javax.swing.*;
import java.awt.*;

public class PainterFrame extends JFrame {
    public static int CANVAS_SIZE_X = 1024;
    public static int CANVAS_SIZE_Y = 600;

    private App app;

    // components
    private JTextArea mouseHistory;
    private PaintPanel canvas;

    public  PainterFrame(App app){
        this.app = app;

        setTitle("Painter");
        buildMenu();
        getContentPane().setBackground(Color.ORANGE);
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
    }

    private void initComponents() {
        BorderLayout layout = new BorderLayout();
        Container container = getContentPane();
        container.setLayout(layout);
        canvas = new PaintPanel(CANVAS_SIZE_X, CANVAS_SIZE_Y, Color.WHITE);

        mouseHistory = new JTextArea(6, 80);
        mouseHistory.setBackground(new Color(240,240,240));
        mouseHistory.setEditable(false); // set textArea non-editable
        var scroll = new JScrollPane(mouseHistory);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        mouseHistory.append("Put your messages here!");
        container.add(canvas, BorderLayout.CENTER);
        container.add(scroll, BorderLayout.SOUTH);
    }

    private void buildMenu() {
        // TODO
    }
}
