package src; import src.view.*;
public class ShipButton extends Button{

	private Ship s;
	
	public ShipButton(int inX, int inY, int w, int h, GameController gc, Ship ss) {
		super(inX, inY, w, h, gc, ss.toString());
		s=ss;
	}
	
	public Ship getShip(){
		return s;
	}
	
}
