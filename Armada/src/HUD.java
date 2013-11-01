import java.awt.Graphics;


public class HUD extends ViewLayer {
    //protected BoundingRectangle r;
	protected Grid grid;
	protected int location;//0=static, 1+ is dynamic, 1= top left, 2= top right, 3 = bot left, 4 = bot right.  NEEDS TO BE IMPLIMENTED
	
	public HUD(int x, int y, int width, int height) {
	    //r = new BoundingRectangle(x, y, width, height);
		super(new BoundingRectangle(x,y,width,height));
		// TODO Auto-generated constructor stub
	}
	
	public HUD(int x, int y, int width, int height, Grid gr){
	    //r = new BoundingRectangle(x, y, width, height);
		super(new BoundingRectangle(x,y,width,height));
		grid=gr;
	}
	
	public HUD(int x, int y, int width, int height, Grid gr, int l){
	    //r = new BoundingRectangle(x, y, width, height);
		super(new BoundingRectangle(x,y,width,height));
		grid=gr;
		location = l;
	} 
	
	public void updateLocation(){
		if(grid==null)return;
		switch(location){
		case 0: return;
		case 1:
			r.x=5;
			r.y=TurnHUD.BAR_HEIGHT;
			break;
		case 2:
			r.x = grid.getAp().getWidth()-r.width;
			r.y=TurnHUD.BAR_HEIGHT;
			break;
		case 3:
			r.x = 5;
			r.y = grid.getAp().getHeight() - r.height;
			break;
		case 4:
			r.x = grid.getAp().getWidth()-r.width;
			r.y = grid.getAp().getHeight() - r.height;
			break;
		case 5:
			r.x = grid.getAp().getWidth()/2-r.width/2;
			r.y=TurnHUD.BAR_HEIGHT;
			break;
		case 6:
			r.x = grid.getAp().getWidth()-r.width;
			r.y = grid.getAp().getHeight()/2 - r.height/2;
			break;
		case 7:
			r.x = grid.getAp().getWidth()/2-r.width/2;
			r.y = grid.getAp().getHeight() - r.height;
			break;
		case 8:
			r.x = 5;
			r.y = grid.getAp().getHeight()/2 - r.height/2;
			break;
		}
	}
	
	public void draw(Graphics g){
		
	}

}
