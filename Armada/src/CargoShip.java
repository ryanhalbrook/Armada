
public class CargoShip extends Ship {
	
	private double hullBonus = .1, engineBonus = .1;

	public CargoShip(int inX, int inY, int team) {
		super(inX,inY, 89, 71, "boarding",team);//just temporary until we have a cargo ship .png
		ah = new ShipAnimationHelper(this);
		baseMaxHull = 1500;
		maxHull=baseMaxHull;
		hull=maxHull;
		baseMaxEngine=4000;
		maxEngine=baseMaxEngine;
		engine=maxEngine;
		baseSpeed=600;
		maxSpeed=baseSpeed;
		speed=baseSpeed;
		baseWeapons=100;
		weapons = baseWeapons;
		range = 220;
		maxCargo=25;
		this.addItem(new Item(ItemList.ItemNames.Cargo));
		update();	
	}
	
	
}
