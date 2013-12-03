package element.ship; 
import item.Item;
import item.ItemList;
import item.ItemList.ItemNames;
import animation.ShipAnimationHelper;
import view.*;
public class JuggernautShip extends Ship {
	
	//private double hullBonus = .1, engineBonus = .1;

	public JuggernautShip(int inX, int inY, int team) {
		super(inX,inY, 130, 104, "juggernaut",team);// 89 71
		ah = new ShipAnimationHelper(this);
		baseMaxHull=500;
		maxHull=baseMaxHull;
		hull=maxHull;
		baseMaxEngine=500;
		maxEngine=baseMaxEngine;
		engine=maxEngine;
		baseSpeed=550;
		maxSpeed=baseSpeed;
		speed=baseSpeed;
		baseWeapons=650;
		weapons=baseWeapons;
		range=650;
		maxCargo=5;

		
		this.addItem(new Item(ItemList.ItemNames.Juggernaut));
		update();	
	}
	
	
}
