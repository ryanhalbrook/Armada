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
	protected HUD mode, stat, turn, map, items,trade; 
	protected Ship s1,s2;
	
	/**
	    The only constructor. Creates and initializes a HUD system manager which includes
	    a mode HUD, a stats HUD, a turn HUD, a map HUD, and an items HUD.
	    @param gr The Grid that this HUD system gets its information from.
	*/
	public HUDmanager(Grid gr, GameController gc) {
	    super(new BoundingRectangle(0, 0, 10000, 10000));
		grid = gr;
		this.gc = gc;
		huds = new ArrayList<HUD>();
		mode = new ModeHUD(gr, gc, HUD.Position.MODE_POSITION);//0=default, 1 = top left, 2 = top right, 3 = bot left, 4 = bot right
		stat = new StatHUD(gc, HUD.Position.TOP_RIGHT);
		turn = new TurnHUD(gr, gc);
		map = new MapHUD(gr, gc, HUD.Position.BOTTOM_RIGHT);
		items= new ItemListHUD(gc, HUD.Position.TOP_LEFT, this);
		
		/*s1=new NormalShip(0,0,1);
		s1.addItem(new Item(ItemList.ItemNames.HullPlate));
		s2=new JuggernautShip(0,0,1);*/
		trade=new TradeHUD(gc,HUD.Position.CENTERED);
		
		items.setPosition(HUD.Position.ITEM_POSITION);
		mode.setName("Mode HUD");
		stat.setName("Stat HUD");
		turn.setName("Turn HUD");
		items.setName("Items HUD");
		this.setName("HUD");
		
		addHUD(trade);
		addHUD(map);
		addHUD(stat);
		addHUD(items);
		addHUD(mode);
		addHUD(turn);
		
	}
	
	public void addHUD(HUD hud) {
	    huds.add(hud);
	    addSublayer(hud);
	}
	
	public boolean click(int x, int y) {
	    System.out.println("HUD Layer clicked");
	    return super.click(x, y);
	}
	
	public void refresh(long previousTime, long currentTime) {
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
	

}
