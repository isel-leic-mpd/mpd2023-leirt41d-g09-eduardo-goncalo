package isel.mpd.painter_mvc.view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PaintPanel extends JComponent {

    public PaintPanel(int w, int h, Color backColor) {
        setPreferredSize(new Dimension(w,h));
        setBackground(backColor);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gc = (Graphics2D)  g;

        gc.setColor(getBackground());

        var w = getWidth();
        var h = getHeight();

        gc.fillRect(0,0, w, h);

        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        gc.setColor(Color.BLACK);
        gc.drawString("Put here your shapes!", getWidth()/2 - 100, getHeight()/2);
    }
}
