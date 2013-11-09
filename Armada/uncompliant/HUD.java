import java.awt.Graphics;

/**
    Represents a Heads Up Display that displays information about the state of the game.
*/
public class HUD extends ViewLayer {

    /** The GameController that this HUD gets its information from. */
	protected GameController gameController;
	/** The location setting to place this HUD onscreen. 0=static, 1+ is dynamic, 1= top left, 2= top right, 3 = bot left, 4 = bot right.*/
	protected int location;
	
	/**
        Basic constructor.
	*/
	public HUD(int x, int y, int width, int height) {
		super(new BoundingRectangle(x,y,width,height));
	}
	
	/**
	    Basic Constructor that also takes a Grid.
	*/
	public HUD(int x, int y, int width, int height, GameController gc){
		super(new BoundingRectangle(x,y,width,height));
		gameController = gc;
	}
	
	/**
	    Basic constructor that also takes a Grid and a location setting.
	*/
	public HUD(int x, int y, int width, int height, GameController gc, int l){
		super(new BoundingRectangle(x,y,width,height));
		gameController = gc;
		location = l;
	} 
	
	/**
	    Updates the location of the HUD based on its location setting.
	*/
	public void updateLocation() {
		if(gameController==null)return;
		switch(location){
		case 0: return;
		case 1:
			r.x=5;
			r.y=TurnHUD.BAR_HEIGHT;
			break;
		case 2:
			r.x = gameController.getViewWidth()-r.width;
			r.y=TurnHUD.BAR_HEIGHT;
			break;
		case 3:
			r.x = 5;
			r.y = gameController.getViewHeight() - r.height;
			break;
		case 4:
			r.x = gameController.getViewWidth()-r.width;
			r.y = gameController.getViewHeight() - r.height;
			break;
		case 5:
			r.x = gameController.getViewWidth()/2-r.width/2;
			r.y=TurnHUD.BAR_HEIGHT;
			break;
		case 6:
			r.x = gameController.getViewWidth()-r.width;
			r.y = gameController.getViewHeight()/2 - r.height/2;
			break;
		case 7:
			r.x = gameController.getViewWidth()/2-r.width/2;
			r.y = gameController.getViewHeight() - r.height;
			break;
		case 8:
			r.x = 5;
			r.y = gameController.getViewHeight()/2 - r.height/2;
			break;
		}
	}

}
