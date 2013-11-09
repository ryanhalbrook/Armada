import java.awt.Graphics;
import java.util.ArrayList;

/**
    Manages all of the HUD layers.
*/
public class HUDmanager {
	/** The Grid that this HUD system shows information about. */
	protected Grid grid;

	protected ArrayList<HUD> huds;
	protected HUD mode, stat, turn, map, items;
	private ViewLayer viewLayer;
	
	/**
	    The only constructor. Creates and initializes a HUD system manager which includes
	    a mode HUD, a stats HUD, a turn HUD, a map HUD, and an items HUD.
	    @param gr The Grid that this HUD system gets its information from.
	*/
	public HUDmanager(Grid gr){
		grid = gr;
		huds = new ArrayList<HUD>();
		mode = new ModeHUD(gr, 1);//0=default, 1 = top left, 2 = top right, 3 = bot left, 4 = bot right
		stat = new StatHUD(gr, 2);
		turn = new TurnHUD(gr);
		map = new MapHUD(gr,4);
		items= new ItemListHUD(gr, 6, this);
		mode.setName("Mode HUD");
		stat.setName("Stat HUD");
		turn.setName("Turn HUD");
		items.setName("Items HUD");
		huds.add(stat);
		huds.add(turn);
		huds.add(map);
		huds.add(items);
		huds.add(mode);
		initializeViewLayer();
	}
	
	/**
	    Sets up the view layer that can render all of the HUD layers managed
	    by this HUD manager.
	*/
	private void initializeViewLayer() {
	    viewLayer = new ViewLayer(new BoundingRectangle(0,0,10000, 10000));
		viewLayer.setName("HUD Layer");
		for (HUD h : huds) {
		    viewLayer.addSublayer(h);
		}
	}
	
	/**
	    @return A view layer that draws all of the HUDs associated with this HUD system.
	*/
	public ViewLayer getViewLayer() {
	    return viewLayer;
	}
	
	/**
	    Draws this HUD system.
	    @param g The graphics context to draw the HUD system into.
	*/
	public void draw(Graphics g){
		viewLayer.draw(g);
	}
	
	/**
	    Adds a Heads Up Display to this HUD system.
	    @param h The HUD to add.
	*/
	public void add(HUD h){
		huds.add(h);
		viewLayer.addSublayer(h);
	}
	
	/**
	    Removes a Heads Up Display from this HUD system.
	    @param h The HUD to remove.
	*/
	public void remove(HUD h){
		huds.remove(h);
		viewLayer.removeSublayer(h);
	}
	
	/**
	    @return the map HUD.
	*/
	public MapHUD getMap(){
		return (MapHUD)map;
	}
	
	/*
	public boolean click(int inX, int inY){
		if(items.click(inX,inY))return true;
		if(map.click(inX,inY))return true;
		return false;
	}
	*/
}
