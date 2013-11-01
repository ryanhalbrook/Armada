import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;

import javax.imageio.ImageIO;

import java.awt.image.*;    

/*
 * Grid paints, moves, stores, and keeps track of everything visual on the panel
 * ie, ships, planets, everything below the frame/menus and above the background
 */
public class GridLayer extends ViewLayer {
    
    private  int mode = 0, turn =0, index=0;
    private  DynamicElement activeE;
    private ArmadaEngine engine;
    private BoundingRectangle viewRegion = new BoundingRectangle(0, 0, 500,500);
    private ArmadaPanel ap;
    private ArrayList<DynamicElement> delements;
    
    static final int GRID_WIDTH = 3840;
    static final int GRID_HEIGHT = 2160;

    // Mouse coordinate information
    private int currentX = 0;
    private int currentY = 0;
    
    //Sample Image
    BufferedImage img;

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

    // Constructor
    public GridLayer(ArmadaEngine engine, DynamicSizeBroadcast s, ArmadaPanel p) {
        super(new BoundingRectangle(0,0,10000,10000));
        this.ap = p;
        
        this.engine = engine;
        delements = engine.getDynamicElements();
    	
    	//menus = new ArrayList<Menu>();
    	SoundEffect.init();
    	SoundEffect.volume=SoundEffect.Volume.LOW;

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
    
    public BoundingRectangle getViewRegion() {
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
	
	private Point clickTranslation(Point p) {
	    return new Point((int)(p.getX() + viewRegion.getX()), (int)(p.getY() + viewRegion.getY()));
	}
	
	public boolean click(int x, int y){
	    Point click = clickTranslation(new Point(x, y));
	    x = (int)click.getX();
	    y = (int)click.getY();
		System.out.println("Clicked: ("+x+", "+y+")");
		
	    if (mode == 0 && activeE == null) {
		    for (DynamicElement d : delements) {
			    if(d.isIn(x,y) && d.isTargetable()){
				    mode = 1;
				    activeE=d;
				    activeE.update();
				    return true;
				}
			}
		}
			
		if(activeE==null || activeE.getAlliance()!=turn || !activeE.isTargetable()){
			return true;
		}
		if(mode == 1){
			//move
			if (engine.legalMove(activeE, x, y)) engine.move(activeE, x, y);
		}
		if(mode == 2){
			//attack hull
			if (engine.legalHullAttack(activeE, x, y)) engine.attackHull(activeE, x, y);
		}
		if(mode == 3){
			//attack engine
			if (engine.legalEngineAttack(activeE, x, y)) engine.attackEngine(activeE, x, y);
		}
		if(mode == 4){
			//docking
			if (engine.legalDocking(activeE, x, y)) engine.dock(activeE, x, y);
		}
		return true;
	}
	
	
	/*
	 * draws everything on the Grid
	 */
	public void draw(Graphics g){
	    drawAllDelements(g);
		
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
	
	private void drawAllDelements(Graphics g){
		if(delements != null && delements.size() != 0){
			for (int i=0;i<delements.size();i++) {
				if(delements.get(i).isDead()){
					SoundEffect.EXPLODE.play();
					if((double)Math.random() >= (double)0.75){//25% chance of playing the scream
						SoundEffect.SCREAM.play();
					}
					//delements.remove(i);
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
	public ArmadaPanel getAp() {
		return ap;
	}
	public ArrayList<DynamicElement> getDelements() {
		return delements;
	}
	public int getWidth(){
		return ArmadaEngine.GRID_WIDTH;
	}
	public int getHeight(){
		return ArmadaEngine.GRID_HEIGHT;
	}

}
