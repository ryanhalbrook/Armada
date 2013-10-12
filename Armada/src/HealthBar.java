import java.awt.Color;
import java.awt.Graphics;


public class HealthBar {

	protected DynamicElement de;
	
	public HealthBar(DynamicElement inDE){
		de=inDE;
	}
	
	public void draw(Graphics g, Rectangle viewRect){
		if(de.getAlliance()==1){
			g.setColor(Color.RED);
		}
		else if(de.getAlliance()==2){
			g.setColor(Color.BLUE);
		}
		
	    g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2) -1, de.getY()-viewRect.getY() - de.getHeight()/2 -1 -10, de.getWidth()+3, 6);
	    g.setColor(Color.BLACK);
		g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2), de.getY()-viewRect.getY() - de.getHeight()/2 -10, de.getWidth(), 3);
		if(de.getAlliance()==1){
			g.setColor(Color.RED);
		}
		else if(de.getAlliance()==2){
			g.setColor(Color.BLUE);
		}
		g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2), de.getY()-viewRect.getY() - de.getHeight()/2-10, (int)((double)de.getWidth()*(double)((double)de.getHull()/(double)de.getMaxHull())), 3);
		
		
		if(de.getAlliance()==1){
			g.setColor(new Color(250,100,100));
		}
		else if(de.getAlliance()==2){
			g.setColor(Color.CYAN);
		}
	    g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2) -1, de.getY()-viewRect.getY() - de.getHeight()/2 -1, de.getWidth()+3, 6);
	    g.setColor(Color.BLACK);
		g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2), de.getY()-viewRect.getY() - de.getHeight()/2, de.getWidth(), 3);
		if(de.getAlliance()==1){
			g.setColor(new Color(250,100,100));
		}
		else if(de.getAlliance()==2){
			g.setColor(Color.CYAN);
		}
		g.fillRect(de.getX()-viewRect.getX() - (de.getWidth()/2), de.getY()-viewRect.getY() - de.getHeight()/2, (int)((double)de.getWidth()*(double)((double)de.getEngine()/(double)de.getMaxEngine())), 3);
		
		
	}
	
}
