package src; import src.view.*;
import java.awt.*;
import java.awt.event.*;

public class LobbyController extends ViewLayerController {
    ApplicationManager am = null;
    public LobbyController(ApplicationManager am, DynamicSizeBroadcast dsb) {
        this.am = am;
        this.viewLayer = new LobbyLayer( new BoundingRectangle(0, 0, dsb));
    }
    
    public void keyPressed(KeyEvent evt) {
	    int keycode = evt.getKeyCode();
	    if (keycode == KeyEvent.VK_ESCAPE) {
	        am.shutdownConnection();
	    }   
	}
}

