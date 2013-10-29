import java.awt.Color;
import java.awt.Graphics;


public class StatHUD extends HUD{

	public StatHUD(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public StatHUD(Grid gr, int t){
		super(5,45,120,100,gr, t);
	}
	
	public void draw(Graphics g){
		if(grid==null)return;
		this.updateLocation();
		if(grid.getActiveE() != null){
			g.setColor(Color.WHITE);
			g.drawString("Hull: " + grid.getActiveE().getHull()  + "/" + grid.getActiveE().getMaxHull(), x, y+g.getFontMetrics().getHeight());
			g.drawString("Engine: " + grid.getActiveE().getEngine(), x, y+g.getFontMetrics().getHeight()*2);
			g.drawString("Damage: " + grid.getActiveE().getWeapons(), x, y+g.getFontMetrics().getHeight()*3);	
			g.drawString("Speed: " + grid.getActiveE().getAdjustedSpeed(), x, y+g.getFontMetrics().getHeight()*4);
			g.drawString("Movement Left: " + (grid.getActiveE().getAdjustedSpeed() - grid.getActiveE().getMoved()), x, y+g.getFontMetrics().getHeight()*5);
			g.drawString("Can Attack: " + grid.getActiveE().canAttack(), x, y+g.getFontMetrics().getHeight()*6);	
		}
	}
	
	
}
