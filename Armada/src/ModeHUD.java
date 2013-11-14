import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;


public class ModeHUD extends HUD{

    private String[] modes = {"Move (1)", "Hull (2)", "Eng (3)", "Dock(4)", "Move F.(5)"};
    private Color[] modeColors = {Color.GREEN, new Color(250,100,0), Color.YELLOW, Color.MAGENTA, new Color(0.0f, 0.9f, 1.0f)};
	private float phase = -1.0f;
	private int lastMode = 1;
	private int transitionMode = -1;
	private long startTime;
	private long rAnimationStart = -1;
	private float rPhase = 0.0f;
	private boolean rReverse = false;
	
	Grid grid = null;
	
	static final int ANIMATION_TIME = 100;
	static final int RETICLE_ANIMATION_TIME = 1000;
	
	public ModeHUD(Grid gr, GameController gc, Position p){
		super(new BoundingRectangle(5,45,500,40),gc, p);
		this.grid = gr;
	}
	
	public void refresh(long previousTime, long currentTime) {
	    /*
	    if (lastMode == -1) {
	        startTime = currentTime;
	        lastMode = grid.getMode();
	        return;
	    }
	    */
	    if (rAnimationStart == -1) {
	        rAnimationStart = currentTime;
	    } else {
	        long delta = currentTime - rAnimationStart;
	        rPhase = (delta * 1.0f) / RETICLE_ANIMATION_TIME;
	        if (rReverse) {
	            rPhase = 1-rPhase;
	        }
	        if (rPhase > 1.0f) {
	            //System.out.println("Greater");
	            rReverse = true;
	            rAnimationStart = currentTime;
	        }
	        if (rPhase < 0.0f) {
	            //System.out.println("Lesser");
	            rReverse = false;
	            rAnimationStart = currentTime;
	        }
	        //System.out.println(rPhase);
	    }
	    
	    if (lastMode != grid.getMode() && phase < -0.5f) {
	        // Start animation.
	        //System.out.println("Starting Animation");
	        startTime = currentTime;
	        transitionMode = grid.getMode();
	        phase = 0.0f;
	    }
	    if (phase > -0.5f && phase <= 1.0f) {
	        //System.out.println("Time delta: " + (currentTime - startTime));
	        phase = (currentTime - startTime) * 1.0f / ANIMATION_TIME;
	        //System.out.println("Phase " + phase);
	    }
	    if (phase > 0.99f) {
	        phase = -1.0f;
	        lastMode = grid.getMode();    
	        startTime = -1;  
	    }
	}
	
