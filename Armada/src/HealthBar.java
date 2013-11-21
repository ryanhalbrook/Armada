package src; import src.view.*;
import java.awt.Color;
import java.awt.Graphics;

public class HealthBar {

	protected DynamicElement de;
	
	/**
	    Creates a health bar that can be drawn onto a Graphics object.
	    @param de The dynamic element that this health bar is associated with.
	*/
	public HealthBar(DynamicElement de) {
		this.de = de;
	}
	
	/**
	    Draws the health bar if it is inside of the given rectangular boundary
	    @param g The graphics context to draw the health bar into.
	*/
	public void draw(Graphics g, BoundingRectangle viewRect){
	
		if(!viewRect.isIn(de.getX(), de.getY()))return;
		if(de.getAlliance()==0){
			g.setColor(Color.GREEN);
		}
		else if(de.getAlliance()==1){
			g.setColor(Color.RED);
		}
		else if(de.getAlliance()==2){
			g.setColor(Color.BLUE);
		}
		
	    g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2) -1, de.getY()-viewRect.getY() - de.getHeight()/2 -1 -10, de.getWidth()+3, 4);
	    g.setColor(Color.BLACK);
		g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2), de.getY()-viewRect.getY() - de.getHeight()/2 -10, de.getWidth(), 2);
		if(de.getAlliance()==0){
			g.setColor(Color.GREEN);
		}
		else if(de.getAlliance()==1){
			g.setColor(Color.RED);
		}
		else if(de.getAlliance()==2){
			g.setColor(Color.BLUE);
		}
		g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2), de.getY()-viewRect.getY() - de.getHeight()/2-10, (int)((double)de.getWidth()*(double)((double)de.getHull()/(double)de.getMaxHull())), 2);
		
		if(de instanceof Planet)return;
		
		if(de.getAlliance()==0){
			return;
		}
		if(de.getAlliance()==1){
			g.setColor(new Color(250,100,0));
		}
		else if(de.getAlliance()==2){
			g.setColor(Color.CYAN);
		}
	    g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2) -1, de.getY()-viewRect.getY() - de.getHeight()/2 -3, de.getWidth()+3, 4);
	    g.setColor(Color.BLACK);
		g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2), de.getY()-viewRect.getY() - de.getHeight()/2-2, de.getWidth(), 2);
		if(de.getAlliance()==1){
			g.setColor(new Color(250,100,0));
		}
		else if(de.getAlliance()==2){
			g.setColor(Color.CYAN);
		}
		g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2), de.getY()-viewRect.getY() - de.getHeight()/2-2, (int)((double)de.getWidth()*(double)((double)de.getEngine()/(double)de.getMaxEngine())), 2);
		
	}
	
}
