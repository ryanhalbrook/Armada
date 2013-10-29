import java.awt.Graphics;


public class HUD extends Rectangle{

	protected Grid grid;
	protected int location;//0=static, 1+ is dynamic, 1= top left, 2= top right, 3 = bot left, 4 = bot right.  NEEDS TO BE IMPLIMENTED
	
	public HUD(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public HUD(int x, int y, int width, int height, Grid gr){
		super(x,y,width,height);
		grid=gr;
	}
	
	public HUD(int x, int y, int width, int height, Grid gr, int l){
		super(x,y,width,height);
		grid=gr;
		location = l;
	}
	
	public void updateLocation(){
		if(grid==null)return;
		switch(location){
		case 0: return;
		case 1:
			x=5;
			y=TurnHUD.BAR_HEIGHT;
			break;
		case 2:
			x = grid.getAp().getWidth()-this.width;
			y=TurnHUD.BAR_HEIGHT;
			break;
		case 3:
			x = 5;
			y = grid.getAp().getHeight() - this.height;
			break;
		case 4:
			x = grid.getAp().getWidth()-this.width;
			y = grid.getAp().getHeight() - this.height;
			break;
		case 5://mid top
			x = grid.getAp().getWidth()/2 - this.width/2;
			y=TurnHUD.BAR_HEIGHT;
			break;
		case 6://mid right
			x = grid.getAp().getWidth()-this.width;
			y = grid.getAp().getHeight()/2 - this.height/2;
			break;
		case 7://mid bottom
			x = grid.getAp().getWidth()/2 - this.width/2;
			y = grid.getAp().getHeight() - this.height;
			break;
		case 8://mid left
			x = 5;
			y = grid.getAp().getHeight()/2 - this.height/2;
			break;
		}
	}
	
	public void draw(Graphics g){
		
	}

}
