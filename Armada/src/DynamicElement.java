import java.awt.*;
import java.util.ArrayList;


/*
 * all ships/planets should extend this
 */

public class DynamicElement extends Element {

	protected int range, hull, maxHull, engine, maxEngine, speed, alliance, weapons;
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
			Rectangle rect = new Rectangle(inX,inY,inX-x,inY-y);
			//double cx = x,cy=y;
			double s = (double)Math.abs((double)inX-(double)x)/(double)Math.abs((double)inY-(double)y);
			for(DynamicElement d: delements){
				if(rect.isIn(d.getX(), d.getY())){
					if(rect.getWidth()<= this.getWidth() && rect.getHeight()<=this.getHeight()) return false;
					//return canMovePath2((int)(((double)rect.getHeight()/4) * (double)s),d.getY()+rect.getHeight()/4, 3, rect.getHeight()/2);
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
	
	public void draw(Graphics g, Rectangle viewRect){
		if(ah ==null){
			g.setColor(Color.GREEN);
			g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
			return;
		}
		if(!dead)
	    ah.draw(g, viewRect);
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

}
