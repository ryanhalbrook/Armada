import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class BackgroundImageViewLayer extends ViewLayer {
    BufferedImage img = null;
    BoundingRectangle viewRegion;
    
    public BackgroundImageViewLayer(BoundingRectangle bounds, BoundingRectangle viewRegion, BufferedImage img) {
        super(bounds);
        this.img = img;
        this.viewRegion = viewRegion;
    }
    
    public void draw(Graphics g) {
	    // Draw the background image.
	    if (img != null) {
	        g.drawImage(img, -viewRegion.getX(), -viewRegion.getY(), null);
	    }
    }
}