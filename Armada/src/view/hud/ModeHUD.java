package view.hud; 
import view.*;
import view.controller.GameController;
import view.hud.HUD.Position;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import element.DynamicElement;
import element.planet.Planet;
import element.ship.Ship;

import java.awt.Font;
import java.awt.FontMetrics;


public class ModeHUD extends HUD{

    private String[] modes = {"Move (1)", "Hull (2)", "Eng (3)", "Dock (4)", "Spawn (5)"};
    private Color[] modeColors = {Color.GREEN, new Color(250,100,0), Color.YELLOW, Color.MAGENTA, new Color(0.0f, 0.9f, 1.0f)};
	private float phase = -1.0f;
	private int lastMode = 1;
	private int transitionMode = -1;
	private long startTime;
	private long rAnimationStart = -1;
	private float rPhase = 0.0f;
	private boolean rReverse = false,displayLine=true;
	
	Grid grid = null;
	
	static final int ANIMATION_TIME = 100;
	static final int RETICLE_ANIMATION_TIME = 1000;
	
	public ModeHUD(Grid gr, GameController gc, Position p){
		super(new BoundingRectangle(5,45,400,35),gc, p);
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
	
	public boolean click(int inX, int inY){
		if(r.isIn(inX, inY)){
		    //System.out.println("You clicked the Turn HUD");
		    int selectionWidth = (int)(r.getWidth() / 5.0f);   
		    int mode = (inX-r.getX()+selectionWidth) / selectionWidth;
		    //System.out.println("Mode: " + mode);
		    if (mode > 0 && mode < 6) 
		        grid.setMode(mode);
		    return true;
		}
		return false;
	}
	
	public void draw(Graphics g){
	    int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();
	    
	    
		if(grid == null)return;
		this.updateLocation();
		//System.out.println("x, y, width, height: " + x + ", " + y + ", " + width + ", " + height);
		g.setColor(new Color(0.9f, 0.9f, 0.9f, 0.3f));
		//g.fillRect(x, y, width, height);
		int selection = grid.getMode();
		
		
		g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		
		int selectionWidth = (int)(width / 5.0f);   
		
		//System.out.println("Mode difference: " + (lastMode - grid.getMode()));
		//System.out.println("Selection width, phase" + selectionWidth + ", " + phase);
		float dx = (lastMode - grid.getMode()) * phase * selectionWidth;
		dx = -dx;
		//System.out.println("dx: " + dx);
		selection = lastMode;
		int startX = (selection-1) * selectionWidth + (int)dx;
		
	    //g.setColor(modeColors[grid.getMode()-1]);
		g.fillRect(x+startX, y+1, selectionWidth, height-1);
		
		g.setFont(new Font(null, Font.PLAIN, 14));
		FontMetrics fm = g.getFontMetrics();
		int fontHeight = fm.getHeight();
		int fontWidth = 0;
		for (int i = 0; i < 5; i++) {
		    g.setColor(Color.BLACK);
		    fontWidth = fm.stringWidth(modes[i]);
		    g.drawString(modes[i], (int)(x+i*selectionWidth + (selectionWidth/2.0 - fontWidth/2.0)), (int)(y+height/2.0+fontHeight/2.0));
		}
		int clear = 20;
		if (grid.getActiveE()!=null && grid.getActiveE() instanceof Ship) {
			Graphics2D g2d = (Graphics2D)g;
		    
				    
		    int shipX = grid.getActiveE().getX();
		    int shipY = grid.getActiveE().getY();
		    Stroke s = g2d.getStroke();
		    float array[] = {10.0f};
		    g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 5.0f, array, 0.0f));
		    g2d.setColor(Color.WHITE);
		    if(grid.getMode()==1 && grid.getActiveE().canMovePath2(grid.getCurrentX() + grid.getViewRegion().getX(),grid.getCurrentY() + grid.getViewRegion().getY(),grid.getDelements()) && grid.getActiveE().canMove()){
		    	g.setColor(Color.GREEN);
		    	g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), clear));
		    	g.fillOval(grid.getActiveE().getX()-grid.getActiveE().getMovementLeft() - grid.getViewRegion().getX(), grid.getActiveE().getY()-grid.getActiveE().getMovementLeft() - grid.getViewRegion().getY(), grid.getActiveE().getMovementLeft()*2, grid.getActiveE().getMovementLeft()*2);

		    }
		    else if(grid.getMode() == 1){
		    	g.setColor(Color.RED);
		    	g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), clear));
		    	g.fillOval(grid.getActiveE().getX()-grid.getActiveE().getMovementLeft() - grid.getViewRegion().getX(), grid.getActiveE().getY()-grid.getActiveE().getMovementLeft() - grid.getViewRegion().getY(), grid.getActiveE().getMovementLeft()*2, grid.getActiveE().getMovementLeft()*2);

		    }
		    else if((grid.getMode() == 2 ) && grid.getActiveE().withinRange(grid.getCurrentX() + grid.getViewRegion().getX(),grid.getCurrentY() + grid.getViewRegion().getY()) && grid.getActiveE().canAttack()){
		    	g.setColor(new Color(250,100,0));
		    	g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), clear));
		    	g.fillOval(grid.getActiveE().getX()-grid.getActiveE().getRange() - grid.getViewRegion().getX(), grid.getActiveE().getY()-grid.getActiveE().getRange() - grid.getViewRegion().getY(), grid.getActiveE().getRange()*2, grid.getActiveE().getRange()*2);
		    }
		    else if((grid.getMode() == 2 )){
		    	g.setColor(Color.RED);
		    	g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), clear));
		    	g.fillOval(grid.getActiveE().getX()-grid.getActiveE().getRange() - grid.getViewRegion().getX(), grid.getActiveE().getY()-grid.getActiveE().getRange() - grid.getViewRegion().getY(), grid.getActiveE().getRange()*2, grid.getActiveE().getRange()*2);
		    }
		    else if(( grid.getMode() == 3) && grid.getActiveE().withinRange(grid.getCurrentX() + grid.getViewRegion().getX(),grid.getCurrentY() + grid.getViewRegion().getY()) && grid.getActiveE().canAttack()){
		    	g.setColor(Color.YELLOW);
		    	g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), clear));
		    	g.fillOval(grid.getActiveE().getX()-grid.getActiveE().getRange() - grid.getViewRegion().getX(), grid.getActiveE().getY()-grid.getActiveE().getRange() - grid.getViewRegion().getY(), grid.getActiveE().getRange()*2, grid.getActiveE().getRange()*2);

		    }
		    else if(( grid.getMode() == 3)){
		    	g.setColor(Color.RED);
		    	g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), clear));
		    	g.fillOval(grid.getActiveE().getX()-grid.getActiveE().getRange() - grid.getViewRegion().getX(), grid.getActiveE().getY()-grid.getActiveE().getRange() - grid.getViewRegion().getY(), grid.getActiveE().getRange()*2, grid.getActiveE().getRange()*2);

		    }else if(( grid.getMode() == 4) && grid.getActiveE().distance(grid.getCurrentX() + grid.getViewRegion().getX(), (grid.getCurrentY()) + grid.getViewRegion().getY()) < Ship.getDockRange()){
		    	g.setColor(Color.MAGENTA);
		    	g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), clear));
		    	g.fillOval(grid.getActiveE().getX()-Ship.getDockRange() - grid.getViewRegion().getX(), grid.getActiveE().getY()-Ship.getDockRange()  - grid.getViewRegion().getY(), Ship.getDockRange() *2, Ship.getDockRange() *2);
		    }
		    else if(( grid.getMode() == 4)){
		    	g.setColor(Color.RED);
		    	g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), clear));
		    	g.fillOval(grid.getActiveE().getX()-Ship.getDockRange() - grid.getViewRegion().getX(), grid.getActiveE().getY()-Ship.getDockRange()  - grid.getViewRegion().getY(), Ship.getDockRange() *2, Ship.getDockRange() *2);
		    }
		    else if (( grid.getMode() == 5)) {
		        //g.setColor(new Color(0.0f, 0.9f, 1.0f));
		    }
		    //if (grid.getCurrentX() == 0 && grid.getCurrentY() == 0) System.out.println("Current x,y = 0,0");
		    if((grid.getCurrentX()!=0||grid.getCurrentY()!=0) && displayLine && grid.getActiveE().getAlliance()==grid.getTurn()){
		        if (grid.getMode() != 5 ) {
		        	g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), 255));
			        g.drawLine(shipX-grid.getViewRegion().getX(), shipY-grid.getViewRegion().getY(), grid.getCurrentX(), grid.getCurrentY());
			        int baseRadius = 20;
			        int radius = (int)(baseRadius*(rPhase));
			        //if (radius < 5) radius = 5;
			        float array2[] = {30.0f};
			        g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 5.0f, array2, 0.0f));
			        //radius = (int)((gc.getActiveE().getWidth() + gc.getActiveE().getHeight()) / 2.0f);
			        g.drawOval(grid.getCurrentX()-baseRadius, grid.getCurrentY()-baseRadius, baseRadius*2, baseRadius *2 );
			        g.drawOval(grid.getCurrentX()-radius, grid.getCurrentY()-radius, radius*2, radius*2);
			    } /*else {
			        int counter = 0;
			        for (DynamicElement d : grid.getDelements()) {//needs to be changed to use the same logic method that actually decides if the ship can move
			            if (d instanceof Ship && d.getAlliance()==grid.getTurn() && d.withinMovement(grid.getCurrentX(),grid.getCurrentY()) && d.isTargetable()) {
			                shipX = d.getX();
			                shipY = d.getY();
			                g.drawLine(shipX-grid.getViewRegion().getX(), shipY-grid.getViewRegion().getY(), grid.getCurrentX()-counter*50, grid.getCurrentY()-counter*50);
			                /*int radius = 20;
			                g.drawOval(grid.getCurrentX()-counter*50-radius, grid.getCurrentY()-counter*50-radius, radius*2, radius*2);
			                *//*
			                int baseRadius = 20;
			        int radius = (int)(baseRadius*(rPhase));
			        if (radius < 5) radius = 5;
			        
			        //radius = (int)((gc.getActiveE().getWidth() + gc.getActiveE().getHeight()) / 2.0f);
			        g.drawOval(grid.getCurrentX()-counter*50-baseRadius, grid.getCurrentY()-counter*50-baseRadius, baseRadius*2, baseRadius *2 );
			        g.drawOval(grid.getCurrentX()-counter*50-radius, grid.getCurrentY()-counter*50-radius, radius*2, radius*2);
			                counter++;
			            }
			            
			        }
			    }*/
		    }
		    g2d.setStroke(s);
		    g.setColor(new Color(g.getColor().getRed(), g.getColor().getGreen(), g.getColor().getBlue(), 40));
		    
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
			if(grid.getMode()==5){
				g.setColor(new Color(0,255,0,clear));
				g.fillOval(p.getX()-Ship.getDockRange() - grid.getViewRegion().getX()-p.getWidth()/2, grid.getActiveE().getY()-Ship.getDockRange()  - grid.getViewRegion().getY()-p.getHeight()/2, Ship.getDockRange() *2 + p.getWidth(), Ship.getDockRange() *2 + p.getHeight());
			}
		}
		else{
			g.setColor(Color.WHITE);
			//g.drawString("Nothing Selected", x, y+g.getFontMetrics().getHeight());
		}
	}

	public boolean isDisplayLine() {
		return displayLine;
	}

	public void setDisplayLine(boolean displayLine) {
		//this.displayLine = displayLine;
	}

}


