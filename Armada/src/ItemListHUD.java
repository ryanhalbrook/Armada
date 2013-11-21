import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class ItemListHUD extends HUD {

	private Planet p;
	private Ship s;
	private DynamicElement de;
	//private ArrayList<ItemButton> buttons;
	//private ItemButton activeB;
	private HUDmanager hm;
	private DockHUD dh;
	private ButtonPage buttons;
	
	public ItemListHUD(GameController gc, Position position, HUDmanager h){
		super(new BoundingRectangle(0,0,250,200),gc, position);
		//location = l;
		de =gc.getActiveE();
		hm=h;
		if(de != null && de instanceof Planet){
			p=(Planet)de;
		}
		else if(de != null && de instanceof Ship){
			s=(Ship)de;
		}
		buttons = new ButtonPage(this);
		fillButtons();
	}
	
	public void fillButtons(){
	    int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();
		updateLocation();
		if(de==null){
			return;
		}
		if(gc.getActiveE() != de || de==null){
			de =gc.getActiveE();
			buttons=new ButtonPage(this);
			fillButtons();
		}
		if(de != null && de instanceof Planet){
			p=(Planet)de;
		}
		else if(de != null && de instanceof Ship){
			s=(Ship)de;
		}
		
		if(de instanceof Planet){
			buttons.add(new ItemButton(x+3, y+3, width-6, 20, gc, ItemList.ItemNames.EnginesUpgrade, true));
			buttons.add(new ItemButton(x+3, y+3, width-6, 20, gc, ItemList.ItemNames.HullPlate, true));
			buttons.add(new ItemButton(x+3, y+3, width-6, 20, gc , ItemList.ItemNames.SpeedUpgrade, true));	
		}
		else if(de instanceof Ship){
			buttons.fillShip((Ship)de);
		}
		else{System.out.println("WTF");}
		
	}
	
	public void refresh(long previousTime, long currentTime) {
		super.refresh(previousTime, currentTime);
		if(gc.getGrid().getActiveE() == null){
			displaying=false;
		}
		else{
			displaying=true;
		}
	}
	
	public void draw(Graphics g){
		if(gc.getActiveE() != de || de==null){
			de =gc.getActiveE();
			buttons=new ButtonPage(this);
			fillButtons();
		}
		if(de != null && de instanceof Planet){
			p=(Planet)de;
		}
		else if(de != null && de instanceof Ship){
			s=(Ship)de;
		}
		else{
			p=null;
			return;
		}
		
		updateLocation();
		buttons.setDimensions(r.x,r.y,r.width,r.height);
		//buttons.();
		r.setHeight(buttons.getSuggestedHeight());
		buttons.draw(g);
		if(dh !=null){
			dh.draw(g);
		}
	}
	/*
	public void drawButtons(Graphics g){
		for(Button b: buttons){
			b.draw(g);
		}
	}*/
	
	public boolean click(int inX, int inY){
		if(p==null){
			hm.remove(dh);
			dh=null;
			if(buttons.getActiveB()!=null)buttons.getActiveB().setSelected(false);
			buttons.unselect();
		}
		
		return buttons.click(inX, inY);
	}
	
	
	public ItemList.ItemNames getItemId(){
		if(buttons.getActiveB()!=null){
			return buttons.getActiveB().getId();
		}
		return null;
	}
	
}
