import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class MapHUD extends HUD{
	
	ArrayList<DynamicElement> des;

	public MapHUD(int x, int y, int width, int height, Grid gr, int l) {
		super(x, y, width, height, gr, l);
		// TODO Auto-generated constructor stub
	}
	
	public MapHUD(Grid gr, int l){
		super(0,0,250,125,gr);
		location = l;
		des = grid.getDelements();
	}

	
	public void draw(Graphics g){
		updateLocation();
		g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.1f));
		g.fillRect(x, y, width, height);
		
		int insetWidth = (int)((grid.getAp().getWidth() / (float)grid.getWidth())*(width*1.0));
		int insetHeight = (int)((grid.getAp().getHeight() / (float)grid.getHeight())*(height*1.0));
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
				g.fillRect(x + (int)((temp.getX()/ (float)grid.getWidth())*(width*1.0)), y + (int)((temp.getY()/ (float)grid.getHeight())*(height*1.0)), 2+(int)((temp.getWidth()/ (float)grid.getWidth())*(width*1.0)),2+ (int)((temp.getHeight()/ (float)grid.getHeight())*(height*1.0)));
			}
		}
		
		int dx = (int)dxf; int dy = (int)dyf;
		g.setColor(Color.WHITE);
		g.drawRect(x+dx, y+dy, insetWidth, insetHeight);
	}
}
