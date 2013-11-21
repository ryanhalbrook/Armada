package src.view;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ViewLayerController implements KeyListener, MouseListener, MouseMotionListener, ActionListener {
    protected ViewLayer viewLayer = null;
    private long lastTime;
    
    public void actionPerformed(ActionEvent evt) {
        if (lastTime == 0) {
		    lastTime = new GregorianCalendar().getTimeInMillis();
		}
	    long newTime = new GregorianCalendar().getTimeInMillis();
	    if (viewLayer != null)
        viewLayer.refresh(lastTime, newTime);
        refresh(lastTime, newTime);
    }
    public ViewLayerController() {
    
    }
    public ViewLayerController(ViewLayer vl) {
        this.viewLayer = vl;
    }
    
    protected void refresh(long previousTime, long currentTime) {
        lastTime = currentTime;
    }
    public void setViewLayer(ViewLayer vl) {
        this.viewLayer = vl;
    }

    
    public ViewLayer getViewLayer() {
        return viewLayer;
    }
    
    public void mouseClicked(MouseEvent evt) {
        
    }
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) {}
	public void keyTyped(KeyEvent evt) {}
	public void keyPressed(KeyEvent evt) {
	    
	}
	public void keyReleased(KeyEvent evt) {}
	public void mouseDragged(MouseEvent evt) {}
	public void mouseMoved(MouseEvent evt) {}
	public void mousePressed(MouseEvent evt) {
	    if (viewLayer != null)
	    viewLayer.click(evt.getX(), evt.getY());
	}
}