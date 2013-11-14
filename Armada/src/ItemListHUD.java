import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class ItemListHUD extends HUD {

	private Planet p;
	private ArrayList<ItemButton> buttons;
	private ItemButton activeB;
	private HUDmanager hm;
	private DockHUD dh;
	
	public ItemListHUD(GameController gc, Position position, HUDmanager h){
		super(new BoundingRectangle(0,0,250,300),gc, position);
		//location = l;
		hm=h;
		if(gc.getActiveE() != null && gc.getActiveE() instanceof Planet){
			p=(Planet)gc.getActiveE();
		}
		buttons = new ArrayList<ItemButton>();
		fillButtons();
	}
	
	public void fillButtons(){
	    int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();
		updateLocation();
		buttons.add(new ItemButton(x+3, y+3, width-6, 20, gc, ItemList.ItemNames.EnginesUpgrade));
		buttons.add(new ItemButton(x+3, y+3, width-6, 20, gc, ItemList.ItemNames.HullPlate));
		buttons.add(new ItemButton(x+3, y+3, width-6, 20, gc , ItemList.ItemNames.ScalingWeaponsUpgrade));
	}
	
	public void draw(Graphics g){
		if(gc.getActiveE() != null && gc.getActiveE() instanceof Planet){
			p=(Planet)gc.getActiveE();
		}
		else{
			p=null;
			return;
		}
		
		updateLocation();
		updateButtons();
		
		g.setColor(new Color(25,125,175, 150));
		g.fillRect(r.x, r.y, r.width, r.height);
		drawButtons(g);
		if(dh !=null){
			dh.draw(g);
		}
	}
	
	public void drawButtons(Graphics g){
		for(Button b: buttons){
			b.draw(g);
		}
	}
	
	public boolean click(int inX, int inY){
		if(p==null){
			dh=null;
			if(activeB!=null)activeB.setSelected(false);
			activeB=null;
		}
		
		for(ItemButton b: buttons){
			if(b.click(inX, inY)){
				if(activeB!=null){
					activeB.setSelected(false);
				}
				dh=new DockHUD(gc, 5);
				activeB=b;
				activeB.setSelected(true);
				if(p.getDocked().size() < 1){
					//InformationPopupLayer.getInstance().showPopup("There are no docked ships");
				}
				return true;
			}
		}
		
		return false;
	}
	
	public void updateButtons(){
		r.setHeight(4 + (buttons.size() * 22));
		for(int i =0; i < buttons.size(); i++){
			ItemButton b = buttons.get(i);
			b.setX(r.x+3);
			b.setY(r.y+3+(i* 22));
		}
	}
	
	public void setActiveB(ItemButton b){
		activeB=b;
	}
	
}
