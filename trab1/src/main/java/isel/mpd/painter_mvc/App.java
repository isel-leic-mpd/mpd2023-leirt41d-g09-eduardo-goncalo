package isel.mpd.painter_mvc;

import isel.mpd.painter_mvc.view.PainterFrame;

public class App {
    public static void main(String[] args) {
        
		App app = new App();

        PainterFrame painter = new PainterFrame(app);

        painter.setVisible(true);
    }
}
