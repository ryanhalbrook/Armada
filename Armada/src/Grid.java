
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;

import javax.imageio.ImageIO;

import java.awt.image.*;
/*
 * Grid paints, moves, stores, and keeps track of everything visual on the panel
 * ie, ships, planets, everything below the frame/menus and above the background
 */
public class Grid {

	
    private ArrayList<Element> elements;
    private ArrayList<DynamicElement> delements;
    private  ArrayList<Menu> menus;
    
    private  int mode = 0, turn = 1, index =0;
    private  DynamicElement activeE;
    private ArmadaPanel ap;
    private Rectangle viewRegion = new Rectangle(0, 0, 500,500); //The entire grid is 2000 by 2000 pixels. This is the region that the user sees.
    
    BufferedImage backgroundImage = null;
    
    static final int GRID_WIDTH = 3840;
    static final int GRID_HEIGHT = 2160;

    // Mouse coordinate information
    private int currentX = 0;
    private int currentY = 0;
    
    //Sample Image
    BufferedImage img;

    // Constructor
    public Grid(ArmadaPanel ap) {
        this.ap = ap;
    	elements = new ArrayList<Element>();
    	delements = new ArrayList<DynamicElement>();
    	menus = new ArrayList<Menu>();
    	SoundEffect.init();
    	SoundEffect.volume=SoundEffect.Volume.LOW;
    	
    	// Add some ships
		delements.add(new NormalShip(750,330,1));
		delements.add(new NormalShip(160,330,1));
		delements.add(new NormalShip(260,330,2));
		delements.add(new NormalShip(60,330,2));
		delements.add(new NormalShip(220,330,2));
		delements.add(new Planet());
		delements.add(new Planet());
		
		//Testing Static draw
		loadSampleImage();
    }
    private void loadSampleImage(){
        img = ImageLoader.getImage("saturn.png");

    }
    /**
        Moves the viewing region. Will stop at boundaries.
    */
    public void moveViewRegion(int x, int y) {
        viewRegion.setX(viewRegion.getX()+x);
        viewRegion.setY(viewRegion.getY()+y);
        if (viewRegion.getX() < 0) viewRegion.setX(0);
        if (viewRegion.getY() < 0) viewRegion.setY(0);
        if (viewRegion.getX() + ap.getWidth() > GRID_WIDTH) {
            viewRegion.setX(GRID_WIDTH - ap.getWidth());
        }
        if (viewRegion.getY() + ap.getHeight() > GRID_HEIGHT) {
            viewRegion.setY(GRID_HEIGHT - ap.getHeight());
        }
    }
    
    public Rectangle getViewRegion() {
        return viewRegion;
    }
    
	public void cancelMove() {//does not deselect ship.  use setMode(0) to do that 
	    mode = 0;
	}
	
	public void selectNextDEThisTurn(){
		if(delements == null)return;
		if(delements.size() <=0) return;
		if(index >= delements.size()) index=0;
		int initialIndex = index;
		DynamicElement temp = delements.get(index);
		do{
			index++;
			if(index==initialIndex){//if came full circle
				return;
			}
			if(delements.size() <= index){//if hit end of list
				index=0;
			}
			temp=delements.get(index);
			//if(temp.getAlliance() == turn)break;
		}while(temp.getAlliance()!=turn || !temp.isTargetable());
		activeE=temp;
	}
	
	public void nextMode(){
		mode++;
		if(mode > 4){
			mode=1;
		}
		System.out.println("mode: " + mode);
	}
	
	public int getCurrentX(){
		return currentX;
	}
	
	public int getCurrentY(){
		return currentY;
	}
	
	public void updateViewRegion(){
		viewRegion.setWidth(ap.getWidth());
		viewRegion.setHeight(ap.getHeight());
	}
	
	/*
	 * @param e1 DynamicElement who's range is used for calculation
	 * @param e2 DynamicElement which is calculated to be within range of DynamicElement e1
	 */
	public boolean inRange(DynamicElement e1, DynamicElement e2){
		return e1.getRange() > distance(e1,e2);
	}
	
	public void unselect(){
		activeE=null;
		mode=1;
	}
	
	public void setMode(int i){
		mode=i;
		if(mode==0){
			activeE=null;
		}
		if (mode == 0) {
	        ap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    } else {
	        ap.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	    }
	}
	
	public void mouseMoved(int x, int y) {
	    currentX = x;
	    currentY = y;
	}
	
