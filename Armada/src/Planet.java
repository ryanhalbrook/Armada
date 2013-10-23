import java.awt.Graphics;


public class Planet extends DynamicElement{

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
		setRandomImage();//needs to be used in future
		ah=new DynamicAnimationHelper(this);
		alliance = 0;
		//hb = new HealthBar(this);
	}
	
	private void setRandomImage(){		
		int i = (int)(1 + (Math.random() *13) );
		setImage("planet/planet_" + i);
	}
}
