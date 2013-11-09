import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class ViewLayerPanel extends JPanel {
    private ViewLayerController viewLayerController = null;
    private boolean antialiasingEnabled = false;
    private Timer refreshTimer = null;
    
    public ViewLayerPanel() {
        
    }
    
    public ViewLayerPanel(ViewLayerController vlc) {
        super();
        viewLayerController = vlc;
        if (viewLayerController != null) {
            this.addMouseListener(viewLayerController);
            this.addMouseMotionListener(viewLayerController);
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

