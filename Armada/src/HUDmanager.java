import java.awt.Graphics;
import java.util.ArrayList;


public class HUDmanager {
	
	protected Grid grid;

	protected ArrayList<HUD> huds;
	protected HUD mode, stat, turn, map, items;
	private ViewLayer viewLayer;
	
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
		viewLayer = new ViewLayer(new BoundingRectangle(0,0,10000, 10000));
		viewLayer.setName("HUD Layer");
		//for some reason, these are drawn in reverse order, so the first added is on top
		huds.add(stat);
		huds.add(turn);
		huds.add(map);
		huds.add(items);
		huds.add(mode);
		for (HUD h : huds) {
		    viewLayer.addSublayer(h);
		}
	}
	
	public ViewLayer getViewLayer() {
	    return viewLayer;
	}
	
	public void draw(Graphics g){
	/*
		for(HUD h: huds){
			h.draw(g);
		}
		*/
		viewLayer.draw(g);
	}
	
	/*
	public boolean click(int inX, int inY){
	    
		if(items.click(inX,inY))return true;
		if(map.click(inX,inY))return true;
		return false;
	}
	*/
	public void add(HUD h){
		huds.add(h);
		viewLayer.addSublayer(h);
	}
	public void remove(HUD h){
		huds.remove(h);
		viewLayer.removeSublayer(h);
	}
	
	public MapHUD getMap(){
		return (MapHUD)map;
	}
	
}
