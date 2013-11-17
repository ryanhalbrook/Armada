import java.awt.Cursor;
import java.awt.Graphics;



public class TradeHUD extends HUD{
	
	private ButtonList l1,l2;
	private Ship s1,s2;
	
	public TradeHUD(GameController gc, Position p){
		super(new BoundingRectangle(5,45,400,400),gc, p);
		displaying=false;
	}
	
	public void refresh(long previousTime, long currentTime) {
		super.refresh(previousTime,currentTime);
		if(gc.getActiveE()==null || !(gc.getActiveE() instanceof Ship))displaying=false;
		else if(gc.getActiveE() instanceof Ship  && ((Ship)gc.getActiveE()).isTrading()){
			displaying=true;
			update();
		}
		else displaying=false;
	}
	
	private void update(){
		if(s1!=null&&s2!=null){
			l1=new ButtonList(this);
			l2=new ButtonList(this);
			l1.fillShip(s1);
			l2.fillShip(s2);	
		}
	}
	
	public void draw(Graphics g){
		if(displaying){
			s1=(Ship)gc.getActiveE();
			if(s1.getTrader()!=null);
			s2=s1.getTrader();
			update();
			updateLocation();
			
			l1.draw(g);
			l2.draw(g);	
		}
		
		
	}
	
	public boolean click(int inX, int inY){
		//System.out.println("Moving?");
		if(l1==null)return false;
		if(l2==null)return false;
		if(l1.click(inX, inY)){
			System.out.println("Moving from 1");
			Item temp = new Item(l1.getActiveB().getId());
			if(temp.getId()==ItemList.ItemNames.Blank)return false;
			s1.removeItem(temp);
			s2.addItem(temp);
			
			update();
			return true;
		}
		else if(l2.click(inX, inY)){
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
		l1.setDimensions(r.x, r.y,r.width/2,r.height);
		l2.setDimensions(r.x+l1.getWidth(), r.y, r.width/2, r.height);
	}
	
	
}
