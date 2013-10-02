import java.awt.Graphics;


/*
 * all ships/planets should extend this
 */

public class DynamicElement extends Element {

	protected int range, hull, maxHull, engine, maxEngine, speed, alliance, targetable;
	
	public DynamicElement(){
		
	}
	
	public void takeDamage(DynamicElement de){
		
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
		
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getHull() {
		return hull;
	}

	public void setHull(int hull) {
		this.hull = hull;
	}

	public int getMaxHull() {
		return maxHull;
	}

	public void setMaxHull(int maxHull) {
		this.maxHull = maxHull;
	}

	public int getEngine() {
		return engine;
	}

	public void setEngine(int engine) {
		this.engine = engine;
	}

	public int getMaxEngine() {
		return maxEngine;
	}

	public void setMaxEngine(int maxEngine) {
		this.maxEngine = maxEngine;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAlliance() {
		return alliance;
	}

	public void setAlliance(int alliance) {
		this.alliance = alliance;
	}

	public int getTargetable() {
		return targetable;
	}

	public void setTargetable(int targetable) {
		this.targetable = targetable;
	}
	
}
