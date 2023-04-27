package isel.mpd.mvc.utils;

import isel.mpd.mvc.app.App;
import isel.mpd.mvc.model.shapes.IShape;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SvgSerializer {
    private static void canvasToSvg(Iterable<IShape> shapes, int w, int h, String svgFile) throws IOException {
        try(FileWriter fw = new FileWriter(svgFile)) {
            fw.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fw.append("<svg width=\"" + w + "\" height=\"" + h + "\" xmlns=\"http://www.w3.org/2000/svg\">\n");
            for (var s : shapes) fw.append(s.toSvg());
            fw.append("</svg>");
        }
    }

    public static void exportToSvg(App app, int w, int h) {
        JFileChooser chooser = new JFileChooser("c:");
        int x = chooser.showDialog(null, "Export");

        if(x == JFileChooser.APPROVE_OPTION) {
            try { //create file
                String filePath = chooser.getSelectedFile().getAbsolutePath();
                File file = new File(filePath);
                file.createNewFile();
                canvasToSvg(app.getShapes(), w, h, filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
