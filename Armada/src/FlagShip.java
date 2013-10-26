
public class FlagShip extends Ship{
	
	private int healing;
	
	public FlagShip(int inX, int inY, int team)
	{
		super(inX,inY, 30, 30, "FlagShip",team);
		ah = new DynamicAnimationHelper(this);
		maxHull=3000;
		hull=maxHull;
		maxEngine=2000;
		engine=maxEngine;
		speed=100000;
		weapons = 0;
		range = 0;
		healing = 1500;
	}

}
