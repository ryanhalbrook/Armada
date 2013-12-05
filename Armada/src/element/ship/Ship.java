package element.ship;
import view.*;
import item.Item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import element.Bridge;
import element.DynamicElement;
import element.Element;
import element.planet.Planet;
import element.status.StatEditor;
import animation.ShipAnimationHelper;


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
	protected double perHull, perEng;
	protected float board;
	protected ArrayList<Item> items;
	protected Ship trader;
	private static final int DOCK_RANGE=200;
	
	//Every ship that extends this class needs the following line, but with values assigned to each.  Whenever update() is called, the value of the corresponding stats need to be recalculated starting with these and then adding in each appropriate buff, item, and upgrade.
	//protected final int DEFAULT_HULL, DEFAULT_MAX_HULL, DEFAULT_SPEED, DEFAULT_BOARD, DEFAULT_ENGINE, DEFAULT_MAX_ENGINE, DEFAULT_MAX_CARGO, DEFAULT_RANGE;

	
	protected int cargo=0, maxCargo;
	protected Bridge bridge;
	protected boolean boarding =false,docking =false,docked=false,teleporting=false,trading=false;
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
		if(cargo < maxCargo){
			StatEditor.addItem(this, i);
			cargo++;
		}
		
	}
	
	public void removeItem(Item i){
		StatEditor.removeItem(this, i);
		cargo--;
		System.out.println(cargo);
	}
	
	public void resetStats(){
		perHull=(double)hull/(double)maxHull;
		perEng=(double)engine/(double)maxEngine;
		weapons = baseWeapons;
		speed= baseSpeed;
		maxHull=baseMaxHull;
		maxEngine=baseMaxEngine;
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
		if(planetDocked==null&&!docked)return false;
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
		hull=maxHull;
	}
	
	public void fullHealEngine(){
		engine=maxEngine;
	}
	
	public void fullHeal(){
		fullHealEngine();
		fullHealHull();
	}
	
	public boolean canGetCargo(){
		if(cargo<maxCargo)return true;
		return false;
	}

	public Planet getPlanetDocked() {
		return planetDocked;
	}

	public void incWeaponsFlat(int i){
		weapons += i;
	}
	public void dock(Planet p){
		//TODO: make it "untrade"/"undock" first
		
		this.getSAH().dock(this, p);
	}
	public void undock(){
		this.getSAH().dock(this, null,6);
	}
	public void setDocking(boolean b){
		docking=b;
	}
	public boolean isDocking(){
		return docking;
	}
	public void setPlanetDocked(Planet inP) {
		if(this.planetDocked !=null && this.planetDocked != inP){
			//docking=true;
			//undock();
			this.planetDocked.unDock(this);
		}
		else
			dock(inP);
		this.planetDocked = inP;
	}
	public void moveTo(int x,int y){
		getSAH().moveTo(x, y);
		moved+=distanceFrom(x,y);
		if(moved >= (int)((double)(speed)*(double)((double)engine/(double)maxEngine))){
			canMove=false;
		}
	}
	public void board(Ship target){
		
		
		this.getSAH().board(this, target);
		//return true;
	}
	
	public boolean calculateBoard(Ship target){
		/*if(board==0){
			board=0.1f;
		}*/
		double rand = Math.random();
		double perc = (double)target.getEngine()/(double)target.getMaxEngine();
		System.out.println((rand*0.5) + "+" + (rand*0.5*perc) + " = " + ((rand*0.5)+((rand*0.5)*perc)) + " < " + board + "?" + " " + ((rand*0.5)+((rand*0.5)*perc)<board));
		return ((rand*0.5)+((rand*0.5)*perc)<board);
	}
	
	public void unboard(){
		if(bridge!=null)
			this.getSAH().board(this, null,6);
	}
	public void draw(Graphics g, BoundingRectangle viewRect){
		
		if(isDocking()){
			if(bridge!=null&&bridge.getWidth()!=0){
	    		bridge.draw(g, viewRect);
	    	}
		}
		else if(isDocked()){
			g.setColor(Color.WHITE);
			g.drawString("DOCKED", x - viewRect.getX()-width/2, y - viewRect.getY() - height);
			if(bridge!=null&&bridge.getWidth()!=0){
	    		bridge.draw(g, viewRect);
	    	}
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
		super.draw(g,viewRect);
	}
	
	public void weaponsPerInc(int i){
		double d = ((double)i)/100.0 +1;
		weapons *= d;
	}
	/*
	public void maxWeaponsPerInc(int i){
		double d = ((double)i)/100 +1;
		maxWeapons *= d;
	}*/
	
	public void enginesPerInc(int i){
		double d = ((double)i)/100 +1;
		engine *= d;
	}
	
	public void maxEnginesPerInc(int i){
		double d = ((double)i)/100 +1;
		maxEngine *= d;
	}
	
	public void hullPerInc(int i){
		double d = ((double)i)/100 +1;
		hull *= d;
	}
	
	public void maxHullPerInc(int i){
		double d = ((double)i)/100 +1;
		maxHull *= d;
	}
	
	public void speedPerInc(int i){
		double d = ((double)i)/100 +1;
		speed *= d;
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

	
	public void setDocked(boolean docked) {
		this.docked = docked;
	}

	public boolean isTeleporting() {
		return teleporting;
	}

	public void setTeleporting(boolean teleporting) {
		this.teleporting = teleporting;
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

	public double getPerHull() {
		return perHull;
	}

	public void setPerHull(int perHull) {
		this.perHull = perHull;
	}

	public double getPerEng() {
		return perEng;
	}

	public void setPerEng(int perEng) {
		this.perEng = perEng;
	}
	
	public void incPerHull(int i){
		perHull+=i;
	}
	
	public void incPerEng(int i){
		perEng+=i;
	}
	public boolean cargoNotFull(){
		return cargo<maxCargo;
	}
	public void trade(Ship s){
		//TODO: make it "untrade"/"undock" first
		getSAH().dock(this, s);
		trader=s;
		s.setTrader(this);
		trading=true;
		trader.setTrading(true);
	}
	public void untrade(){
		if(trader==null)
			return;
		if(isDocked())
			undock();
		else
			trader.undock();
	}
	public boolean isTrading(){
		return trading;
	}
	public Ship getTrader(){
		return trader;
	}
	public void setTrader(Ship s){
		trader=s;
	}
	public void setTrading(boolean in){
		trading = in;
	}

	public static int getDockRange() {
		return DOCK_RANGE;
	}
	
}