	/*
	 * received upon any click on ArmadaPanel
	 */
	public void click(int inX, int inY){
		System.out.println("Clicked: ("+inX+", "+inY+")");
		if(mode == 0 || activeE==null){//selecting a menu
			if(menus != null && menus.size() != 0){
				for (Menu m : menus) {
					//if(m.isIn(inX,inY)){
					//	m.click();
					//}
				}
			}
			if(delements != null && delements.size() != 0){//selecting a ship
				inX += viewRegion.getX(); inY += viewRegion.getY();
				//System.out.println("x, y:" + inX + ", " + inY);
				for (DynamicElement d : delements) {
					if(d.isIn(inX,inY) && d.isTargetable()){
					    mode = 1;
						activeE=d;
						return;
						//menus.add(d.getMenu());
						
					}
				}
			}
			
		}
		/////
		if(activeE==null || activeE.getAlliance()!=turn || !activeE.isTargetable()){
			return;
		}
		if(mode == 1){
			//move
			inX += viewRegion.getX(); inY += viewRegion.getY();
			if(activeE.withinMovement(inX,inY) && activeE.canMovePath2(inX,inY, delements) && activeE instanceof Ship){
				activeE.moveTo(inX, inY);
				Ship temp = (Ship) activeE;
				temp.setPlanetDocked(null);
			}
			return;
		}
		if(mode == 2){
			//attack hull
			if(delements != null && delements.size() != 0 && activeE.canAttack()){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				//System.out.println("x, y:" + inX + ", " + inY);
					for (DynamicElement d : delements) {
						//System.out.println("looking for ship 1");
						if(d.isIn(inX,inY) && d.getAlliance()!=activeE.getAlliance() && activeE.withinRange(inX,inY) && d.isTargetable()){
							//System.out.println("looking for ship 2");
							d.hullTakeDamage(activeE);
							activeE.setCanAttack(false);
							//setMode(0);
							return;
						}
					}
				}
		}
		if(mode == 3){
			//attack engine
			if(delements != null && delements.size() != 0 && activeE.canAttack()){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				//System.out.println("x, y:" + inX + ", " + inY);
				for (DynamicElement d : delements) {
					//System.out.println("looking for ship 1");
					if(d.isIn(inX,inY) && d.getAlliance()!=activeE.getAlliance() && activeE.withinRange(inX,inY) && d.isTargetable() && d.getEngine()>0){
						//System.out.println("looking for ship 2");
						d.engineTakeDamage(activeE);
						System.out.println("Engines now at: "+d.getEngine());
						activeE.setCanAttack(false);
						//setMode(0);
						return;
					}
				}
			}
		}
		if(mode == 4){
			//docking
			if(delements != null && delements.size() != 0){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				for (DynamicElement d : delements) {
					if(d.isIn(inX,inY) && (d.getAlliance()==0 || d.getAlliance() == activeE.getAlliance()) && activeE.distanceFrom(inX, inY) < 100 && d.isTargetable() && d instanceof Planet && activeE instanceof Ship){
						System.out.println("docking attempted");
						Planet p = (Planet)d;
						Ship s = (Ship) activeE;
						p.dock(s);
						return;
					}
				}
			}
		}
		
	}
	
	/*
	 * Calculates distance between the two inputs, order does not matter
	 */
	public int distance(DynamicElement e1, DynamicElement e2){
		return (int)Math.sqrt(Math.pow(Math.abs((double)e1.getY()-(double)e2.getY()),2) + Math.pow(Math.abs((double)e1.getX()-(double)e2.getX()),2));
	}
	
	public int distance(DynamicElement e1, int inX, int inY){
		return (int)Math.sqrt(Math.pow(Math.abs((double)e1.getY()-(double)inY),2) + Math.pow(Math.abs((double)e1.getX()-(double)inX),2));
	}
	
	public void toggleTurn(){
		if(turn==1){
			turn=2;
		}
		else{
			turn=1;	
		}
		setMode(1);
		startTurn();		
	}
	
	public void startTurn(){
		activeE=null;
		mode=1;
		if(delements != null && delements.size() != 0){//selecting a ship
			for (DynamicElement d : delements) {
				//System.out.println("looking for ship 1");
				d.startOfTurn();
			}
		}
	}
	
	/*
	 * draws everything on the Grid
	 */
	public void draw(Graphics g){
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    //drawBackground(g);
	    drawAllDelements(g);
		
	} 
	
	private void drawBackground(Graphics g){
		if (backgroundImage != null) {
	        g.drawImage(backgroundImage, -viewRegion.getX(), -viewRegion.getY(), null);
	    }
	}
	
	private void drawAllDelements(Graphics g){
		if(delements != null && delements.size() != 0){
			for (int i=0;i<delements.size();i++) {
				if(delements.get(i).isDead()){
					SoundEffect.EXPLODE.play();
					if((double)Math.random() >= (double)0.75){//25% chance of playing the scream
						SoundEffect.SCREAM.play();
					}
					delements.remove(i);
					i--;
				}
			}
			for (DynamicElement de : delements) {
				if(de instanceof Planet){
					de.draw(g, viewRegion);
				}
			}
			for (DynamicElement de : delements) {
				if(de instanceof Ship){
					if(de.getAlliance() !=turn){
						de.draw(g, viewRegion);
					}
					
				}
			}
			for (DynamicElement de : delements) {
				if(de instanceof Ship){
					if(de.getAlliance() ==turn){
						de.draw(g, viewRegion);
					}
				}
			}
		}
	}
	
	private static BufferedImage loadImage(File f) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Failed to load background image");
        }
        return bi;
        
    }
	public DynamicElement getActiveE() {
		return activeE;
	}
	public int getMode() {
		return mode;
	}
	public int getTurn() {
		return turn;
	}
	public ArrayList<DynamicElement> getDelements() {
		return delements;
	}
	public ArmadaPanel getAp() {
		return ap;
	}
	public int getWidth(){
		return GRID_WIDTH;
	}
	public int getHeight(){
		return GRID_HEIGHT;
	}
}

