import java.awt.Graphics;

/**
    Represents a Heads Up Display that displays information about the state of the game.
*/
public class HUD extends ViewLayer {

	protected GameController gc = null;
	protected boolean displaying = true;
	
	public enum Position { STATIC, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTERED, MODE_POSITION, ITEM_POSITION, TOP_CENTER; }
	/** The location setting to place this HUD on the screen */
	protected Position position = Position.STATIC;
	
	public HUD(BoundingRectangle b) {
		super(b);
		position = Position.STATIC;
	}
	
	public HUD(BoundingRectangle b, GameController gc) {
	    super(b);
	    this.gc = gc;
	    position = Position.STATIC;
	}
	
	public HUD(BoundingRectangle b, GameController gc, Position p) {
	    super(b);
	    this.gc = gc;
	    position = p;
	}
	
	public void refresh(long previousTime, long currentTime) {
	    updateLocation();
	}
	
	/**
	    Updates the location of the HUD based on its location setting.
	*/
	public void updateLocation() {
	    
		
	    if (gc != null) {
		    switch(position){
		    
		        case STATIC: return;
		        case TOP_LEFT:
			        r.x= 10;
			        r.y=TurnHUD.BAR_HEIGHT;
			        break;
		        case TOP_RIGHT:
			        r.x = gc.getViewSize().getWidth()-r.width;
			        r.y=TurnHUD.BAR_HEIGHT;
			        break;
		        case BOTTOM_LEFT:
			        r.x = 10;
			        r.y = gc.getViewSize().getHeight() - r.height - 10;
			        break;
		        case BOTTOM_RIGHT:
			        r.x = gc.getViewSize().getWidth()-r.width - 10;
			        r.y = gc.getViewSize().getHeight() - r.height - 10;
			        break;
		        case ITEM_POSITION:
			        r.x = gc.getViewSize().getWidth()-r.width;
			        r.y = gc.getViewSize().getHeight()/2 - r.height/2;
			        break;
			    case MODE_POSITION:
		            r.x = 0;
		            r.y = 0;
		            break;
		        case CENTERED:
		            r.x = gc.getViewSize().getWidth()/2-r.width/2;
		            r.y = gc.getViewSize().getHeight()/2 - r.height/2;
		            break;
		        case TOP_CENTER:
			        r.x = gc.getViewSize().getWidth()/2-r.width/2;
			        r.y=TurnHUD.BAR_HEIGHT;
			    break;
		    } // End of switch 
	    } // End of if
    } // End of method

	
	public boolean isIn(int inX, int inY){
		if(r.isIn(inX, inY) && displaying){
			return true;
		}
		else return false;
	}
	
	
    public void setPosition(Position p) {
        this.position = p;
	}
    
    public int getX(){
    	return r.x;
    }
    
    public int getY(){
    	return r.y;
    }
	
	public Position getPosition() {
	    return position;   
	}
	
	public GameController getGC(){
		return gc;
	}

}

