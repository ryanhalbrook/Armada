import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class MapHUD extends HUD{
	
	private ArrayList<DynamicElement> des;
	static final double DEFAULT_SCALE = .2;
	private double scale = DEFAULT_SCALE;
	private boolean expanding = false;
	private boolean shrinking = false;
	public static final int SCALING_TIME = 3000; // in milliseconds
	private int inputLocation=4;
	
	public MapHUD(Grid gr, Position p){
		super(new BoundingRectangle(0,0,250,125),gr);
		//inputLocation=l;
	    //location = l;
	    this.position = p;
		des = grid.getDelements();
		setName("Map Layer");
	}
	
	public MapHUD(Grid gr, int l, BoundingRectangle r){
		super(new BoundingRectangle(0,0,250,125),gr);
		inputLocation=l;
		location = l;
		des = grid.getDelements();
		setName("Map Layer");
	}

    public boolean click(int inX, int inY){
		if(r.isIn(inX, inY)){
		    System.out.println("You clicked the map");
			moveMap(inX,inY);
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g){
	    r.setWidth((int)(grid.getAp().getWidth() * scale));
	    r.setHeight((int)(grid.getAp().getHeight() * scale));
	    r.setX(grid.getAp().getWidth() - r.getWidth() - 10);
	    r.setY(grid.getAp().getHeight() - r.getHeight() - 10);
	    updateLocation();
	    int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();
		g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.8f));
		g.fillRect(x, y, width, height);
		int insetWidth = (int)((grid.getViewRegion().getWidth() / (float)grid.getWidth())*(width*1.0));
		int insetHeight = (int)((grid.getViewRegion().getHeight() / (float)grid.getHeight())*(height*1.0));
		double dxf = (width*1.0)*(grid.getViewRegion().getX()/(grid.getWidth()*1.0));
		double dyf = (height*1.0)*(grid.getViewRegion().getY()/(grid.getHeight()*1.0));
		des=grid.getDelements();
		if(des == null)return;
		DynamicElement temp;
		synchronized(des){
			for(int i=0; i<des.size(); i++){
				temp=des.get(i);
				if(temp.getAlliance() == 1){
					g.setColor(Color.RED);
				}
				else if(temp.getAlliance() == 2){
					g.setColor(Color.BLUE);
				}
				else{
					g.setColor(Color.GREEN);
				}
				g.fillOval(x + (int)((temp.getX()/ (float)grid.getWidth())*(width*1.0)), y + (int)((temp.getY()/ (float)grid.getHeight())*(height*1.0)), 2+(int)((temp.getWidth()/ (float)grid.getWidth())*(width*1.0)),2+ (int)((temp.getHeight()/ (float)grid.getHeight())*(height*1.0)));
			}
		}
		
		int dx = (int)dxf; int dy = (int)dyf;
		g.setColor(Color.WHITE);
		g.drawRect(x+dx, y+dy, insetWidth, insetHeight);
		g.drawRect(x-1, y-1, width+1, height+1);
		updateLocation();
		if (scale > 0.79) {
		    g.drawLine(x + (int)(width / 2.0f), y, x + (int)(width / 2.0f), y + height);
		    g.drawLine(x, y + (int)(height / 2.0f), x + width, y + (int)(height / 2.0f));
		    int SCALE_LENGTH = 75;
		    int SCALE_HEIGHT = 16;
		    g.drawLine(x + width - SCALE_LENGTH - 30, y + height - 30, x + width - 30, y + height - 30);
		    g.drawLine(x + width - SCALE_LENGTH - 30, y + height - 30 - (int)(SCALE_HEIGHT / 2.0f), x + width - SCALE_LENGTH - 30, y + height - 30 + (int)(SCALE_HEIGHT / 2.0f));
		    g.drawLine(x + width - 30, y + height - 30 - (int)(SCALE_HEIGHT / 2.0f), x + width - 30, y + height - 30 + (int)(SCALE_HEIGHT / 2.0f));
		    g.drawString("1000 km", x + width - SCALE_LENGTH - 25, y + height - 40);//I don't think we should have a unit, the scale of our game is ridiculous
		    g.drawString("II", x + 10, y + 20);
		    g.drawString("I", x + (int)(width / 2.0f) + 10, y + 20);
		    g.drawString("III", x + 10, y + (int)(height / 2.0f) + 20);
		    g.drawString("IV", x + (int)(width / 2.0f) + 10, y + (int)(height / 2.0f)+20);
		}
		
	}

	public void moveMap(int inX, int inY){
	/*
	    inX += 10;
	    inY += 10;
	    */
		int newX = inX-r.x;
		int newY = inY-r.y;
	    double wPerc = (double)r.width/Grid.GRID_WIDTH;
	    double hPerc = (double)r.height/Grid.GRID_HEIGHT;
		int xx=(int)(((double)newX)/wPerc)-grid.getAp().getWidth()/2;
		int yy=(int)(((double)newY)/hPerc) -grid.getAp().getHeight()/2;
		if(xx < 0) xx = 0;
		if(yy < 0) yy = 0;
		if(xx > Grid.GRID_WIDTH-grid.getAp().getWidth()) xx = Grid.GRID_WIDTH-grid.getAp().getWidth();
		if(yy > Grid.GRID_HEIGHT-grid.getAp().getHeight()) yy = Grid.GRID_HEIGHT-grid.getAp().getHeight();
		grid.moveViewRegionToPoint(xx, yy);
		//grid.getViewRegion().setX(xx);
		//grid.getViewRegion().setY(yy);
	}
	
	public boolean isIn(int inX, int inY){
		return r.isIn(inX, inY);
	}
	
	public void refresh(long previousTime, long currentTime) {
	    
	    super.refresh(previousTime, currentTime);
	    int delta = (int)(currentTime - previousTime);
	    float step = ( delta / (SCALING_TIME * 1.0f) ) * 8.0f;
	    if (shrinking) {
	    	position = HUD.Position.BOTTOM_RIGHT;
	    	//System.out.println("1: " + location);
	        scale -= step;
	        if (scale < DEFAULT_SCALE) {
	            scale = DEFAULT_SCALE;
	            shrinking = false;    
	        }
	        
	    }
	    if (expanding) {
	        position = HUD.Position.CENTERED;
	    	//location=10;
	    	//this.r.setX(grid.getAp().getWidth()/2-r.width/2);
	    	//this.r.setY(grid.getAp().getHeight()/2 - r.height/2);
	    	//System.out.println("2: " + location);
	        scale += step;
	        if (scale > .8) {
	            scale = .8;
	            expanding = false;
	        }
	    }
	}

	public void toggleScale(){
	    if (shrinking || expanding) return;
		if(scale > 0.5){
		    
		    shrinking = true;
			//scale = MapHUD.DEFAULT_SCALE;
		} else {
		    expanding = true;
		    //scale = 1;
		}
	}
	
	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
}
