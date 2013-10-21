import java.awt.Graphics;
import java.util.ArrayList;


public class HUDmanager {
	
	protected Grid grid;

	protected ArrayList<HUD> huds;
	protected HUD mode, stat;
	
	public HUDmanager(Grid gr){
		grid = gr;
		huds = new ArrayList<HUD>();
		mode = new ModeHUD(gr, 1);
		stat = new StatHUD(gr, 2);
		huds.add(mode);
		huds.add(stat);
	}
	
	public void draw(Graphics g){
		for(HUD h: huds){
			h.draw(g);
		}
	}
	
}
