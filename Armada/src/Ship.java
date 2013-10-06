import java.awt.Point;


public class Ship extends DynamicElement{

	/*
	 * list is a 2 dimensional array of ints.  The first position number represents the priority/order that items/buffs/upgrades should be added
	 * Typically, straight modifiers (such as items that give +20 to a stat) are in the first row (row 0)
	 * Percentage modifiers (such as buffs and items that give + 20% to a stat) are in the next row and are calculated after the previous (this is row 1)
	 * Situational items are found in row 3 and their presence will be checked in specific situations in which they will be used (such as being selected or a shield automatically going off and consuming itself after use)
	 * The columns represent each buff / item
	 * The number at [row][column] represents how many of those are present
	 */
	private int[][] list = new int[3][10];//numbers subject to change
	
	//Every ship that extends this class needs the following line, but with values assigned to each.  Whenever update() is called, the value of the corresponding stats need to be recalculated starting with these and then adding in each appropriate buff, item, and upgrade.
	//protected final int DEFAULT_HULL, DEFAULT_MAX_HULL, DEFAULT_SPEED, DEFAULT_BOARD, DEFAULT_ENGINE, DEFAULT_MAX_ENGINE, DEFAULT_MAX_CARGO, DEFAULT_RANGE;
	protected int board;
	
	protected int cargo, maxCargo;
	
	/*
	 * @param a The alliance of the ship. 0 for neutral, 1 for p1, 2 for p2
	 */

	public Ship(int a, int b, int w, int h, String img, int r,
			int maxH, int maxE, int s, int all, int t, int weap) {
		super(a, b, w, h, img, r, maxH, maxE, s, all, t, weap);
		
	}
	public Ship(int a, int b, int w, int h, double an, String img, int r,
			int maxH, int maxE, int s, int all, int t, int weap) {
		super(a, b, w, h, an, img, r, maxH, maxE, s, all, t, weap);
		
	}
	/*
	 * given an item, it will
	 */
	public void receiveItem(Item i){
		if (i.getIdentity() == 0) list[0][1]++;
		else if (i.getIdentity() == 1) list[0][2]++;
		else if (i.getIdentity() == 2) list[0][3]++;
	}
	
	public boolean isIn(int inX, int inY){
		if(inX >(this.x) && inX < (this.x+width) && inY>(this.y) && inY < (this.y+height)){
			return true;
		}
		return false;
		
	}
	
	public void move(int inX, int inY){
		x=inX;
		y=inY;
	}
	
	public int getCargo() {
		return cargo;
	}

	public void setCargo(int cargo) {
		this.cargo = cargo;
	}

	public int getMaxCargo() {
		return maxCargo;
	}

	public void setMaxCargo(int maxCargo) {
		this.maxCargo = maxCargo;
	}
	
}
