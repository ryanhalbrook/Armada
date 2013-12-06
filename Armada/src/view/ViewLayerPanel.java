package view;
import java.awt.event.*;
import javax.swing.*;
import view.controller.ViewLayerController;
import java.awt.*;

/**
    A panel that displays the ViewLayer hierarchy of a ViewLayerController.
*/
public class ViewLayerPanel extends JPanel implements DynamicSize, ActionListener {
    private ViewLayerController viewLayerController = null;
    private boolean antialiasingEnabled = false;
    private Timer refreshTimer = null;
    
    /**
    Creates a regular instance of a ViewLayerPanel without a ViewLayerController.
    One must be added later to display anything.
    */
    public ViewLayerPanel() {
        super();
    }
    
    /**
    Creates an instance of a ViewLayerPanel with the given ViewLayerController.
    */
    public ViewLayerPanel(ViewLayerController vlc) {
        super();
        setViewLayerController(vlc);
    }
    /**
    Called by the refresh timer.
    */
    public void actionPerformed(ActionEvent evt) {
        viewLayerController.actionPerformed(evt);
        repaint();
    }
    
    /**
    Sets the ViewLayerController whose ViewLayer hierarchy is to be presented by this
    ViewLayerPanel.
    */
    public void setViewLayerController(ViewLayerController vlc) {
        viewLayerController = vlc;
        
        if (viewLayerController != null) {
            this.addMouseListener(viewLayerController);
            this.addMouseMotionListener(viewLayerController);
            this.addKeyListener(viewLayerController);
            this.addComponentListener(viewLayerController);
            refreshTimer = new Timer(30, this);
            refreshTimer.start();
        }
    }
    
    /**
    Set whether drawing is performed with antialiasing enabled.
    */
    public void setAntialiasingEnabled(boolean enabled) {
        antialiasingEnabled = enabled;
    }
    
    /**
    Renders the ViewLayer of the ViewLayerController onto the g graphics object.
    */
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

