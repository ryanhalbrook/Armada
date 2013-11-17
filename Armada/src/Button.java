import java.awt.Color;
import java.awt.Graphics;


public class Button extends BoundingRectangle{

	
	protected String title;
	protected Grid grid;
	protected boolean isSelected=false, clickable;
	protected GameController gc;
	protected HoverButtonText t;
	protected String imageName;
	/*public Button(){
		super(0,0,30,30);
	}*/
	
	public Button(int inX, int inY, int w, int h, GameController gc, String s, boolean b){
		super(inX,inY,w,h);
		this.gc = gc;
		title=s;
		clickable=b;
		t=new HoverButtonText(this);
		imageName=null;
	}
	
	public Button(int inX, int inY, int w, int h, GameController gc, String s){
		super(inX,inY,w,h);
		clickable=true;
		this.gc = gc;
		title=s;
		t=new HoverButtonText(this);
		imageName=null;
	}
	public Button(int inX, int inY, int w, int h, GameController gc, String s, String imageName,boolean b){
		super(inX,inY,w,h);
		this.gc = gc;
		title=s;
		clickable=b;
		t=new HoverButtonText(this);
		this.imageName=imageName;
	}
	
	public Button(int inX, int inY, int w, int h, GameController gc, String s,String imageName){
		super(inX,inY,w,h);
		clickable=true;
		this.gc = gc;
		title=s;
		t=new HoverButtonText(this);
		this.imageName=imageName;
	}
	
	public boolean click(int x, int y){
		if(!clickable)return false;
		if(this.isIn(x, y)){
			return true;
		}
		if(this.isIn(gc.getCurrentX(), gc.getCurrentY())){
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g){
		if(isSelected){
			g.setColor(new Color(25,25,25, 250));
		}
		else if(clickable && this.isIn(gc.getCurrentX(), this.gc.getCurrentY())){
			glow(g);
		}
		else{g.setColor(new Color(50,50,50, 200));}
		
		g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		if(imageName==null||imageName=="")
			g.drawString(title, x + width/2 - g.getFontMetrics().stringWidth(title)/2, y + 3 + height/2);
		else{
			g.drawString(title, x + width/2 + height/2 - g.getFontMetrics().stringWidth(title)/2, y + 3 + height/2);
			AnimationHelper.draw(x+height/2, y+height/2, (int)(height*0.8), (int)(height*0.8), imageName, g);
		}	
		t.draw(g);
	}
	
	public void glow(Graphics g){
		int maxBright =150;
		int minBright=75;
		int cycle=20;
		int time = minBright + (int)(System.currentTimeMillis()%(((maxBright-minBright) *2)*cycle))/cycle;
		if(time > maxBright){
			time = maxBright - (time-maxBright);
		}
		if(time < 0){
			time = minBright;
		}
		g.setColor(new Color(time,time, time, 200));
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public GameController getGameController(){
		return gc;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
}
