import java.awt.Color;
import java.awt.Graphics;


public class Button extends BoundingRectangle{

	protected String title;
	protected Grid grid;
	protected boolean isSelected=false, clickable;
	
	/*public Button(){
		super(0,0,30,30);
	}*/
	
	public Button(int inX, int inY, int w, int h, Grid g, String s){
		super(inX,inY,w,h);
		grid=g;
		title=s;
	}
	
	public boolean click(int x, int y){
		if(!clickable)return false;
		if(this.isIn(x, y)){
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g){
		if(isSelected){
			g.setColor(new Color(25,25,25, 250));
		}
		else if(this.isIn(grid.getCurrentX(), this.grid.getCurrentY())){
			g.setColor(new Color(150,150,150, 200));
		}
		else{g.setColor(new Color(50,50,50, 200));}
		
		g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		g.drawString(title, x + width/2 - g.getFontMetrics().stringWidth(title)/2, y+15);
	}
	
	public boolean isSelected(){
		return isSelected;
	}
	
	public void setSelected(boolean in){
		isSelected = in;
	}

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
	
}
