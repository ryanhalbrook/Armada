
public class NormalShip extends Ship{

	public NormalShip(int inX, int inY, int team) {
		super(inX,inY, 30, 30, "fighter",team);
		dah = new DynamicAnimationHelper(this);
		maxHull=1000;
		hull=maxHull;
		maxEngine=1000;
		engine=maxEngine;
		speed=300;
		weapons = 300;
		range = 300;
	}

	
	
}