	public void draw(Graphics g){
	    int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();
	    
	    
		if(grid == null)return;
		this.updateLocation();
		//System.out.println("x, y, width, height: " + x + ", " + y + ", " + width + ", " + height);
		g.setColor(new Color(0.0f, 0.1f, 0.1f, 0.5f));
		//g.fillRect(x, y, width, height);
		int selection = grid.getMode();
		g.setColor(Color.WHITE);
		int selectionWidth = (int)(width / 6.0f);
		for (int i = 0; i < 5; i++) {
		    
		    g.drawString(modes[i], i*selectionWidth+5, y+height-15);
		}
		//System.out.println("Mode difference: " + (lastMode - grid.getMode()));
		//System.out.println("Selection width, phase" + selectionWidth + ", " + phase);
		float dx = (lastMode - grid.getMode()) * phase * selectionWidth;
		dx = -dx;
		//System.out.println("dx: " + dx);
		selection = lastMode;
		int startX = (selection-1) * selectionWidth + (int)dx;
	    g.setColor(modeColors[grid.getMode()-1]);
		g.drawRect(startX, y+1, selectionWidth, height-1);
		
		if (grid.getActiveE()!=null && grid.getActiveE() instanceof Ship) {
			Graphics2D g2d = (Graphics2D)g;
		    
				    
		    int shipX = grid.getActiveE().getX();
		    int shipY = grid.getActiveE().getY();
		    Stroke s = g2d.getStroke();
		    float array[] = {30.0f};
		    g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 5.0f, array, 0.0f));
		    g2d.setColor(Color.WHITE);
		    if(grid.getMode()==1 && grid.getActiveE().canMovePath2(grid.getCurrentX() + grid.getViewRegion().getX(),grid.getCurrentY() + grid.getViewRegion().getY(),grid.getDelements()) && grid.getActiveE().canMove()){
		    	g.setColor(Color.GREEN);
		    }
		    else if(grid.getMode() == 1){
		    	g.setColor(Color.RED);
		    }
		    else if((grid.getMode() == 2 ) && grid.getActiveE().withinRange(grid.getCurrentX() + grid.getViewRegion().getX(),grid.getCurrentY() + grid.getViewRegion().getY()) && grid.getActiveE().canAttack()){
		    	g.setColor(new Color(250,100,0));
		    }
		    else if((grid.getMode() == 2 )){
		    	g.setColor(Color.RED);
		    }
		    else if(( grid.getMode() == 3) && grid.getActiveE().withinRange(grid.getCurrentX() + grid.getViewRegion().getX(),grid.getCurrentY() + grid.getViewRegion().getY()) && grid.getActiveE().canAttack()){
		    	g.setColor(Color.YELLOW);
		    }
		    else if(( grid.getMode() == 3)){
		    	g.setColor(Color.RED);
		    }else if(( grid.getMode() == 4) && grid.getActiveE().distance(grid.getCurrentX() + grid.getViewRegion().getX(), (grid.getCurrentY()) + grid.getViewRegion().getY()) < 100){
		    	g.setColor(Color.MAGENTA);
		    }
		    else if(( grid.getMode() == 4)){
		    	g.setColor(Color.RED);
		    	//g.drawString("Out of docking range or already docked", x, y+g.getFontMetrics().getHeight()*2);
		    }
		    else if (( grid.getMode() == 5)) {
		        g.setColor(new Color(0.0f, 0.9f, 1.0f));
		    }
		    //if (grid.getCurrentX() == 0 && grid.getCurrentY() == 0) System.out.println("Current x,y = 0,0");
		    if(grid.getCurrentX()!=0||grid.getCurrentY()!=0 || true){
		        if (grid.getMode() != 5) {
			        g.drawLine(shipX-grid.getViewRegion().getX(), shipY-grid.getViewRegion().getY(), grid.getCurrentX(), grid.getCurrentY());
			        int baseRadius = 20;
			        int radius = (int)(baseRadius*(rPhase));
			        //radius = (int)((gc.getActiveE().getWidth() + gc.getActiveE().getHeight()) / 2.0f);
			        g.drawOval(grid.getCurrentX()-baseRadius, grid.getCurrentY()-baseRadius, baseRadius*2, baseRadius *2 );
			        g.drawOval(grid.getCurrentX()-radius, grid.getCurrentY()-radius, radius*2, radius*2);
			    } else {
			        int counter = 0;
			        for (DynamicElement d : grid.getDelements()) {
			            if (d instanceof Ship && d.getAlliance()==grid.getTurn() && d.withinMovement(grid.getCurrentX(),grid.getCurrentY()) && d.isTargetable()) {
			                shipX = d.getX();
			                shipY = d.getY();
			                g.drawLine(shipX-grid.getViewRegion().getX(), shipY-grid.getViewRegion().getY(), grid.getCurrentX()-counter*50, grid.getCurrentY()-counter*50);
			                int radius = 20;
			                g.drawOval(grid.getCurrentX()-counter*50-radius, grid.getCurrentY()-counter*50-radius, radius*2, radius*2);
			                counter++;
			            }
			            
			        }
			    }
		    }
		    g2d.setStroke(s);
		    /*
		    switch(grid.getMode()){
		    case 0:
		    	g.drawString("Mode: No Move Selected", x, y+g.getFontMetrics().getHeight());
		    	break;
		    case 1:
		    	g.drawString("Mode: Move", x, y+g.getFontMetrics().getHeight());
		    	break;
		    case 2:
		    	g.drawString("Mode: Attack Hull", x, y+g.getFontMetrics().getHeight());
		    	break;
		    case 3:
		    	g.drawString("Mode: Attack Engine", x, y+g.getFontMetrics().getHeight());
		    	break;
		    case 4:
		    	g.drawString("Mode: Docking", x, y+g.getFontMetrics().getHeight());
		    	break;
		    case 5:
		        g.drawString("Mode: Fleet Move", x, y+g.getFontMetrics().getHeight());
		        break;
		    }  
		    */
		}
		else if(grid.getActiveE() != null && grid.getActiveE() instanceof Planet){
			Planet p = (Planet)grid.getActiveE();
			switch(grid.getActiveE().getAlliance()){
				case 1:
					g.setColor(Color.RED);
					break;
				case 2:
					g.setColor(Color.BLUE);
					break;
				default:
					g.setColor(Color.WHITE);
					break;
			}
			g.drawOval(p.getX()-p.getWidth()/2 - grid.getViewRegion().getX(), p.getY()-p.getHeight()/2 - grid.getViewRegion().getY(), p.getWidth(), p.getHeight());
		}
		else{
			g.setColor(Color.WHITE);
			//g.drawString("Nothing Selected", x, y+g.getFontMetrics().getHeight());
		}
	}

}

/*
grid.distance(grid.getActiveE(), grid.getCurrentX() + grid.getViewRegion().getX(), (grid.getCurrentY()) + grid.getViewRegion().getY()) < 100)
*/
