package view.controller; 
import game.ApplicationManager;

import java.awt.*;
import java.awt.event.*;

import view.BoundingRectangle;
import view.DynamicSize;
import view.LobbyLayer;

public class LobbyController extends ViewLayerController {
    ApplicationManager am = null;
    public LobbyController(ApplicationManager am, DynamicSize dsb) {
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

