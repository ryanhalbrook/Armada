import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;


public class Ship extends DynamicElement{

	/*
	 * list is a 2 dimensional array of ints.  The first position number represents the priority/order that items/buffs/upgrades should be added
	 * Typically, straight modifiers (such as items that give +20 to a stat) are in the first row (row 0)
	 * Percentage modifiers (such as buffs and items that give + 20% to a stat) are in the next row and are calculated after the previous (this is row 1)
	 * Situational items are found in row 3 and their presence will be checked in specific situations in which they will be used (such as being selected or a shield automatically going off and consuming itself after use)
	 * The columns represent each buff / item
	 * The number at [row][column] represents how many of those are present
	 */
	protected int[][] list = new int[3][10];//numbers subject to change
	protected Planet planetDocked;
	protected int baseWeapons, baseSpeed, baseMaxHull, baseMaxEngine;
	protected ArrayList<Item> items;
	
	//Every ship that extends this class needs the following line, but with values assigned to each.  Whenever update() is called, the value of the corresponding stats need to be recalculated starting with these and then adding in each appropriate buff, item, and upgrade.
	//protected final int DEFAULT_HULL, DEFAULT_MAX_HULL, DEFAULT_SPEED, DEFAULT_BOARD, DEFAULT_ENGINE, DEFAULT_MAX_ENGINE, DEFAULT_MAX_CARGO, DEFAULT_RANGE;

	
	protected int cargo, maxCargo;
	protected Bridge bridge;
	protected boolean boarding =false;
	Element rope,hook;
	public Ship(int team) {
	    super();
	    ah=new ShipAnimationHelper(this);
	}
	
	//use this one
	public Ship(int inX, int inY, int w, int h, String img, int all){
		super(inX,inY, w, h, img, all);
		items=new ArrayList<Item>();
		ah=new ShipAnimationHelper(this);
	}
	public Ship(int inX, int inY, int w, int h, double an, String img, int all){
		super(inX,inY, w, h,an, img, all);
		ah=new ShipAnimationHelper(this);
	}
	/*
	 * @param a The alliance of the ship. 0 for neutral, 1 for p1, 2 for p2
	 */
	 
	
	/*
	 * given an item, it will
	 */
	public void addItem(Item i){
		StatEditor.addItem(this, i);
	}
	
	public void removeItem(Item i){
		StatEditor.removeItem(this, i);
	}
	
	public void resetStats(){
		weapons = baseWeapons;
		speed= baseSpeed;
		maxHull=baseMaxHull;
		maxEngine=baseMaxEngine;
	}
	
	public void move(int inX, int inY){
		x=inX;
		y=inY;
	}
	public ShipAnimationHelper getSAH(){
		return (ShipAnimationHelper)ah;
	}
	public void setSAH(ShipAnimationHelper s){
		ah=s;
	}
	public int getCargo() {
		return cargo;
	}

	public boolean isDocked(){
		if(planetDocked==null)return false;
		return true;
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
	
	public void fullHealHull(){
		System.out.println("Setting hull to: " + maxHull);
		hull=maxHull;
	}
	
	public void fullHealEngine(){
		engine=maxEngine;
	}
	
	public void fullHeal(){
		fullHealEngine();
		fullHealHull();
	}

	public Planet getPlanetDocked() {
		return planetDocked;
	}

	public void incWeaponsFlat(int i){
		weapons += i;
	}
	
	public void setPlanetDocked(Planet inP) {
		if(this.planetDocked !=null && this.planetDocked != inP){
			this.planetDocked.unDock(this);
		}
		this.planetDocked = inP;
	}
	public void board(Ship target){
		this.getSAH().board(this, target);
		//unboard();
	}
	public void unboard(){
		if(bridge!=null)
			this.getSAH().board(this, null,6);
	}
	public void draw(Graphics g, BoundingRectangle viewRect){
		super.draw(g,viewRect);
		if(isDocked()){
			g.setColor(Color.WHITE);
			g.drawString("DOCKED", x - viewRect.getX()-width/2, y - viewRect.getY() - height);
		}
		if(boarding){
	    	if(rope!=null&&rope.getWidth()!=0 )
	    		rope.draw(g, viewRect);
	    	if(hook!=null)
	    		hook.draw(g,viewRect);
	    	if(bridge!=null&&bridge.getWidth()!=0){
	    		bridge.draw(g, viewRect);
	    	}
	    }
	}
	
	public void weaponsPerInc(int i){
		double d = ((double)i)/100 +1;
		weapons *= d;
	}
	
	public void incSpeedFlat(int i){
		speed += i;
	}
	public void incMaxEngineFlat(int i){
		maxEngine += i;
	}
	
	public void incMaxHullFlat(int i){
		maxHull += i;
	}
	
	public void update(){
		StatEditor.update(this);
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public void upgrade() {
		
	}
	public Element getRope() {
		return rope;
	}

	public void setRope(Element rope) {
		this.rope = rope;
	}
	public boolean isBoarding() {
		return boarding;
	}

	public void setBoarding(boolean boarding) {
		this.boarding = boarding;
	}

	

	public Element getHook() {
		return hook;
	}

	public void setHook(Element hook) {
		this.hook = hook;
	}

	public Bridge getBridge() {
		return bridge;
	}

	public void setBridge(Bridge bridge) {
		this.bridge = bridge;
	}
}
