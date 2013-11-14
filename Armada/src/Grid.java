
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
public class Grid extends ViewLayer {

   // static final int GRID_WIDTH = 38400; // The width of the grid in pixels.
    //static final int GRID_HEIGHT = 21600; // The height of the grid in pixels.
	static final int GRID_WIDTH = 7680; // The width of the grid in pixels.
    static final int GRID_HEIGHT = 4320; // The height of the grid in pixels.
	private ArmadaEngine engine;
	
    private ArrayList<Element> elements;
    private ArrayList<DynamicElement> delements;
    
    private int mode = 1;
    private int index = 0;
    private DynamicElement activeE;
    private GameController gc = null;
    private DynamicSizeBroadcast dsb;
    private BoundingRectangle viewRegion = null; //The entire grid is 2000 by 2000 pixels. This is the region that the user sees.
    
    // Mouse coordinate information
    private int currentX = 0;
    private int currentY = 0;
    
    /**
        The only constructor
        @param ap The ArmadaPanel object that this grid will be inside of.
    */
    public Grid(GameController gc) {
        super(new BoundingRectangle(0,0,10000,10000));
        this.gc = gc;
        engine = gc.getEngine();
        this.dsb = gc.getViewSize();
        viewRegion = gc.getViewRegion();
    	elements = new ArrayList<Element>();
    	delements = engine.getDynamicElements();
    	SoundEffect.init();
    	SoundEffect.volume=SoundEffect.Volume.LOW;
    }
    
    /**
        Moves the viewing region. Stops at boundaries.
        @param x The number of pixels to move in the x direction.
        @param y The number of pixels to move in the y direction.
    */
    public void moveViewRegion(int x, int y) {
        gc.moveViewRegion(x, y);
    }
    
    /**
        @return The rectangular portion of the overall grid that is being displayed.
    */
    public BoundingRectangle getViewRegion() {
        return gc.getViewRegion();
    }
    
    public void moveViewRegionToPoint(int x, int y) {
        gc.moveViewRegionToPoint(x, y);
    }
    
    /**
        Enables ships to move extremely far. Intended for debugging purposes.
    */
	public void moveCheat() { 
	    engine.enableDebugSpeed(); 
	    gc.showInfo("Move Cheat Enabled");
	}
	
	//does not deselect ship.  use setMode(0) to do that 
	public void cancelMove() { setMode(0);}
	
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
		int temp = mode;
		temp++;
		if(temp > 5){
			temp=1;
		}
		this.setMode(temp);
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
	
	public void refresh(long previousTime, long currentTime) {
		viewRegion.setWidth(dsb.getWidth());
		viewRegion.setHeight(dsb.getHeight());
		
		int vel = 50;
		
		if (!(currentX == 0) || !(currentY == 0)) {
			if(dsb.getWidth() * .03 > currentX){
				if(viewRegion.getX() >=25){
					viewRegion.setX(viewRegion.getX() - vel);	
				}
		    }
			if(dsb.getWidth() * .97 < currentX){
		    	if(Grid.GRID_WIDTH - (viewRegion.getX()+viewRegion.getWidth()) >=25){
					viewRegion.setX(viewRegion.getX() + vel);	
				}
		    }
			if(dsb.getHeight() * .03 > currentY){
		    	if(viewRegion.getY() >=25){
					viewRegion.setY(viewRegion.getY() - vel);	
				}
		    }
			if(dsb.getHeight() * .97 < currentY){
		    	if(Grid.GRID_HEIGHT - (viewRegion.getY() + viewRegion.getHeight()) >=25){
					viewRegion.setY(viewRegion.getY() + vel);	
				}
		    }	
		}
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
		//mode=1;
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
	        //ap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    } else {
	        //ap.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	    }
	    gc.modeChanged();
	}
	
	public void mouseMoved(int x, int y) {
	    currentX = x;
	    currentY = y;
	}
	
