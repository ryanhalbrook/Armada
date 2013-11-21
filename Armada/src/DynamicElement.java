package src; import src.view.*;
import java.awt.*;
import java.util.ArrayList;


/*
 * all ships/planets should extend this
 */

public class DynamicElement extends Element {

	protected int range, hull, maxHull, engine, maxEngine, speed, alliance, weapons, maxSpeed, maxWeapons;
	protected int moved = 0;
	
	protected HealthBar hb;
	protected boolean dead=false, canMove=true, canAttack=true;
	
	protected Element laser1,laser2;
	
	protected boolean attacking = false;
	

	

	public DynamicElement(){}
	
	//use this one
	public DynamicElement(int inX, int inY, int w, int h, String img, int all){
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
		x = inX;
		y = inY;
		width = w;
		height = h;
		angle=0;
		index=-1;
		alliance=all;
		targetable=true;
		ah=new DynamicAnimationHelper(this);
		
		if(all!=0){
			hb = new HealthBar(this);
		}
	}
	
	public DynamicElement(int inX, int inY, int w, int h, double angle, String img, int all){
		
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
		x = inX;
		y = inY;
		width = w;
		height = h;
		this.angle=angle;
		index=-1;
		alliance=all;
		targetable=true;
		ah=new DynamicAnimationHelper(this);
		
		if(all!=0){
		hb = new HealthBar(this);
		}
	}
	
	/*
	 * method in making.  trying to get canMovePath() to be more efficient
	 */
	public boolean canMovePath2(int inX, int inY, ArrayList<DynamicElement> delements){
		if(this.canMove() && this.withinMovement(inX, inY)){
			for(DynamicElement d: delements){
				if(d!=this && (d.isIn(inX, inY) || d.distanceFrom(inX, inY) < d.getWidth()/2 + this.getWidth()/2)){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	
	public boolean canMovePath(int inX, int inY, ArrayList<DynamicElement> delements){
		if(this.canMove() && this.withinMovement(inX, inY)){
			double cx = x,cy=y;
			double s = (double)Math.abs((double)inY-(double)y)/(double)Math.abs((double)inX-(double)x);
			while((cx<inX && x<inX) || (cx>inX && x>inX) || (cy<inY && y<inY) || (cy>inY && y>inY) ){
				
				for(DynamicElement d: delements){
					//System.out.println(x+", "+y+" to " + inX + ", " + inY + " at " + cx +", " + cy + " S=" + s);
					if(d.isIn((int)cx, (int)cy) && d!=this){
						//System.out.println("false");
						return false;
					}
				}
				if(inX < x){
					if(s>1){
						cx -=(double)1/s;
					}
					else cx--;
				}
				else{
					if(s>1){
						cx += (double)1/s;
					}
					else cx++;
				}
				
				if(inY < y){
					if(s<1){
						cy -=(double)s;
					}
					else cy--;
				}
				else{
					if(s<1){
						cy += (double)s;
					}
					else cy++;
				}
				//System.out.println("true");
					
			}
				return true;
		}
		//System.out.println("false");
		return false;
	}

	public void hullTakeDamage(DynamicElement de){
		de.attack(this,"hull");
	
		
	}
	public void engineTakeDamage(DynamicElement de){
		de.attack(this,"engine");
	}
	public synchronized void attackHullHelper(DynamicElement target){
		target.update();
		update();
		target.setHull(target.getHull()-getWeapons()/2);
		if(target.getHull() <= 0){
			target.setHull(0);
			target.setDead(true);
		}
	}
	public synchronized void attackEngineHelper(DynamicElement target){
		target.update();
		update();
		target.setEngine(target.getEngine()-getWeapons()/2);
		if(target.getEngine() <= 0){
			target.setEngine(0);
		}
	}
		/*
	 * Calculates distance between the two inputs, order does not matter
	 */
	public int distance(DynamicElement e){
		return (int)Math.sqrt(Math.pow(Math.abs((double)this.getY()-(double)e.getY()),2) + Math.pow(Math.abs((double)this.getX()-(double)e.getX()),2));
	}
	
	public int distance(int inX, int inY){
		return (int)Math.sqrt(Math.pow(Math.abs((double)this.getY()-(double)inY),2) + Math.pow(Math.abs((double)this.getX()-(double)inX),2));
	}
	

	/*
	 * performs certain start of turn jobs. Ex: a planet checks if it should update its alliance based off of docked ships; A planet pays the Player; A ship comes out of cloaking; etc
	 */
	public void startOfTurn(){
		moved=0;
		canAttack=true;
		canMove=true;		
	}

	
	
	public boolean isIn(int inX, int inY){
		return ah.isIn(inX, inY);
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
		ah.moveTo(inX,inY);
		moved+=distanceFrom(inX,inY);
		if(moved >= (int)((double)(speed)*(double)((double)engine/(double)maxEngine))){
			canMove=false;
		}
	}
	
	public void attack(DynamicElement de,String attacked){

		this.getDAH().attack(this, de, attacked);
	}
	
	public DynamicAnimationHelper getDAH(){
		return (DynamicAnimationHelper) ah;
	}
	
	public Element getLaser1() {
		return laser1;
	}

	public void setLaser1(Element laser1) {
		this.laser1 = laser1;
	}

	public Element getLaser2() {
		return laser2;
	}

	public void setLaser2(Element laser2) {
		this.laser2 = laser2;
	}

	public void setAttack(boolean b){
		attacking=b;
	}
	
	public int distanceFrom(int inX, int inY){
		return (int)Math.sqrt(Math.pow(Math.abs((double)y-(double)inY),2) + Math.pow(Math.abs((double)x-(double)inX),2));
	}
	
	public void draw(Graphics g, BoundingRectangle viewRect){
		if(ah ==null){
			g.setColor(Color.GREEN);
			g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
			return;
		}
		if(!dead){
			ah.draw(g, viewRect);
		}
	    if(hb!=null)
	    	hb.draw(g,viewRect);
	    if(attacking){
	    	laser1.draw(g, viewRect);
	    	laser2.draw(g, viewRect);
	    }
	    
	    
	}
	
	public boolean withinRange(int inX, int inY) {
		return distanceFrom(inX,inY) <= range;
	}
	
	public boolean withinRange(DynamicElement de) {
		return distanceFrom(de.getX(),de.getY()) <= range;
	}

	public int distanceFrom(DynamicElement de ){
		return this.distanceFrom(de.getX(), de.getY());
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
		if(hull <= 0){
			dead=true;
		}
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
	
	public int getAdjustedSpeed(){
		return (int)((double)speed * ((double)engine/(double)maxEngine));
	}

	public void setSpeed(int s) {
		speed = s;
	}

	public int getAlliance() {
		return alliance;
	}

	public void setAlliance(int all) {
		alliance = all;
		switch(all){
		case 1:
			setImage(image.substring(0, image.length()-5)+"_red");
			break;
		case 2:
			setImage(image.substring(0, image.length()-4)+"_blue");
			break;
		default:
			break;
		}
		ah.changeImage(getImage());
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

	public int getMoved() {
		return moved;
	}
	
	public int getMovementLeft(){
		return this.getAdjustedSpeed()  -moved;
	}
	
}
