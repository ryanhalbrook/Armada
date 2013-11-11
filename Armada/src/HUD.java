import java.awt.Graphics;

/**
    Represents a Heads Up Display that displays information about the state of the game.
*/
public class HUD extends ViewLayer {

    /** The grid that this HUD gets its information from. */
	protected Grid grid;
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
	public HUD(int x, int y, int width, int height, Grid gr){
		super(new BoundingRectangle(x,y,width,height));
		grid=gr;
	}
	
	/**
	    Basic constructor that also takes a Grid and a location setting.
	*/
	public HUD(int x, int y, int width, int height, Grid gr, int l){
		super(new BoundingRectangle(x,y,width,height));
		grid=gr;
		location = l;
	} 
	
	public void refresh() {
	    updateLocation();
	}
	
	/**
	    Updates the location of the HUD based on its location setting.
	*/
	public void updateLocation() {
		if(grid==null)return;
		switch(location){
		case 0: return;
		case 1:
			r.x= 10;
			r.y=TurnHUD.BAR_HEIGHT;
			break;
		case 2:
			r.x = grid.getAp().getWidth()-r.width;
			r.y=TurnHUD.BAR_HEIGHT;
			break;
		case 3:
			r.x = 10;
			r.y = grid.getAp().getHeight() - r.height - 10;
			break;
		case 4:
			r.x = grid.getAp().getWidth()-r.width - 10;
			r.y = grid.getAp().getHeight() - r.height - 10;
			break;
		case 5:
			r.x = grid.getAp().getWidth()/2-r.width/2;
			r.y=TurnHUD.BAR_HEIGHT;
			break;
		case 6:
			r.x = grid.getAp().getWidth()-r.width;
			r.y = grid.getAp().getHeight()/2 - r.height/2;
			break;
		case 7:
			r.x = grid.getAp().getWidth()/2-r.width/2;
			r.y = grid.getAp().getHeight() - r.height;
			break;
		case 8:
			r.x = 5;
			r.y = grid.getAp().getHeight()/2 - r.height/2;
			break;
		case 9:
		    r.x = 0;
		    r.y = 0;
		    break;
		case 10:
		    r.x = grid.getAp().getWidth()/2-r.width/2;
		    r.y = grid.getAp().getHeight()/2 - r.height/2;
		    break;
		  
		   
		}
		
	}

}