	public void update() {
		
		if(delements != null) {
			for (int i=0;i<delements.size();i++) {
				if(delements.get(i).isDead()){
					SoundEffect.EXPLODE.play();
					if((double)Math.random() >= (double)0.9){//10% chance of playing the scream
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
		}
		
	}
	
	public boolean click(int inX, int inY){
			
	    if((mode == 0 || activeE==null) && delements != null && delements.size() != 0) { //selecting a ship
			inX += viewRegion.getX(); inY += viewRegion.getY();
			for (DynamicElement d : delements) {
			    if(d.isIn(inX,inY) && d.isTargetable()) {
				    //mode = 1;
				    activeE=d;
				    activeE.update();
			        return true;
			    }
			}
		}
	      
		if(activeE==null || !activeE.isTargetable()) {
			return true;
		}
		if(activeE.getAlliance()!=getTurn()){
			gc.invalidMoveAttempt("Can only command units under your control");
			return true;
		}	
		if(mode == 1) { //move
			inX += viewRegion.getX(); inY += viewRegion.getY();
			handleMovementStatus(engine.moveDynamicElement(activeE, inX, inY));
			return true;
		}
		if(mode == 2) { //attack hull
			if(delements != null && delements.size() != 0 && activeE.canAttack()){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				handleAttackStatus(engine.attackHull(activeE, inX, inY));
		    }
			else if (!activeE.canAttack()){
				gc.invalidMoveAttempt("Cannot attack again this turn");
			}
		    return true;
		}
		if(mode == 3) { //attack engine
			if(delements != null && delements.size() != 0 && activeE.canAttack()){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				handleAttackStatus(engine.attackEngine(activeE, inX, inY));
			}
			else if (!activeE.canAttack()){
				gc.invalidMoveAttempt("Cannot attack again this turn");
			}
		}
		if(mode == 4) {
			//docking
			if(delements != null && delements.size() != 0){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				engine.dock(activeE, inX, inY);
			}
			return true;
		}
		
		if (mode == 5) {
		    // Fleet move
		    inX += viewRegion.getX(); inY += viewRegion.getY();
		    int counter = 0;
		    for (DynamicElement d : delements) {
		        if (d instanceof Ship && d.getAlliance()==engine.getTurn() && d.withinMovement(inX,inY) && d.isTargetable()) {
		            
		            System.out.println("Moving");
		            handleMovementStatus(engine.moveDynamicElement(d, inX+counter-counter*50, inY-counter*50));
		            counter++;
		        }
		    }
		}
		return false;
	}
	
	private void handleMovementStatus(ArmadaEngine.MovementStatus status) {
	    
	    if (status == ArmadaEngine.MovementStatus.SUCCESS) {
	        if (mode != 5) gc.showInfo("Moving Ship");
	        else gc.showInfo("Moving Fleet");
	    }
	    if (status == ArmadaEngine.MovementStatus.RANGE) gc.invalidMoveAttempt("Out of Range!");
	    if (status == ArmadaEngine.MovementStatus.OBJECT_IN_PATH) gc.invalidMoveAttempt("Path obstructed!");
	    if (status == ArmadaEngine.MovementStatus.CANNOT_MOVE_PLANET) gc.invalidMoveAttempt("Planets cannot move!");
	    if (status == ArmadaEngine.MovementStatus.UNKNOWN_FAILURE) gc.invalidMoveAttempt("Invalid Move!");
	}
	
	private void handleAttackStatus(ArmadaEngine.AttackStatus status) {
	    if (status == ArmadaEngine.AttackStatus.SUCCESS) gc.showInfo("Attacking Target");
	    if (status == ArmadaEngine.AttackStatus.RANGE) gc.invalidMoveAttempt("Target out of range!");
	    if (status == ArmadaEngine.AttackStatus.BAD_TARGET) gc.invalidMoveAttempt("Target out of range!");
	    if (status == ArmadaEngine.AttackStatus.UNKNOWN_FAILURE) gc.invalidMoveAttempt("Cannot attack this!");
	}
	
	public void toggleTurn() {
	    gc.newTurn();
	    //setMode(0);
	    engine.toggleTurn();
	    activeE = null;
	    mode = 1;		
	}
	
	/* draws everything on the Grid */
	public void draw(Graphics g){ drawAllDelements(g); } 
	
	private void drawAllDelements(Graphics g){
		if(delements != null){
			
			
			for(Element e:elements){
			    e.draw(g, viewRegion);
			}
			
			//draws DE's in order
			for (DynamicElement de : delements) {
				if(de instanceof Planet){
					de.draw(g, viewRegion);
				}
			}
			for (DynamicElement de : delements) {
				if(de instanceof Ship && de.getAlliance() != engine.getTurn()){
					de.draw(g, viewRegion);
				}
			}
			for (DynamicElement de : delements) {
				if(de instanceof Ship && de.getAlliance() == engine.getTurn()){
					de.draw(g, viewRegion);
				}
			}
			//End drawing DE's
			
			
		}
			
			
	}
	
	public PlayerManager getPlayerManager(){
		return engine.getPlayerManager();
	}
	
	public Player getWinner(){
		return getPlayerManager().getWinner();
	}
	
	public Player getLoser(){
		return getPlayerManager().getLoser();
	}
    
    public double secondsRemainingForTurn() {
        return engine.secondsRemainingForTurn();
    }
    
    public double maxSecondsForTurn() {
        return engine.maxSecondsForTurn();
    }
	
	public void add(DynamicElement inDE){
		delements.add(inDE);
	}
	
	public DynamicElement getActiveE() {
		return activeE;
	}
	public int getMode() {
		return mode;
	}
	
	public String getModeString() {
	    if (mode == 0) return "No Selection";
	    if (mode == 1) return "Move";
	    if (mode == 2) return "Attack Hull";
	    if (mode == 3) return "Attack Engine";
	    if (mode == 4) return "Dock";
	    if (mode == 5) return "Move Fleet";
	    return "Unknown Mode";
	}
	
	public int getTurn() {
		return engine.getTurn();
	}
	public ArrayList<DynamicElement> getDelements() {
		return delements;
	}
	/*
	public ArmadaPanel getAp() {
		return ap;
	}
	*/
	public DynamicSizeBroadcast getAp() {
		return this.dsb;
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
//this.ap = ap;
//private ArmadaPanel ap;
//delements = new ArrayList<DynamicElement>();

/*
        viewRegion.setX(viewRegion.getX()+x);
        viewRegion.setY(viewRegion.getY()+y);
        if (viewRegion.getX() < 0) viewRegion.setX(0);
        if (viewRegion.getY() < 0) viewRegion.setY(0);
        if (viewRegion.getX() + dsb.getWidth() > GRID_WIDTH) {
            viewRegion.setX(GRID_WIDTH - dsb.getWidth());
        }
        if (viewRegion.getY() + dsb.getHeight() > GRID_HEIGHT) {
            viewRegion.setY(GRID_HEIGHT - dsb.getHeight());
        }
        
        */

