package src.view;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class ViewLayerPanel extends JPanel implements DynamicSize, ActionListener {
    private ViewLayerController viewLayerController = null;
    private boolean antialiasingEnabled = false;
    private Timer refreshTimer = null;
    
    
    public ViewLayerPanel() {
        super();
    }
    
    public ViewLayerPanel(ViewLayerController vlc) {
        super();
        setViewLayerController(vlc);
    }
    
    public void actionPerformed(ActionEvent evt) {
        viewLayerController.actionPerformed(evt);
        repaint();
    }
    
    public void setViewLayerController(ViewLayerController vlc) {
        viewLayerController = vlc;
        
        if (viewLayerController != null) {
            this.addMouseListener(viewLayerController);
            this.addMouseMotionListener(viewLayerController);
            this.addKeyListener(viewLayerController);
            refreshTimer = new Timer(30, this);
            refreshTimer.start();
        }
    }
    
    public void setAntialiasingEnabled(boolean enabled) {
        antialiasingEnabled = enabled;
    }
    
    public void paintComponent(Graphics g) {
	    if (antialiasingEnabled) {
	        Graphics2D g2d = (Graphics2D)g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    }
	
	    // Draw a black background.
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    
	    if (viewLayerController != null) {
	        viewLayerController.getViewLayer().draw(g);
	    }
	}
    

}

