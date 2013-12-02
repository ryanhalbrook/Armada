package element.ship; 
import item.Item;
import item.ItemList;
import item.ItemList.ItemNames;
import animation.ShipAnimationHelper;
import view.*;
public class FighterShip extends Ship {
	
	//private double hullBonus = .1, engineBonus = .1;

	public FighterShip(int inX, int inY, int team) {
		super(inX,inY, 89, 71, "fighter",team);
		ah = new ShipAnimationHelper(this);
		baseMaxHull=350;
		maxHull=baseMaxHull;
		hull=maxHull;
		baseMaxEngine=350;
		maxEngine=baseMaxEngine;
		engine=maxEngine;
		baseSpeed=500;
		maxSpeed=baseSpeed;
		speed=baseSpeed;
		baseWeapons=400;
		weapons=baseWeapons;
		range=400;
		maxCargo=3;


		//this.addItem(new Item(ItemList.ItemNames.Fighter));
		update();	
	}
	
	
}
