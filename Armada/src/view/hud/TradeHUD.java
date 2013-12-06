package view.hud; 
import view.*;
import view.button.Button;
import view.button.ButtonPage;
import view.controller.GameController;
import view.hud.HUD.Position;
import item.Item;
import item.ItemList;
import item.ItemList.ItemNames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;

import element.ship.Ship;
import animation.AnimationHelper;


/***
 * Provides an interface for trading between ships of the same alliance
 * @author Aaron
 *
 */
public class TradeHUD extends HUD{
	
	private ButtonPage l1,l2;
	private Ship s1,s2;
	private final int TITLE_HEIGHT=30,GAP=5;
	private String title;
	private Button close; 
	
	public TradeHUD(GameController gc, Position p){
		super(new BoundingRectangle(5,45,400,400),gc, p);
		displaying=false;
		close = new Button(0,0,15,15,gc,"X",true);
		close.setColor(Color.RED);
		title="TRADE";
		l1=new ButtonPage(this);
		l2=new ButtonPage(this);
	}
	
	public void refresh(long previousTime, long currentTime) {
		super.refresh(previousTime,currentTime);
		if(gc.getActiveE()==null || !(gc.getActiveE() instanceof Ship))displaying=false;
		else if(gc.getActiveE() instanceof Ship  && ((Ship)gc.getActiveE()).isTrading()&& !((Ship)gc.getActiveE()).getTrader().isDead() &&gc.getActiveE().getAlliance()==gc.getGrid().getTurn()){
			displaying=true;
			update();
		}
		else {
			displaying=false; 
		}
	}
	
	private void update(){
		if(s1!=null&&s2!=null){
			l1.clear();
			l2.clear();
			l1.fillShip(s1);
			l2.fillShip(s2);
			if(l1.getSuggestedHeight()>l2.getSuggestedHeight()){
				r.setHeight(l1.getSuggestedHeight() + TITLE_HEIGHT+GAP);	
			}
			else{
				r.setHeight(l2.getSuggestedHeight() + TITLE_HEIGHT+GAP);	
			}
			close.setX(r.getX() + r.getWidth()-close.getWidth());
			close.setY(r.getY());
		}
	}
	
	public void draw(Graphics g){
		if(displaying){
			s1=(Ship)gc.getActiveE();
			if(s1.getTrader()!=null)
			s2=s1.getTrader();
			update();
			updateLocation();
			g.setColor(new Color(60,60,60,220));
			g.fillRect(r.getX(),r.getY(),r.getWidth(),r.getHeight());
			g.setColor(Color.white);
			g.drawString(title, r.getX() - g.getFontMetrics().stringWidth(title)/2 +r.getWidth()/2, r.getY() + TITLE_HEIGHT/2);
			AnimationHelper.draw(r.getX() + r.getWidth()/4, r.getY()+TITLE_HEIGHT/2, (int)(TITLE_HEIGHT*.75), (int)(TITLE_HEIGHT*.75), s1.getImage(), g);
			AnimationHelper.draw(r.getX() + (r.getWidth()*3)/4, r.getY()+TITLE_HEIGHT/2, (int)(TITLE_HEIGHT*.75), (int)(TITLE_HEIGHT*.75), s2.getImage(), g);
			l1.draw(g);
			l2.draw(g);
			close.draw(g);
		}
	}
	
	public void drag(int inX, int inY){
		if(inY-r.getY()<TITLE_HEIGHT){
			position=HUD.Position.STATIC;
			r.setX(inX-r.getWidth()/2);
			r.setY(inY-TITLE_HEIGHT/2);	
		}
		
	}
	
	public boolean click(int inX, int inY){
		//System.out.println("Moving?");
		if(!displaying)return false;
		if(close.isIn(inX, inY)){
			//displaying=false;
			s1.setTrading(false);
			s1.untrade();
			return true;
		}
		if(l1==null)return false;
		if(l2==null)return false;
		if(l1.click(inX, inY)  && s2.canGetCargo()){
			if(l1.getActiveB()==null)return true;
			Item temp = new Item(l1.getActiveB().getId());
			if(temp.getId()==ItemList.ItemNames.Blank)return true;
			s1.removeItem(temp);
			s2.addItem(temp);
			l1.unselect();
			update();
			return true;
		}
		else if(l2.click(inX, inY)&& s1.canGetCargo()){
			if(l2.getActiveB()==null)return true;
			Item temp = new Item(l2.getActiveB().getId());
			if(temp.getId()==ItemList.ItemNames.Blank)return true;
			s1.addItem(temp);
			s2.removeItem(temp);
			
			update();
			l2.unselect();
			return true;
		}
		else if(r.isIn(inX, inY)){
			return true;
		}
		return false;
	}
	
	public void updateLocation(){
		if(l1==null)return ;
		if(l2==null)return ;
		super.updateLocation();
		l1.setDimensions(r.x+GAP, r.y+this.TITLE_HEIGHT,(int)Math.round(r.width/2-GAP*1.5),l1.getSuggestedHeight());
		l2.setDimensions(r.x+l1.getWidth()+GAP*2, r.y +this.TITLE_HEIGHT, (int)Math.round(r.width/2-GAP*1.5), l2.getSuggestedHeight());
	}
	
	
}
