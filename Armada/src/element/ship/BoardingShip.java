package element.ship; 
import item.Item;
import item.ItemList;
import item.ItemList.ItemNames;
import animation.ShipAnimationHelper;
import view.*;
public class BoardingShip extends Ship {

	public BoardingShip(int inX, int inY, int team) {
		super(inX,inY, 50, 50, "boarding",team);
		ah = new ShipAnimationHelper(this);
		baseMaxHull=300;
		maxHull=baseMaxHull;
		hull=maxHull;
		baseMaxEngine=400;
		maxEngine=baseMaxEngine;
		engine=maxEngine;
		baseSpeed=750;
		maxSpeed=baseSpeed;
		speed=baseSpeed;
		baseWeapons=250;
		weapons=baseWeapons;
		range=250;
		maxCargo=5;
		board=.5f;


		//this.addItem(new Item(ItemList.ItemNames.Boarding));
		update();
	}
	
	
}
