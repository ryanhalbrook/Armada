import java.awt.Color;
import java.awt.Graphics;


public class StatHUD extends HUD{

	public StatHUD(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public StatHUD(Grid gr){
		super(5,45,100,100,gr);
	}
	
	public void draw(Graphics g){
		if(grid==null)return;
		if(grid.getActiveE() != null){
			int dx= grid.getAp().getWidth() - 120;
			int dy= grid.getAp().getHeight() - 215;
			g.setColor(Color.WHITE);
			g.drawString("Hull: " + grid.getActiveE().getHull(), dx, dy);
			g.drawString("Engine: " + grid.getActiveE().getEngine(), dx, dy+15);
			g.drawString("Damage: " + grid.getActiveE().getWeapons(), dx, dy+30);	
			g.drawString("Speed: " + grid.getActiveE().getAdjustedSpeed(), dx, dy+45);
			g.drawString("Movement Left: " + (grid.getActiveE().getAdjustedSpeed() - grid.getActiveE().getMoved()), dx, dy+60);
			g.drawString("Can Attack: " + grid.getActiveE().canAttack(), dx, dy+75);	
		}
	}
	
	
}
