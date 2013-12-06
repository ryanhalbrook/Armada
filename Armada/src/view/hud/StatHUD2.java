package view.hud;

import java.awt.Color;
import java.awt.Graphics;

import element.ship.Ship;
import view.BoundingRectangle;
import view.controller.GameController;
import view.hud.HUD.Position;

import java.awt.FontMetrics;
import java.awt.Font;

public class StatHUD2 extends HUD {
	
	private Ship s;
	int dmg, adjspd, spd, ml, hll, mxhll, eng, mxeng;
	boolean atk;
	private final int BAR_HEIGHT = 12, TRI_DIMENSION=20, GAP = 10; 
	
	public StatHUD2(GameController gc, Position p){
		super(new BoundingRectangle(5,45,300,0),gc, p);
	}

	public boolean click(int inX, int inY){
		return false;
	}
	
	public void refresh(long previousTime, long currentTime) {
		super.refresh(previousTime, currentTime);
		if(gc.getGrid().getActiveE() == null){
			displaying=false;
		}
		else if(gc.getGrid().getActiveE() instanceof Ship){
			displaying=true;
			s= (Ship)gc.getGrid().getActiveE();
			dmg=s.getWeapons();
			spd = s.getSpeed();
			hll = s.getHull();
			mxhll = s.getMaxHull();
			adjspd=s.getAdjustedSpeed();
			ml = s.getMovementLeft();
			eng=s.getEngine();
			mxeng = s.getMaxEngine();
			atk = s.canAttack();
		}
		else{
			displaying=false;
		}
	}

	public void draw(Graphics g){
		if(!displaying||s==null)return;
		
		
		
		int x = r.getX();
		int y = r.getY();
		int w = r.getWidth();
		int h = r.getHeight();
		double pH = (double)hll/(double)mxhll;
		double pE = (double)eng/(double)mxeng;
		
		int dy = y + 5;
		//g.setColor(Color.black);
		//g.fillRect(x, dy, w-BAR_HEIGHT, BAR_HEIGHT);
		if(s.getAlliance() == 1){
			g.setColor(Color.RED);
		}
		if(s.getAlliance() == 2){
			g.setColor(Color.BLUE);
		}
		g.fillRect(x, dy, (int)(w*pH)-BAR_HEIGHT, BAR_HEIGHT);
		g.setColor(Color.WHITE);
		//g.fillRect(x + w - BAR_HEIGHT, dy, BAR_HEIGHT, BAR_HEIGHT);//triangle?
		int[] xx = {x+w-BAR_HEIGHT, x+w-BAR_HEIGHT, x+w};
		int[] yy = {dy,dy+BAR_HEIGHT, dy+BAR_HEIGHT};
		g.fillPolygon(xx,yy, 3);
		g.setFont(new Font(null, Font.PLAIN, 10));
		g.drawString("Hull: " + hll + "/" + mxhll, x+ BAR_HEIGHT, dy + 10);
		dy+=BAR_HEIGHT;
		g.fillRect(x, dy, w, (int)((double)BAR_HEIGHT*0.25));
		dy+=GAP;
		
		if(s.getAlliance() == 1){
			g.setColor(new Color(250,100,0));
		}
		if(s.getAlliance() == 2){
			g.setColor(Color.CYAN);
		}
		g.fillRect(x, dy, (int)(w*pE)-BAR_HEIGHT, BAR_HEIGHT);
		g.setColor(Color.WHITE);
		xx = new int[] {x+w-BAR_HEIGHT, x+w-BAR_HEIGHT, x+w};
		yy = new int[] {dy,dy+BAR_HEIGHT, dy+BAR_HEIGHT};
		g.fillPolygon(xx,yy, 3);
		if (s.getAlliance() == 2) g.setColor(Color.BLACK);
		g.drawString("Engine: " + eng + "/" + mxeng, x+ BAR_HEIGHT, dy + 10);
		g.setColor(Color.WHITE);
		dy+=BAR_HEIGHT;
		g.fillRect(x, dy, w, (int)((double)BAR_HEIGHT*0.25));
		g.setFont(new Font(null, Font.PLAIN, 14));
		dy+=10+GAP;
		g.drawString("Speed: " + adjspd + "/" + spd  , x, dy);
		dy+=GAP + 4;
		if(atk)
			g.drawString("Fuel: " + ml + " | 1 Laser (" + dmg + " damage)", x, dy);
		else
			g.drawString("Fuel: " + ml + " | ", x, dy);
	}
	
}

