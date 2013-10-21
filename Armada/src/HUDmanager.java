import java.awt.Graphics;
import java.util.ArrayList;


public class HUDmanager {
	
	protected Grid grid;

	protected ArrayList<HUD> huds;
	protected HUD mode;
	
	public HUDmanager(Grid gr){
		grid = gr;
		huds = new ArrayList<HUD>();
		mode = new ModeHUD(gr);
		huds.add(mode);
	}
	
	public void draw(Graphics g){
		for(HUD h: huds){
			h.draw(g);
		}
	}
	
}
