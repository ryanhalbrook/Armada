import java.awt.Graphics;
import java.util.ArrayList;


public class Planet extends DynamicElement{
	
	protected ArrayList<Ship> dockedList;

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
}
