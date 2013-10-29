
public class NormalShip extends Ship{

	public NormalShip(int inX, int inY, int team) {
		super(inX,inY, 30, 30, "fighter",team);
		ah = new DynamicAnimationHelper(this);
		baseMaxHull = 1000;
		maxHull=baseMaxHull;
		hull=maxHull;
		baseMaxEngine=100;
		maxEngine=baseMaxEngine;
		engine=maxEngine;
		baseSpeed=300;
		speed=baseSpeed;
		baseWeapons=300;
		weapons = baseWeapons;
		range = 300;
	}

	
	
}
