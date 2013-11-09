
/* BasicStroke, Color, Cursor, FontMetrics, Graphics, Graphics2D, Stroke */
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
  Grid paints, moves, stores, and keeps track of everything visual on the panel
  ie, ships, planets, everything below the frame/menus and above the background
*/
public class GridLayer extends ViewLayer {
    static final int GRID_WIDTH = 38400; // The width of the grid in pixels.
    static final int GRID_HEIGHT = 21600; // The height of the grid in pixels.

    private GameController gameController = null;
    
    private int mode = 0;
    private int index = 0;
    private DynamicElement activeE;
    
    
    
    // Mouse coordinate information
    private int currentX = 0;
    private int currentY = 0;
    
    /**
        The only constructor
        @param ap The ArmadaPanel object that this grid will be inside of.
    */
    public GridLayer(GameController gc) {
        super(new BoundingRectangle(0,0,10000,10000));
        
        gameController = gc;
    	
    	SoundEffect.init();
    	SoundEffect.volume=SoundEffect.Volume.LOW;
    }
    
    /**
        @return The rectangular portion of the overall grid that is being displayed.
    */
    public BoundingRectangle getViewRegion() {
        return viewRegion;
    }
    
	public void cancelMove() {//does not deselect ship.  use setMode(0) to do that 
	    mode = 0;
	}
	
	/**
	    Set the selected dynamic element to the next allowed selection.
	*/
	public void cycleActiveDE() {
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
		}while(temp.getAlliance()!=getTurn() || !temp.isTargetable());
		
		activeE=temp;
		viewRegion.setCenter(activeE.getX(), activeE.getY());
	}
	
	/**
	    Change the action mode to the next mode.
	*/
	public void nextMode(){
		mode++;
		if(mode > 4){
			mode=1;
		}
	}
	
	/**
	    @return The current x coordinate of the mouse cursor.
	*/
	public int getCurrentX(){
		return currentX;
	}
	
	/**
	    @return The current y coordinate of the mouse cursor. 
	*/
	public int getCurrentY(){
		return currentY;
	}
	
	/**
	 * @param e1 DynamicElement who's range is used for calculation
	 * @param e2 DynamicElement which is calculated to be within range of DynamicElement e1
	 */
	public boolean inRange(DynamicElement e1, DynamicElement e2){
		return e1.getRange() > e1.distance(e2);
	}
	
	/**
	    Deselect the selected dynamic element.
	*/
	public void unselect(){
		activeE=null;
		mode=1;
	}
	
	/**
	    Set the action mode.
	    @param i the mode. Could be a value from 1 to 4.
	*/
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
	
	public void update() {
		int vel = 50;
		
		if (!(currentX == 0) || !(currentY == 0)) {
			if(ap.getWidth() * .03 > currentX){
				if(viewRegion.getX() >=25){
					viewRegion.setX(viewRegion.getX() - vel);	
				}
		    }
			if(ap.getWidth() * .97 < currentX){
		    	if(Grid.GRID_WIDTH - (viewRegion.getX()+viewRegion.getWidth()) >=25){
					viewRegion.setX(viewRegion.getX() + vel);	
				}
		    }
			if(ap.getHeight() * .03 > currentY){
		    	if(viewRegion.getY() >=25){
					viewRegion.setY(viewRegion.getY() - vel);	
				}
		    }
			if(ap.getHeight() * .97 < currentY){
		    	if(Grid.GRID_HEIGHT - (viewRegion.getY() + viewRegion.getHeight()) >=25){
					viewRegion.setY(viewRegion.getY() + vel);	
				}
		    }	
		}
	}
	
	/*
	 * received upon any click on ArmadaPanel
	 */
	public boolean click(int inX, int inY){
		ArrayList<DynamicElement> delements = engine.getDynamicElements();
	    if((mode == 0 || activeE==null) && delements != null && delements.size() != 0) { //selecting a ship
			inX += viewRegion.getX(); inY += viewRegion.getY();
			for (DynamicElement d : delements) {
			    if(d.isIn(inX,inY) && d.isTargetable()) {
				    mode = 1;
				    activeE=d;
				    activeE.update();
			        return true;
			    }
			}
		}
			  
		if(activeE==null || activeE.getAlliance()!=getTurn() || !activeE.isTargetable()) {
			return true;
		}
		if(mode == 1) { //move
			inX += viewRegion.getX(); inY += viewRegion.getY();
			engine.moveDynamicElement(activeE, inX, inY, delements);
			return true;
		}
		if(mode == 2) { //attack hull
			if(delements != null && delements.size() != 0 && activeE.canAttack()){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				engine.attackHull(activeE, inX, inY, delements);
		    }
		    return true;
		}
		if(mode == 3) { //attack engine
			if(delements != null && delements.size() != 0 && activeE.canAttack()){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				engine.attackEngine(activeE, inX, inY, delements);
			}
		}
		if(mode == 4) {
			//docking
			if(delements != null && delements.size() != 0){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				engine.dock(activeE, inX, inY, delements);
			}
			return true;
		}
		return false;
	}
	
	public void toggleTurn(){
	ArrayList<DynamicElement> delements = engine.getDynamicElements();
	    setMode(0);
	    engine.toggleTurn();
	    for (DynamicElement d : delements) {
				//System.out.println("looking for ship 1");
				d.startOfTurn(this);
		}
	    activeE = null;
	    mode = 1;		
	}
	
	/*
	 * draws everything on the Grid
	 */
	public void draw(Graphics g){
	    drawAllDelements(g);
	} 
	
	private void drawAllDelements(Graphics g){
		ArrayList<DynamicElement> delements = engine.getDynamicElements();
		if(delements != null) {
			for (int i=0;i<delements.size();i++) {
				if(delements.get(i).isDead()){
					SoundEffect.EXPLODE.play();
					if((double)Math.random() >= (double)0.9){//25% chance of playing the scream
					    SoundEffect.SCREAM.play();
					}   
					elements.add(new Explosion(delements.get(i)));
					
					delements.remove(i);
					i--;
				}
			}
			for (int i=0;i<elements.size();i++){
				 if(elements.get(i) instanceof Explosion){
					if(((Explosion)(elements.get(i))).isDone()){
					    elements.remove(i);
					    i--;
					}
				}
			}
			for(Element e:elements){
			    e.draw(g, viewRegion);
			}
			for (DynamicElement de : delements) {
			    de.draw(g, viewRegion);
			}
			
		}
	}
	
	public DynamicElement getActiveE() {
		return activeE;
	}
	public int getMode() {
		return mode;
	}
	public int getWidth(){
		return GRID_WIDTH;
	}
	public int getHeight(){
		return GRID_HEIGHT;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

}



