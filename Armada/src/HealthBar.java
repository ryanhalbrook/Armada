import java.awt.Color;
import java.awt.Graphics;


public class HealthBar {

	protected DynamicElement de;
	
	public HealthBar(DynamicElement inDE){
		de=inDE;
	}
	
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
