package element.ship; 
import item.Item;
import item.ItemList;
import item.ItemList.ItemNames;
import animation.ShipAnimationHelper;
import view.*;
public class JuggernautShip extends Ship {
	
	private double hullBonus = .1, engineBonus = .1;

	public JuggernautShip(int inX, int inY, int team) {
		super(inX,inY, 89, 71, "juggernaut",team);
		ah = new ShipAnimationHelper(this);
		baseMaxHull = 2000;
		maxHull=baseMaxHull;
		hull=maxHull;
		baseMaxEngine=2000;
		maxEngine=baseMaxEngine;
		engine=maxEngine;
		baseSpeed=300;
		maxSpeed=baseSpeed;
		speed=baseSpeed;
		baseWeapons=300;
		weapons = baseWeapons;
		range = 400;
		maxCargo=5;
		this.addItem(new Item(ItemList.ItemNames.Juggernaut));
		update();	
	}
	
	
}
