import java.awt.Graphics;


public class HUD extends Rectangle{

	protected Grid grid;
	
	public HUD(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public HUD(int x, int y, int width, int height, Grid gr){
		super(x,y,width,height);
		grid=gr;
	}
	
	public void draw(Graphics g){
		
	}

}
