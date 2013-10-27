import java.awt.Graphics;
import java.util.ArrayList;


public class Planet extends DynamicElement{
	
	protected ArrayList<Ship> dockedList;
	protected int upgradeLevel;
	private int[][] storeList = new int[3][10]; // The store should be handled by another class
	//private Teleporter tp; // teleport is a simple change in X and Y, there is no need for it to be an entire class
	private HealthBar hb;//health bars don't account for the PLanet's state yet, so it isnt ready to add to the Planet yet.  Also, you forgot to add the hb to the draw method

	public Planet(int a, int b, int w, int h, String img, int r,
			int maxH, int maxE, int s, int all, int t, int weap) {
		//super(a, b, w, h, img, r, maxH, maxE, s, all, t, weap);
		
	}
	
	public Planet(){
		super();
		targetable = true;
		angle=0;
		speed=0; maxEngine =0; engine=0;
		maxHull=6000; hull=maxHull;
		canAttack=false; canMove= false;
		x = (int)((double)Grid.GRID_WIDTH* Math.random());
		y = (int)((double)Grid.GRID_HEIGHT* Math.random());
		width = 50 +(int)(Math.random() * 100.0);
		height = width;
		range = (int)((double)width * 1.5);
		setRandomImage();//needs to be used in future
		ah=new DynamicAnimationHelper(this);
		alliance = 0;
		dockedList = new ArrayList<Ship>();
		upgradeLevel = 0;
		//hb = new HealthBar(this);
	}
	
	private void setRandomImage(){		
		int i = (int)(1 + (Math.random() *13) );
		setImage("planet/planet_" + i);
	}
	
	public void dock(Ship s){
		if(alliance == 0 || alliance == s.getAlliance()){
			s.setPlanetDocked(this);
			dockedList.add(s);
			System.out.println(s + " docked at " + this);
		}
	}
	
	public void startOfTurn(){
		if(alliance==0 && onlyOneAllianceDocked()){
			alliance = dockedList.get(0).getAlliance();
		}
		if(alliance!=0){
			//player of alliance # gets gold
			System.out.println("Pay player " + alliance);
		}
		System.out.println("Ships docked: " + dockedList.size());
		
	}
	
	public void unDock(Ship s){
		System.out.println("Removed " + s + " from " + this);
		dockedList.remove(s);
		
	}
	
	private boolean onlyOneAllianceDocked(){
		if(dockedList == null) return false;
		if(dockedList.size()==0)return false;
		int al = dockedList.get(0).getAlliance();
		for(DynamicElement d: dockedList){
			if(d.getAlliance()!=al) return false;
		}
		return true;
	}
	/*
	 * There are far too few situations accounted for in this code and needs to be removed.
	public void upgrade(){
		if (alliance == 0) {
			if (dockedList.size() == 1) {  // What if I have more than one ship docked of the same alliance?
				alliance = dockedList.get(0).alliance; //NEVER directly access an object's variables.  Use .getAlliance()
				upgradeLevel++;
			}
			else if (dockedList.size() == 0) System.out.println("Cannot upgrade unless ship is docked.");//I can't think of any situation in which this would happen.  If alliance==0, then the Planet shouldnt even have an upgrade option
			else System.out.println("Planet can only be upgraded if one ship is docked.");//When did we decide that?
		}
		else if (upgradeLevel == 1) {
			if (dockedList.size() == 1 && dockedList.get(0).alliance != alliance) alliance = dockedList.get(0).alliance;//again, NEVER access variable directly.  Also, why is alliance being set here? It should already have an alliance. Also, this if statement doesn't make sense in general.  Why would an enemy ship be docked? Why would upgrade be called in this situation?  why, again, is there only one ship? I already have a method that you could use to check if every ship is the same alliance, so you could use that instead of assuming that there is one ship and setting it to that alliance.  Use onlyOneAllianceDocked() 
			else {
				upgradeLevel++;
				
			}
		}
		//at this point, I stopped commenting on why I removed this method
		else if (upgradeLevel == 2) {
			if (dockedList.size() == 1 && dockedList.get(0).alliance != alliance){
				alliance = dockedList.get(0).alliance;
				upgradeLevel = 0;
			}
			else {
				upgradeLevel++;
				storeList[0][0]++;
				storeList[0][1]++;
				storeList[0][2]++;
				hb = new HealthBar(this);
			}
		}
		else if (upgradeLevel == 3) {
			if (dockedList.size() == 1 && dockedList.get(0).alliance != alliance){
				alliance = dockedList.get(0).alliance;
				upgradeLevel = 0;
			}
			else {
				upgradeLevel++;
				//tp = new Teleporter(x, y);
			}
		}
	}
	
	public int getUpgradeLevel(){
		return upgradeLevel;
	}
	public void purchaseStore(){
	}*/
}
