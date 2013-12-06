package view.controller;

import java.awt.event.*;
import javax.swing.*;
import view.ViewLayer;
import java.awt.*;
import java.util.*;

/**
The ViewLayerController is a class that holds a reference to the top level ViewLayer in the
view hierarchy. It handles events including time events, and user interaction events. This
class, itself doesn't do much, but this class should typically be subclassed in order to
override event callback methods.
*/
public class ViewLayerController implements KeyListener, MouseListener, MouseMotionListener, ActionListener, ComponentListener {
    protected ViewLayer viewLayer = null;
    private long lastTime;
    
    /**
    Called by the refresh timer. Is useful to drive animations.
    */
    public void actionPerformed(ActionEvent evt) {
        if (lastTime == 0) {
		    lastTime = new GregorianCalendar().getTimeInMillis();
		}
	    long newTime = new GregorianCalendar().getTimeInMillis();
	    if (viewLayer != null)
        viewLayer.refresh(lastTime, newTime);
        refresh(lastTime, newTime);
    }
    
    /**
    Creates a ViewLayerController instance.
    */
    public ViewLayerController() {
    
    }
    
    /**
    Creates a ViewLayerController instance that displays a ViewLayer's hierarchy.
    */
    public ViewLayerController(ViewLayer vl) {
        this.viewLayer = vl;
    }
    
    protected void refresh(long previousTime, long currentTime) {
        lastTime = currentTime;
    }
    
    /**
    Sets the ViewLayer that this ViewLayerController should display.
    */
    public void setViewLayer(ViewLayer vl) {
        this.viewLayer = vl;
    }

    /**
    Returns the ViewLayer that this controller is displaying.
    */
    public ViewLayer getViewLayer() {
        return viewLayer;
    }
    
    /*
    The following 4 methods are called when an important state change occurs to the
    component. These can be overridden to react to these changes.
    */
    public void componentShown(ComponentEvent evt) {}
    public void componentResized(ComponentEvent evt) {}
    public void componentMoved(ComponentEvent evt) {}
    public void componentHidden(ComponentEvent evt) {}
    
    /*
    The following 4 methods are called when a mouse event occurs. These can be overridden
    to react to mouse events.
    */
    public void mouseClicked(MouseEvent evt) {}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) {}
	
	/*
    The following 3 methods are called when a key event occurs. These can be overridden
    to react to key events.
    */
	public void keyTyped(KeyEvent evt) {}
	public void keyPressed(KeyEvent evt) {}
	public void keyReleased(KeyEvent evt) {}
	
	/*
    The following 2 methods are called when a mouse event occurs. These can be overridden
    to react to mouse events.
    */
	public void mouseDragged(MouseEvent evt) {}
	public void mouseMoved(MouseEvent evt) {}
	
	/*
	Gets called when the mouse is pressed. Forwards to the ViewLayer.
	*/
	public void mousePressed(MouseEvent evt) {
	    if (viewLayer != null)
	    viewLayer.click(evt.getX(), evt.getY());
	}
}