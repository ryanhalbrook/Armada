import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class StatHUD extends HUD{
	
	private ArrayList<Button> buttons;
	private DynamicElement de;

	public StatHUD(int x, int y, int width, int height) {
		super(x, y, width, height);
		fillButtons();
		// TODO Auto-generated constructor stub
	}

	public StatHUD(Grid gr, int t){
		super(5,45,150,100,gr, t);
	}
	

	public void fillButtons(){
		buttons=new ArrayList<Button>();
	    int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();
		updateLocation();
		if(grid.getActiveE() instanceof Ship){
			buttons.add(new Button(0,y,width,18, grid,"Hull: " + grid.getActiveE().getHull() + "/" + grid.getActiveE().getMaxHull()));//x and y actually don't matter
			buttons.add(new Button(0,y,width,18, grid,"Engine: " + grid.getActiveE().getEngine() + "/" + grid.getActiveE().getMaxEngine()));
			buttons.add(new Button(0,y,width,18, grid,"Damage: " + grid.getActiveE().getWeapons()));
			buttons.add(new Button(0,y,width,18, grid,"Speed: " + grid.getActiveE().getAdjustedSpeed() + "/" + grid.getActiveE().getSpeed()));
			buttons.add(new Button(0,y,width,18, grid,"Movement Left: " + (grid.getActiveE().getAdjustedSpeed() - grid.getActiveE().getMoved())));
			buttons.add(new Button(0,y,width,18, grid,"Can Attack: " + grid.getActiveE().canAttack()));
		}
		if(grid.getActiveE() instanceof Planet){
			buttons.add(new Button(0,y,width,18, grid,"Health: " + grid.getActiveE().getHull() + "/" + grid.getActiveE().getMaxHull()));
		}
	}
	
	public void draw(Graphics g){
		if(grid.getActiveE() != de){
			de=grid.getActiveE();
			updateLocation();
			fillButtons();
			updateButtons();
		}
		if(grid.getActiveE() == null)return;
		
		updateLocation();
		updateButtons();
		
		g.setColor(new Color(25,125,175, 150));
		g.fillRect(r.x, r.y, r.width, r.height);
		drawButtons(g);
	}
	
	public void drawButtons(Graphics g){
		if(buttons == null){
			return;
		}
		for(Button b: buttons){
			b.draw(g);
		}
	}
	
	
	public void updateButtons(){
		if(buttons==null)return;
		r.setHeight(4 + (buttons.size() * 22));
		for(int i =0; i < buttons.size(); i++){
			Button b = buttons.get(i);
			b.setX(r.x+3);
			b.setY(r.y+3+(i* 22));
		}
	}
	
	
}
