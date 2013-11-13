import java.awt.Graphics;

/**
    Represents a Heads Up Display that displays information about the state of the game.
*/
public class HUD extends ViewLayer {

    /** The grid that this HUD gets its information from. */
	protected Grid grid;
	protected GameController gc = null;
	/** The location setting to place this HUD onscreen. 0=static, 1+ is dynamic, 1= top left, 2= top right, 3 = bot left, 4 = bot right.*/
	protected int location;
	
	public enum Position { STATIC, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, CENTERED, MODE_POSITION, ITEM_POSITION; }
	protected Position position = Position.STATIC;
	
	public HUD(BoundingRectangle b) {
		super(b);
	}
	
	public void setPosition(Position p) {
        this.position = p;
	}
	
	public Position getPosition() {
	    return position;   
	}
	
	public HUD(BoundingRectangle b, int l) {
	    super(b);
	    location = l;
	}
	
	/**
        Basic constructor.
	*/
	public HUD(int x, int y, int width, int height) {
		super(new BoundingRectangle(x,y,width,height));
	}
	
	/**
	    Basic Constructor that also takes a Grid.
	*/
	public HUD(int x, int y, int width, int height, Grid gr){
		super(new BoundingRectangle(x,y,width,height));
		grid=gr;
	}
	
	public HUD(BoundingRectangle b, GameController gc, int l) {
	    super(b);
	    this.gc = gc;
	    this.location = l;
	}
	
	public HUD(BoundingRectangle b, GameController gc) {
	    super(b);
	    this.gc = gc;
	}
	
	public HUD(BoundingRectangle b, GameController gc, Position p) {
	    super(b);
	    this.gc = gc;
	    position = p;
	}
	
	/**
	    Basic constructor that also takes a Grid and a location setting.
	*/
	public HUD(int x, int y, int width, int height, Grid gr, int l){
		super(new BoundingRectangle(x,y,width,height));
		grid=gr;
		location = l;
	} 
	
	public HUD(int x, int y, int width, int height, Grid gr, Position p){
		super(new BoundingRectangle(x,y,width,height));
		grid=gr;
		position = p;
	} 
	
	public void refresh(long previousTime, long currentTime) {
	    updateLocation();
	}
	
	/**
	    Updates the location of the HUD based on its location setting.
	*/
	public void updateLocation() {
	
	    //if (grid == null) return;
		if(grid != null) {
		    switch(position){
		    case STATIC: return;
		    case TOP_LEFT:
			    r.x= 10;
			    r.y=TurnHUD.BAR_HEIGHT;
			    break;
		    case TOP_RIGHT:
			    r.x = grid.getAp().getWidth()-r.width;
			    r.y=TurnHUD.BAR_HEIGHT;
			    break;
		    case BOTTOM_LEFT:
			    r.x = 10;
			    r.y = grid.getAp().getHeight() - r.height - 10;
			    break;
		    case BOTTOM_RIGHT:
			    r.x = grid.getAp().getWidth()-r.width - 10;
			    r.y = grid.getAp().getHeight() - r.height - 10;
			    break;
		    case ITEM_POSITION:
			    r.x = grid.getAp().getWidth()-r.width;
			    r.y = grid.getAp().getHeight()/2 - r.height/2;
			    break;
			case MODE_POSITION:
		        r.x = 0;
		        r.y = 0;
		        break;
		    case CENTERED:
		        r.x = grid.getAp().getWidth()/2-r.width/2;
		        r.y = grid.getAp().getHeight()/2 - r.height/2;
		        break;
		    }
		}
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
		    }
		    
		
		
		
	    }
}


}

