import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;



public class TradeHUD extends HUD{
	
	private ButtonList l1,l2;
	private Ship s1,s2;
	private final int TITLE_HEIGHT=30,GAP=5;
	private String title;
	
	public TradeHUD(GameController gc, Position p){
		super(new BoundingRectangle(5,45,400,400),gc, p);
		displaying=false;
		title="TRADE";
		l1=new ButtonList(this);
		l2=new ButtonList(this);
	}
	
	public void refresh(long previousTime, long currentTime) {
		super.refresh(previousTime,currentTime);
		if(gc.getActiveE()==null || !(gc.getActiveE() instanceof Ship))displaying=false;
		else if(gc.getActiveE() instanceof Ship  && ((Ship)gc.getActiveE()).isTrading()&&gc.getActiveE().getAlliance()==gc.getGrid().getTurn()){
			displaying=true;
			update();
		}
		else displaying=false;
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
			
			
		}
	}
	
	public void draw(Graphics g){
		if(displaying){
			s1=(Ship)gc.getActiveE();
			if(s1.getTrader()!=null);
			s2=s1.getTrader();
			update();
			updateLocation();
			g.setColor(new Color(60,60,60));
			g.fillRect(r.getX(),r.getY(),r.getWidth(),r.getHeight());
			g.setColor(Color.white);
			g.drawString(title, r.getX() - g.getFontMetrics().stringWidth(title)/2 +r.getWidth()/2, r.getY() + TITLE_HEIGHT/2);
			AnimationHelper.draw(r.getX() + r.getWidth()/4, r.getY()+TITLE_HEIGHT/2, (int)(TITLE_HEIGHT*.75), (int)(TITLE_HEIGHT*.75), s1.getImage(), g);
			AnimationHelper.draw(r.getX() + (r.getWidth()*3)/4, r.getY()+TITLE_HEIGHT/2, (int)(TITLE_HEIGHT*.75), (int)(TITLE_HEIGHT*.75), s2.getImage(), g);
			l1.draw(g);
			l2.draw(g);	
		}
		
		
	}
	
	public boolean click(int inX, int inY){
		//System.out.println("Moving?");
		if(!displaying)return false;
		if(l1==null)return false;
		if(l2==null)return false;
		if(l1.click(inX, inY)  && s2.canGetCargo()){
			System.out.println("Moving from 1");
			Item temp = new Item(l1.getActiveB().getId());
			if(temp.getId()==ItemList.ItemNames.Blank)return false;
			s1.removeItem(temp);
			s2.addItem(temp);
			
			update();
			return true;
		}
		else if(l2.click(inX, inY)&& s1.canGetCargo()){
			System.out.println("Moving from 2");
			Item temp = new Item(l2.getActiveB().getId());
			if(temp.getId()==ItemList.ItemNames.Blank)return false;
			s1.addItem(temp);
			s2.removeItem(temp);
			
			update();
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
