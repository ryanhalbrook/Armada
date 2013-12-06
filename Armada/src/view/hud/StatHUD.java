package view.hud; 
import view.*;
import view.button.Button;
import view.controller.GameController;
import view.hud.HUD.Position;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import element.DynamicElement;
import element.planet.Planet;
import element.ship.Ship;

/**
 * Unused in final version
 * @author Aaron
 *
 */
public class StatHUD extends HUD{
	
	private ArrayList<Button> buttons;
	private DynamicElement de;
	private int buttonHeight = 30, buttonGap=3;
	private double scaleH =.05, scaleW = .15;
	
/*
	public StatHUD(int x, int y, int width, int height) {
		super(x, y, width, height);
		fillButtons();
		// TODO Auto-generated constructor stub
	}
*/
	public StatHUD(GameController gc, Position p){
		super(new BoundingRectangle(5,45,150,100),gc, p);
	}
	

	public void fillButtons(){
		buttons=new ArrayList<Button>();
	    int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();
		updateLocation();
		if(gc.getActiveE() instanceof Ship){
			buttons.add(new Button(0,y,width,buttonHeight, gc,"Hull: " + gc.getActiveE().getHull() + "/" + gc.getActiveE().getMaxHull(), false));//x and y actually don't matter
			buttons.add(new Button(0,y,width,buttonHeight, gc,"Engine: " + gc.getActiveE().getEngine() + "/" + gc.getActiveE().getMaxEngine(), false));
			buttons.add(new Button(0,y,width,buttonHeight, gc,"Damage: " + gc.getActiveE().getWeapons(), false));
			buttons.add(new Button(0,y,width,buttonHeight, gc,"Speed: " + gc.getActiveE().getAdjustedSpeed() + "/" + gc.getActiveE().getSpeed(), false));
			buttons.add(new Button(0,y,width,buttonHeight, gc,"Movement Left: " + (gc.getActiveE().getAdjustedSpeed() - gc.getActiveE().getMoved()), false));
			buttons.add(new Button(0,y,width,buttonHeight, gc,"Can Attack: " + gc.getActiveE().canAttack(), false));
		}
		if(gc.getActiveE() instanceof Planet){
			buttons.add(new Button(0,y,width,18, gc,"Health: " + gc.getActiveE().getHull() + "/" + gc.getActiveE().getMaxHull(), false));
		}
	}
	
	public boolean click(int inX,int inY){
		if(buttons != null){
			for(Button b: buttons){
				if(b.click(inX,inY)){
					return true;
				}
			}
		}
		if(r.isIn(inX, inY)){
			return true;
		}
		return false;
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
	if (gc == null) return;
		if(gc.getActiveE() == null) return;
		Font temp = g.getFont();
		g.setFont(new Font("Dialog",Font.BOLD, buttonHeight/2));
		
		buttonHeight=(int)((double)gc.getViewSize().getHeight()*scaleH);
		r.setWidth((int)((double)gc.getViewSize().getWidth()*scaleW));
		
		
		updateLocation();
		fillButtons();	
		updateButtons();
		
		g.setColor(new Color(25,125,175, 150));
		g.fillRect(r.x, r.y, r.width, r.height);
		drawButtons(g);
		g.setFont(temp);
	}
	
	public void drawButtons(Graphics g){
		if(buttons == null){
			return;
		}
		for(Button b: buttons){
			b.draw(g);
		}
	}
	
	
	public void updateButtons(){
		if(buttons==null)return;
		r.setHeight(4 + (buttons.size() * (buttonHeight + buttonGap)));
		for(int i =0; i < buttons.size(); i++){
			Button b = buttons.get(i);
			b.setX(r.x+3);
			b.setY(r.y+3+(i* (buttonHeight+buttonGap)));
		}
	}
	
	
}
