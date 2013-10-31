import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;


public class ModeHUD extends HUD{

	
	
	public ModeHUD(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public ModeHUD(Grid gr, int t){
		super(5,45,200,40,gr, t);
	}
	
	public void draw(Graphics g){
	    int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();
		if(grid == null)return;
		this.updateLocation();
		if (grid.getActiveE()!=null) {
			Graphics2D g2d = (Graphics2D)g;
		    
				    
		    int shipX = grid.getActiveE().getX();
		    int shipY = grid.getActiveE().getY();
		    Stroke s = g2d.getStroke();
		    float array[] = {10.0f};
		    g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, array, 0.0f));
		    g2d.setColor(Color.WHITE);
		    if(grid.getMode()==1 && grid.getActiveE().canMovePath2(grid.getCurrentX() + grid.getViewRegion().getX(),grid.getCurrentY() + grid.getViewRegion().getY(),grid.getDelements()) && grid.getActiveE().canMove()){
		    	g.setColor(Color.GREEN);
		    }
		    else if(grid.getMode() == 1){
		    	g.setColor(Color.RED);
		    	g.drawString("Out of range or colision detected", x, y+g.getFontMetrics().getHeight()*2);
		    }
		    else if((grid.getMode() == 2 ) && grid.getActiveE().withinRange(grid.getCurrentX() + grid.getViewRegion().getX(),grid.getCurrentY() + grid.getViewRegion().getY()) && grid.getActiveE().canAttack()){
		    	g.setColor(new Color(250,100,0));
		    }
		    else if((grid.getMode() == 2 )){
		    	g.setColor(Color.RED);
		    	g.drawString("Out of range or out of shots this turn", x, y+g.getFontMetrics().getHeight()*2);
		    }
		    else if(( grid.getMode() == 3) && grid.getActiveE().withinRange(grid.getCurrentX() + grid.getViewRegion().getX(),grid.getCurrentY() + grid.getViewRegion().getY()) && grid.getActiveE().canAttack()){
		    	g.setColor(Color.YELLOW);
		    }
		    else if(( grid.getMode() == 3)){
		    	g.setColor(Color.RED);
		    	g.drawString("Out of range or out of shots this turn", x, y+g.getFontMetrics().getHeight()*2);
		    }else if(( grid.getMode() == 4) && grid.distance(grid.getActiveE(), grid.getCurrentX() + grid.getViewRegion().getX(), (grid.getCurrentY()) + grid.getViewRegion().getY()) < 100){
		    	g.setColor(Color.MAGENTA);
		    }
		    else if(( grid.getMode() == 4)){
		    	g.setColor(Color.RED);
		    	g.drawString("Out of docking range or already docked", x, y+g.getFontMetrics().getHeight()*2);
		    }
		    g.drawLine(shipX-grid.getViewRegion().getX(), shipY-grid.getViewRegion().getY(), grid.getCurrentX(), grid.getCurrentY());
		    int radius = 20;
		    
		    g.drawOval(grid.getCurrentX()-radius, grid.getCurrentY()-radius, radius*2, radius*2);
		    g2d.setStroke(s);
		    
		    switch(grid.getMode()){
		    case 0:
		    	g.drawString("No Move Selected", x, y+g.getFontMetrics().getHeight());
		    	break;
		    case 1:
		    	g.drawString("Move Initiated", x, y+g.getFontMetrics().getHeight());
		    	break;
		    case 2:
		    	g.drawString("Attacking Hull", x, y+g.getFontMetrics().getHeight());
		    	break;
		    case 3:
		    	g.drawString("Attacking Engine", x, y+g.getFontMetrics().getHeight());
		    	break;
		    case 4:
		    	g.drawString("Docking Initiated", x, y+g.getFontMetrics().getHeight());
		    	break;
		    }  
		}
		else{
			g.setColor(Color.WHITE);
			g.drawString("No Ship Selected", x, y+g.getFontMetrics().getHeight());
		}
	}

}
