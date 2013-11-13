import java.awt.Graphics;
import java.util.*;

/**
    Manages all of the HUD layers.
*/
public class HUDmanager extends ViewLayer {
	/** The Grid that this HUD system shows information about. */
	protected Grid grid;
	protected GameController gc = null;

	protected ArrayList<HUD> huds;
	protected HUD mode, stat, turn, map, items; 
	
	/**
	    The only constructor. Creates and initializes a HUD system manager which includes
	    a mode HUD, a stats HUD, a turn HUD, a map HUD, and an items HUD.
	    @param gr The Grid that this HUD system gets its information from.
	*/
	public HUDmanager(Grid gr, GameController gc) {
	    super(new BoundingRectangle(0, 0, gr.getAp().getWidth(), gr.getAp().getHeight()));
		grid = gr;
		this.gc = gc;
		huds = new ArrayList<HUD>();
		mode = new ModeHUD(gr, HUD.Position.MODE_POSITION);//0=default, 1 = top left, 2 = top right, 3 = bot left, 4 = bot right
		stat = new StatHUD(gr, HUD.Position.TOP_RIGHT);
		turn = new TurnHUD(gr);
		map = new MapHUD(gr, HUD.Position.BOTTOM_RIGHT);
		items= new ItemListHUD(gr, 1, this);
		items.setPosition(HUD.Position.ITEM_POSITION);
		mode.setName("Mode HUD");
		stat.setName("Stat HUD");
		turn.setName("Turn HUD");
		items.setName("Items HUD");
		//viewLayer = new ViewLayer(new BoundingRectangle(0, 0, grid.getAp().getWidth(), grid.getAp().getHeight()));
		/*
		addHUD(mode, Position.MODE_POSITION);
		addHUD(stat, Position.TOP_RIGHT);
		addHUD(map, Position.BOTTOM_RIGHT);
		addHUD(items, Position.ITEM_POSITION);
		addHUD(turn, Position.STATIC);
		*/
		addHUD(stat);
		addHUD(map);
		addHUD(items);
		addHUD(mode);
		addHUD(turn);
	}
	/*
	private void updateLocations() {
	    for (HUD h : huds) {
	        HUD.Position p = h.getPosition();
	        BoundingRectangle r = h.getBoundingRectangle();
	        if(gc != null) {
		        if (p == HUD.Position.STATIC) continue;
		        if (p == HUD.Position.TOP_LEFT) {
			        r.setX(10);
			        r.setY(TurnHUD.BAR_HEIGHT);
			    }
		        if (p == HUD.Position.TOP_RIGHT) {
			        r.setX(gc.getViewWidth()-r.width);
			        r.setY(TurnHUD.BAR_HEIGHT);
			    }
		        if (p == HUD.Position.BOTTOM_LEFT) {
			        r.setX(10);
			        r.setY(gc.getViewHeight() - r.height - 10);
			    }
		        if (p == HUD.Position.BOTTOM_RIGHT) {
			        r.setX(gc.getViewWidth()-r.width - 10);
			        r.setY(gc.getViewHeight() - r.height - 10);
			    }
		        if (p == HUD.Position.ITEM_POSITION) {
			        r.setX(gc.getViewWidth()-r.width);
			        r.setY(gc.getViewHeight()/2 - r.height/2);
			    }
			    if (p == HUD.Position.MODE_POSITION) {
		            r.setX(0);
		            r.setY(0);
		        }
		        if (p == HUD.Position.CENTERED) {
		            System.out.println("CENTERED");
		            r.setX(grid.getAp().getWidth()/2-r.width/2);
		            r.setY(grid.getAp().getHeight()/2 - r.height/2);
		        }
		    }
	    }
	}
	*/
	
	/*
	private void updateLocations() {
	    for (Position p: huds2.keySet()) {
	        System.out.println(p);
	        HUD h = huds2.get(p);
	        BoundingRectangle r = h.getBoundingRectangle();
	        if(gc != null) {
		        if (p == Position.STATIC) continue;
		        if (p == Position.TOP_LEFT) {
			        r.setX(10);
			        r.setY(TurnHUD.BAR_HEIGHT);
			    }
		        if (p == Position.TOP_RIGHT) {
			        r.setX(gc.getViewWidth()-r.width);
			        r.setY(TurnHUD.BAR_HEIGHT);
			    }
		        if (p == Position.BOTTOM_LEFT) {
			        r.setX(10);
			        r.setY(gc.getViewHeight() - r.height - 10);
			    }
		        if (p == Position.BOTTOM_RIGHT) {
			        r.setX(gc.getViewWidth()-r.width - 10);
			        r.setY(gc.getViewHeight() - r.height - 10);
			    }
		        if (p == Position.ITEM_POSITION) {
			        r.setX(gc.getViewWidth()-r.width);
			        r.setY(gc.getViewHeight()/2 - r.height/2);
			    }
			    if (p == Position.MODE_POSITION) {
		            r.setX(0);
		            r.setY(0);
		        }
		    }
	    }
	}
	*/
	/*
	public HUDmanager(GameController gc) {
	    super(new BoundingRectangle(
	    huds2 = new Hashtable<Position, HUD>();
	    this.gc = gc;
	}
	*/
	
	public void addHUD(HUD hud) {
	huds.add(hud);
	    addSublayer(hud);
	    //viewLayer.addSublayer(hud);
	}
	
	/**
	    @return A view layer that draws all of the HUDs associated with this HUD system.
	*/
	/*
	public ViewLayer getViewLayer() {
	    return viewLayer;
	}
	*/
	
	public void refresh(long previousTime, long currentTime) {
	    //updateLocations();
	    super.refresh(previousTime, currentTime);
	}
	
	/**
	    Removes a Heads Up Display from this HUD system.
	    @param h The HUD to remove.
	*/
	public void remove(HUD h){
		//huds.remove(h);
		removeSublayer(h);
	}
	
	/**
	    @return the map HUD.
	*/
	
	public MapHUD getMap(){
		return (MapHUD)map;
	}
	
	public TurnHUD getTurnHUD() {
	    return (TurnHUD)turn;
	}
	
	/*
	public boolean click(int inX, int inY){
		if(items.click(inX,inY))return true;
		if(map.click(inX,inY))return true;
		return false;
	}
	*/
}
