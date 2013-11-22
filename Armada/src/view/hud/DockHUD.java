package view.hud; 
import view.*;
import view.button.ShipButton;
import view.controller.GameController;
import item.Item;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import element.planet.Planet;

public class DockHUD extends HUD{
	
	private Planet p;
	private ArrayList<ShipButton> buttons;
	private ItemListHUD il;

	public DockHUD(GameController gc, Position p, ItemListHUD inIl){
		super(new BoundingRectangle(0,0,300,30),gc);
		//location = l;
		il=inIl;
		position = p;
		buttons=new ArrayList<ShipButton>();
		fillButtons();
	}
	
	public void draw(Graphics g){
		int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();
	    
		if(gc.getActiveE() instanceof Planet) p = (Planet)gc.getActiveE();
		else return;
		if(p==null){
			return;
		}
		updateLocation();
		if(p.getDocked().size() < 1){
			//InformationPopupLayer.getInstance().showPopup("There are no docked ships");
			return;
		}
		
		
		
		fillButtons();
		updateHeight();
		g.setColor(new Color(50,100,210, 75));
		g.fillRect(x, y, width, height);
		g.setColor(new Color(25,125,175, 75));
		g.fillRect(x+5, y+5, width-10, height-10);
		drawButtons(g);
	}
	
	public void updateHeight(){
		r.setHeight( 12 + (buttons.size() * 24));
	}
	
	public void updateButtons(){
		r.setHeight(buttons.size() * 35);
		for(int i =0; i < buttons.size(); i++){
			ShipButton b = buttons.get(i);
			b.setX(r.x+3);
			b.setY(r.y+3+(i* 22));
		}
	}
	
	public void drawButtons(Graphics g){
		for(ShipButton b: buttons){
			b.draw(g);
		}
	}
	
	public boolean click(int inX, int inY){
		for(ShipButton b: buttons){
			if(b.isIn(inX, inY)){
				//b.setSelected(true);
				if(il.getItemId()!=null){
					b.getShip().addItem(new Item(il.getItemId()));	
				}
				return true;	
			}
		}
		return false;
	}
	
	public void fillButtons(){
	    int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();
		buttons=new ArrayList<ShipButton>();
		updateLocation();
		if(p==null)return;
		if(p.getDocked().size()<1)return;
		for(int i =0; i < p.getDocked().size(); i++){
			buttons.add(new ShipButton(x+8, y+8+(i*24), width-16, 20, gc, p.getDocked().get(i)));	
		}
		
	}

}
