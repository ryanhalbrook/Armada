import java.awt.*;


/*
 * all ships/planets should extend this
 */

public class DynamicElement extends Element {

	protected int range, hull, maxHull, engine, maxEngine, speed, alliance, targetable, weapons;
	protected DynamicAnimationHelper dah; 
	protected HealthBar hb;
	protected boolean dead;

    public DynamicElement() {}

	public DynamicElement(int a, int b, int w, int h, String img, int r, int maxH, int maxE, int s, int all, int t, int weap){
		super(a,b,w,h,img);
		switch(all){
		case 1:
			setImage(img+"_red");
			break;
		case 2:
			setImage(img+"_blue");
			break;
		default:
			break;
		}

		range = r;
		hull = maxH;
		maxHull = maxH;
		engine = maxE;
		maxEngine = maxE;
		speed = s;
		alliance = all;
		targetable = t;
		weapons = weap;
		dah = new DynamicAnimationHelper(this);
	}
	
	//use this one
	public DynamicElement(int inX, int inY, int w, int h, String img, int all){
		super(inX,inY,w,h,img);
		switch(all){
		case 1:
			setImage(img+"_red");
			break;
		case 2:
			setImage(img+"_blue");
			break;
		default:
			break;
		}
		alliance=all;
		targetable=1;
		dah = new DynamicAnimationHelper(this);
		hb = new HealthBar(this);
	}
	
	
	public DynamicElement(int a, int b, int w, int h, double an, String img, int r, int maxH, int maxE, int s, int all, int t, int weap){
		super(a,b,w,h, an,img);
		switch(all){
		case 1:
			setImage(img+"_red");
			break;
		case 2:
			setImage(img+"_blue");
			break;
		default:
			break;
		}
		range = r;
		hull = maxH;
		maxHull = maxH;
		engine = maxE;
		maxEngine = maxE;
		speed = s;
		alliance = all;
		targetable = t;
		weapons = weap;
		dah = new DynamicAnimationHelper(this);
	}

	public void hullTakeDamage(DynamicElement de){
		de.update();
		update();
		hull -= de.getWeapons();
		if(hull <= 0){
			hull=0;
			dead=true;
		}
	}
	
	public void engineTakeDamage(DynamicElement de){
		de.update();
		update();
		engine -= de.getWeapons();
	}

	/*
	 * performs certain start of turn jobs. Ex: a planet checks if it should update its alliance based off of docked ships; A planet pays the Player; A ship comes out of cloaking; etc
	 */
	public void startOfTurn(){

	}

	public DynamicAnimationHelper getDAH(){
		return dah;
	}
	
	public boolean isIn(int inX, int inY){
		return dah.isIn(inX, inY);
	}
	
	/*
	 * updates all stats
	 */
	public void update(){

	}

	public void moveTo(int inX, int inY){
		dah.moveTo(inX,inY);
	}
	
	public void draw(Graphics g, Rectangle viewRect){
		if(dah ==null){
			g.setColor(Color.GREEN);
			g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
			return;
		}
	    dah.draw(g, viewRect);
	    hb.draw(g,viewRect);
	    
	}
	
	public boolean withinRange(DynamicElement de) {
		update();
		int distance = (int)((de.getX()^2+de.getY()^2)^(1/2) - (getX()^2 + getY()^2)^(1/2));
		if (distance < 0) distance = -distance;
		return distance <= range;
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

	public int getWeapons() {
		return weapons;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

}
