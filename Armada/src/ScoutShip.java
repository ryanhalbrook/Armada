package src; import src.view.*;
public class ScoutShip extends Ship{

	public ScoutShip(int inX, int inY, int team) {
		super(inX,inY, 39, 65, "scout",team);
		ah = new ShipAnimationHelper(this);
		baseMaxHull = 1000;
		maxHull=baseMaxHull;
		hull=maxHull;
		baseMaxEngine=1000;
		maxEngine=baseMaxEngine;
		engine=maxEngine;
		baseSpeed=500;
		maxSpeed=baseSpeed;
		speed=baseSpeed;
		baseWeapons=300;
		weapons = baseWeapons;
		range = 300;
		maxCargo=3;
		//Some Examples of my testing
		//this.addItem(new Item(ItemList.ItemNames.OverloadWeapons));
		//this.addItem(new Item(ItemList.ItemNames.ScalingWeaponsUpgrade));
		//Item i = new Item(ItemList.ItemNames.HullPlate);
		//this.addItem(i);
		//this.removeItem(i);
	}

}
