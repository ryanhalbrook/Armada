import java.awt.*;


/*
 * all ships/planets should extend this
 */

public class DynamicElement extends Element {

	protected int range, hull, maxHull, engine, maxEngine, speed, alliance, targetable, weapons;
	protected int moved = 0;
	protected DynamicAnimationHelper dah; 
	protected HealthBar hb;
	protected boolean dead=false, canMove=true, canAttack=true;
	
	protected DynamicElement laser1,laser2,owner=null;
	protected boolean attacking = false;

	public DynamicElement(){}
	
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
		if(all!=0){
		hb = new HealthBar(this);
		}
	}
	public DynamicElement(int inX, int inY, int w, int h, double angle, String img, int all){
		super(inX,inY,w,h,angle,img);
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
		if(all!=0){
		hb = new HealthBar(this);
		}
	}
	
	
	

	public void hullTakeDamage(DynamicElement de){
		de.attack(this,"hull");
	
		
	}
	
	public void engineTakeDamage(DynamicElement de){
		de.attack(this,"engine");
	}

	/*
	 * performs certain start of turn jobs. Ex: a planet checks if it should update its alliance based off of docked ships; A planet pays the Player; A ship comes out of cloaking; etc
	 */
	public void startOfTurn(){
		moved=0;
		canAttack=true;
		canMove=true;
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

	public boolean withinMovement(int inX, int inY){
		if(distanceFrom(inX,inY) < (int)((double)(speed)*(double)((double)engine/(double)maxEngine))-moved){
			return true;
		}
		return false;
	}
	
	public void moveTo(int inX, int inY){
		dah.moveTo(inX,inY);
		moved+=distanceFrom(inX,inY);
		if(moved >= (int)((double)(speed)*(double)((double)engine/(double)maxEngine))){
			canMove=false;
		}
	}
	
	public void attack(DynamicElement de,String attacked){

		this.getDAH().attack(this, de, attacked);
	}

	public DynamicElement getLaser1() {
		return laser1;
	}

	public void setLaser1(DynamicElement laser1) {
		this.laser1 = laser1;
	}

	public DynamicElement getLaser2() {
		return laser2;
	}

	public void setLaser2(DynamicElement laser2) {
		this.laser2 = laser2;
	}

	
	public DynamicElement getOwner(){
		return owner;
	}
	public void setOwner(DynamicElement de){
		owner=de;
	}
	public void setAttack(boolean b){
		attacking=b;
	}
	
	
	public int distanceFrom(int inX, int inY){
		return (int)Math.sqrt(Math.pow(Math.abs((double)y-(double)inY),2) + Math.pow(Math.abs((double)x-(double)inX),2));
	}
	
	public void draw(Graphics g, Rectangle viewRect){
		if(dah ==null){
			g.setColor(Color.GREEN);
			g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
			return;
		}
		if(!dead)
	    dah.draw(g, viewRect);
	    if(hb!=null)
	    	hb.draw(g,viewRect);
	    if(attacking){
	    	laser1.draw(g, viewRect);
	    	laser2.draw(g, viewRect);
	    }
	    
	}
	
	public boolean withinRange(int inX, int inY) {
		update();
		return distanceFrom(inX,inY) <= range;
	}
	
	public boolean withinRange(DynamicElement de) {
		update();
		return distanceFrom(de.getX(),de.getY()) <= range;
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

	public boolean canMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public boolean canAttack() {
		return canAttack;
	}

	public void setCanAttack(boolean canAttack) {
		this.canAttack = canAttack;
	}

}
