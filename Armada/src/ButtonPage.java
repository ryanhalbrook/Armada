import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class ButtonPage {

	static final int BUTTONS_PER_PAGE = 5;
	protected int x,y,width,height,page;
	private ArrayList<ButtonList> lists;
	private HUD hud;
	private ButtonList activeL;
	private Button back, forward;
	
	public ButtonPage(HUD h){
		page=0;
		hud=h;
		lists=new ArrayList<ButtonList>();
		back=new Button(0,0,30,20,h.getGC(), "<-");
		forward=new Button(0,0,30,20,h.getGC(), "->");
		back.setColor(new Color(50,150,200, 150));
		forward.setColor(new Color(50,150,200, 150));
	}

	public void setDimensions(int inX, int inY, int w, int h){
		setLocation(inX, inY);
		width=w;
		height=h;
		if(lists.size()>0){
			for(ButtonList b: lists){
				b.setDimensions(inX, inY, w, h);
			}
		}
		
	}
	
	public void setLocation(int inX, int inY){
		x=inX;
		y=inY;
		activeL.setLocation(inX,inY);
	}
	
	public void add(ItemButton b){
		if(lists==null){
			lists = new ArrayList<ButtonList>();
		}
		if(lists.size()<1){
			lists.add(new ButtonList(hud));
			activeL=lists.get(0);
		}
		ButtonList temp = lists.get(lists.size()-1);
		if(temp.size() >= BUTTONS_PER_PAGE){
			temp=new ButtonList(hud);
			lists.add(temp);
		}
		temp.add(b);
		//buttons.add(b);
	}
	
	public void draw(Graphics g){
		if(lists.size()<1)return;
		activeL=lists.get(page);
		if(activeL==null)return;
		updateButtons();
		activeL.setHeight(activeL.getSuggestedHeight());
		activeL.draw(g);
		back.draw(g);
		forward.draw(g);
	}
	
	private void updateButtons(){
		if(activeL==null)return;
		activeL.updateButtons();
		back.setY(y-back.getHeight());
		back.setX(x);
		forward.setY(y-forward.getHeight());
		forward.setX(x+width-forward.getWidth());
	}
	
	public boolean click(int inX, int inY){
		if(activeL==null)return false;
		if(back.click(inX, inY)){
			if(page==0)return true;
			else page--;
			activeL=lists.get(page);
			System.out.println("PAGE 2: " + page);
			return true;
		}
		if(forward.click(inX, inY)){
			if(page>=lists.size()-1)return true;
			else page++;
			activeL=lists.get(page);
			return true;
		}
		if(activeL.click(inX, inY)){
			return true;
		}
		return false;
	}
	
	public int size(){
		if(activeL==null)return 0;
		return activeL.size();
	}
	
	public void setHeight(int h){
		height = h;
	}
	
	public ItemButton getActiveB(){
		if(activeL==null)return null;
		return activeL.getActiveB();
	}
	
	public void unselect(){
		if(activeL==null)return;
		activeL.unselect();
	}
	
	public int getWidth(){
		return width;
	}
	public void clear(){
		if(lists==null)return;
		if(lists.size()<1)return;
		lists=new ArrayList<ButtonList>();
	}
	
	public int getSuggestedHeight(){
		if(activeL==null)return 0;
		updateButtons();
		return activeL.getSuggestedHeight();
	}
	
	public void fillShop(Planet p){
		clear();		
		//TODO: put in a switch that adds items depending on the planet's upgrade level.  It should no have breaks so that you dont need to re-type items that are added for lower levels and done highest level first
		add(new ItemButton(x+3, y+3, width, ButtonList.BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.HullPlate,true));
		add(new ItemButton(x+3, y+3, width, ButtonList.BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.SpeedUpgrade,true));
		add(new ItemButton(x+3, y+3, width, ButtonList.BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.WeaponsUpgrade,true));
		add(new ItemButton(x+3, y+3, width, ButtonList.BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.ScalingHullUpgrade,true));
		add(new ItemButton(x+3, y+3, width, ButtonList.BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.EnginesUpgrade,true));
		add(new ItemButton(x+3, y+3, width, ButtonList.BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.ScalingEnginesUpgrade,true));
		add(new ItemButton(x+3, y+3, width, ButtonList.BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.ScalingSpeedUpgrade,true));
		add(new ItemButton(x+3, y+3, width, ButtonList.BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.ScalingWeaponsUpgrade,true));
		add(new ItemButton(x+3, y+3, width, ButtonList.BUTTON_HEIGHT, hud.getGC(), ItemList.ItemNames.SpeedUpgrade,true));
	}
	
}
