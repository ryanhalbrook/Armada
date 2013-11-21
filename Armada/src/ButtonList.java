package src; import src.view.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class ButtonList {

	protected int x,y,width,height;
	static final int BUTTON_HEIGHT = 40, BUTTON_GAP = 4;
	static Color BACKGROUND_COLOR = new Color(25,125,175, 150);
	private ItemButton activeB;
	private HUD hud;
	private ArrayList<ItemButton> buttons;
	private int suggestedHeight;
	
	public ButtonList(HUD h){
		hud=h;
		buttons=new ArrayList<ItemButton>();
		x=hud.r.x;
		y=hud.r.y;
		width=hud.r.width;
		height=hud.r.height;
	}
	
	public void setDimensions(int inX, int inY, int w, int h){
		setLocation(inX, inY);
		width=w;
		height=h;
	}

	public void setLocation(int inX, int inY){
		x=inX;
		y=inY;
	}
	
	public void add(ItemButton b){
		buttons.add(b);
	}
	
	public void draw(Graphics g){
		updateButtons();
		if(buttons.size()<1)return;
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(x, y, width, height);
		drawButtons(g);
	}
	
	public void drawButtons(Graphics g){
		for(ItemButton b: buttons){
			b.draw(g);
		}
	}
	
	public void updateButtons(){
		this.setSuggestedHeight(BUTTON_GAP + (buttons.size() * (BUTTON_HEIGHT+BUTTON_GAP)));
		
		for(int i =0; i < buttons.size(); i++){
			ItemButton b = buttons.get(i);
			b.setX(x+BUTTON_GAP);
			b.setY(y+BUTTON_GAP+(i*(BUTTON_HEIGHT+BUTTON_GAP)));
			b.setWidth(width-BUTTON_GAP*2);
		}
	}
	
	public boolean click(int inX, int inY){
		synchronized(buttons){
			for(ItemButton b: buttons){
				if(b.click(inX, inY)){
					if(activeB!=null){
						activeB.setSelected(false);
					}
					//dh=new DockHUD(gc, HUD.Position.TOP_CENTER, this);
					//hm.addHUD(dh);
					activeB=b;
					activeB.setSelected(true);
					return true;
				}
			}
			return false;
		}
		
	}
	
	public int size(){
		return buttons.size();
	}
	
	public void setHeight(int h){
		height = h;
	}
	
	public ItemButton getActiveB(){
		return activeB;
	}
	
	public void unselect(){
		activeB=null;
	}
	
	public int getWidth(){
		return width;
	}
	public void clear(){
		buttons.clear();
	}
	
	public void fillShip(Ship s){
		buttons.clear();
		if(s.getItems()==null)return;
		//if(s.getItems().size() <1)return;
		int temp = s.getMaxCargo();
		for(Item i: s.getItems()){
			ItemButton tempItem =new ItemButton(x+3, y+3, 0, BUTTON_HEIGHT, hud.getGC(), i.getId(),false);
			tempItem.setClickable(true);
			add(tempItem );
			temp--;
		}
		while(temp>0){
			ItemButton tempItem =new ItemButton(x+3, y+3, 0, BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.Blank);
			tempItem.setClickable(false);
			add(tempItem);
			 temp--;
		}
	}
	
	public void fillShop(Planet p){
		buttons.clear();		
		//TODO: put in a switch that adds items depending on the planet's upgrade level.  It should no have breaks so that you dont need to re-type items that are added for lower levels and done highest level first
		add(new ItemButton(x+3, y+3, width, BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.HullPlate,true));
		add(new ItemButton(x+3, y+3, width, BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.SpeedUpgrade,true));
		add(new ItemButton(x+3, y+3, width, BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.WeaponsUpgrade,true));
		add(new ItemButton(x+3, y+3, width, BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.ScalingHullUpgrade,true));
	}
	
	public int getHeight(){
		return height;
	}

	public int getSuggestedHeight() {
		return suggestedHeight;
	}

	public void setSuggestedHeight(int suggestedHeight) {
		this.suggestedHeight = suggestedHeight;
	}
	
	
}
