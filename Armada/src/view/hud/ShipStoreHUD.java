package view.hud;

import item.Item;
import item.ItemList;

import java.awt.Graphics;

import element.planet.HomePlanet;
import element.ship.CargoShip;
import element.ship.JuggernautShip;
import element.ship.NormalShip;
import element.ship.Ship;
import view.BoundingRectangle;
import view.button.ButtonPage;
import view.button.ItemButton;
import view.controller.GameController;
import view.hud.HUD.Position;

public class ShipStoreHUD extends HUD{

	private ButtonPage shipList;
	private final int TITLE_HEIGHT=30,GAP=5;
	private HomePlanet p;
	private ItemButton activeB;
	private Ship s;
	
	public ShipStoreHUD(GameController gc, Position p){
		super(new BoundingRectangle(5,45,350,400),gc, p);
		shipList = new ButtonPage(this);
		displaying=false;
	}
	
	public void refresh(long previousTime, long currentTime) {
		super.refresh(previousTime,currentTime);
		if(gc.getActiveE()==null || !(gc.getActiveE() instanceof HomePlanet))displaying=false;
		else if(gc.getActiveE() instanceof HomePlanet && gc.getActiveE().getAlliance()==gc.getGrid().getTurn()){
			p=(HomePlanet) gc.getActiveE();
			displaying=true;
			//update();
		}
		else {
			displaying=false; 
		}
	}
	
	private void update(){
		shipList.clear();
		shipList.fillHomePlanet();
		r.setHeight(shipList.getSuggestedHeight() + TITLE_HEIGHT+GAP);
		updateLocation();
		shipList.setDimensions(r.x+GAP, r.y+this.TITLE_HEIGHT,r.width-GAP,shipList.getSuggestedHeight());
		
	}
	
	public void draw(Graphics g){
		if(!displaying)return;
		update();
		shipList.draw(g);
	}
	
	public boolean click(int inX, int inY){
		System.out.println("SHIP LIST");
		if(!displaying)return false;
		if(shipList==null)return false;
		if(shipList.click(inX, inY)){
			if(shipList.getActiveB()==null)return true;
			activeB=shipList.getActiveB();
			activeB.setClickable(true);
			activeB.setSelected(true);
			ItemList.ItemNames temp = activeB.getId();
			switch(temp){
				case Juggernaut:
					s=new JuggernautShip(0,0,p.getAlliance());
					break;
				case Cargo:
					s= new CargoShip(0,0,p.getAlliance());
					break;
				case Normal:
					s=new NormalShip(0,0,p.getAlliance());
					break;
			}
			if(s==null){
				return true;
			}
			else{
				gc.getGrid().prepareSpawn(s,ItemList.getInt(activeB.getId(), ItemList.ItemStats.Price));
			}
			//gc.getGrid().setMode(9);
			//System.out.println("YYYAAAAYYYY");	
			update();
			return true;
		}
		return false;
	}
	
	public Ship getShip(){
		return s;
	}
	
}
