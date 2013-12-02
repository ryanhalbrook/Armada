package element.ship; 
import item.Item;
import item.ItemList;
import item.ItemList.ItemNames;
import animation.ShipAnimationHelper;
import view.*;
public class ScoutShip extends Ship{

	public ScoutShip(int inX, int inY, int team) {
		super(inX,inY, 39, 65, "scout",team);
		ah = new ShipAnimationHelper(this);
		baseMaxHull=250;
		maxHull=baseMaxHull;
		hull=maxHull;
		baseMaxEngine=250;
		maxEngine=baseMaxEngine;
		engine=maxEngine;
		baseSpeed=1000;
		maxSpeed=baseSpeed;
		speed=baseSpeed;
		baseWeapons=100;
		weapons=baseWeapons;
		range=100;
		maxCargo=1;

		//this.addItem(new Item(ItemList.ItemNames.Scout));
		update();
		//Some Examples of my testing
		//this.addItem(new Item(ItemList.ItemNames.OverloadWeapons));
		//this.addItem(new Item(ItemList.ItemNames.ScalingWeaponsUpgrade));
		//Item i = new Item(ItemList.ItemNames.HullPlate);
		//this.addItem(i);
		//this.removeItem(i);
	}

}
