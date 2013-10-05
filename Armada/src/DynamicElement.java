import java.awt.*;


/*
 * all ships/planets should extend this
 */

public class DynamicElement extends Element {

	protected int range, hull, maxHull, engine, maxEngine, speed, alliance, targetable, weapons;
	
	public DynamicElement(int a, int b, int w, int h, int r, int maxH, int maxE, int s, int all, int t, int weap){
		x = a;
		y = b;
		width = w;
		height = h;
		range = r;
		hull = maxH;
		maxHull = maxH;
		engine = maxE;
		maxEngine = maxE;
		speed = s;
		alliance = all;
		targetable = t;
		weapons = weap;
	}
	
	public void takeDamage(DynamicElement de){
		hull -= de.getDamage();
	}
	
	/*
	 * performs certain start of turn jobs. Ex: a planet checks if it should update its alliance based off of docked ships; A planet pays the Player; A ship comes out of cloaking; etc
	 */
	public void startOfTurn(){
	
	}
	
	
	/*
	 * updates all stats
	 */
	public void update(){
		
	}
	
	public void draw(Graphics g){
	System.out.println("Drawing element");
	    g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
	}

	public int getRange() {
		return range;
	}

	public void setRange(int r) {
		range = r;
	}

	public int getHull() {
		return hull;
	}

	public void setHull(int h) {
		hull = h;
	}

	public int getMaxHull() {
		return maxHull;
	}

	public void setMaxHull(int maxH) {
		maxHull = maxH;
	}

	public int getEngine() {
		return engine;
	}

	public void setEngine(int e) {
		engine = e;
	}

	public int getMaxEngine() {
		return maxEngine;
	}

	public void setMaxEngine(int maxE) {
		maxEngine = maxE;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int s) {
		speed = s;
	}

	public int getAlliance() {
		return alliance;
	}

	public void setAlliance(int all) {
		alliance = all;
	}

	public int getTargetable() {
		return targetable;
	}

	public void setTargetable(int t) {
		targetable = t;
	}
	
	public void setWeapons(int weap) {
		weapons = weap;
	}
	}
	
}
