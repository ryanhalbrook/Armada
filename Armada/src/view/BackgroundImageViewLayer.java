package view;

import game.ArmadaEngine;

import java.awt.*;
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
	    int imageRows = (int)((ArmadaEngine.getGridHeight() / img.getHeight()) * 1.0f);
	    int imageColumns = (int)((ArmadaEngine.getGridWidth() / img.getWidth()) * 1.0f);
	    //System.out.println("Images: " + imageRows + " x " + imageColumns);
	    if (img != null) {
	        for (int i = 0; i < imageRows; i++) {
	            for (int j = 0; j < imageColumns; j++) {
	                g.drawImage(img, -viewRegion.getX() + i * img.getWidth(), -viewRegion.getY() + j * img.getHeight(), null);
	            }
	    }
	        //g.drawImage(img, -viewRegion.getX(), -viewRegion.getY(), null);
	        //g.drawImage(img, -viewRegion.getX() + img.getWidth(), -viewRegion.getY(), null);
	    }
    }
}