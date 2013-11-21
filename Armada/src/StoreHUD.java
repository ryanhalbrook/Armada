import java.awt.Color;
import java.awt.Graphics;

//import HUD.Position;

//import HUD.Position;


public class StoreHUD extends HUD{
	
	//private ButtonList shipList;//, shopList;
	private ButtonPage shopList, shipList;
	private Ship s;
	private Planet p;
	private final int TITLE_HEIGHT=30,GAP=5;
	private String title = "";

	public StoreHUD(GameController gc, Position p){
		super(new BoundingRectangle(5,45,700,400),gc, p);
		shipList = new ButtonPage(this);
		shopList=new ButtonPage(this);
		displaying=false;
	}
	
	public void refresh(long previousTime, long currentTime) {
		super.refresh(previousTime,currentTime);
		if(gc.getActiveE()==null || !(gc.getActiveE() instanceof Ship))displaying=false;
		else if(gc.getActiveE() instanceof Ship  && ((Ship)gc.getActiveE()).isDocked()&&gc.getActiveE().getAlliance()==gc.getGrid().getTurn() && ((Ship)gc.getActiveE()).getPlanetDocked().getAlliance() == ((Ship)gc.getActiveE()).getAlliance()){
			displaying=true;
			//update();
		}
		else {
			displaying=false; 
		}
	}

	private void update(){
		if(s!=null&&p!=null){
			shipList.clear();
			shopList.clear();
			shipList.fillShip(s);
			shopList.fillShop(p);
			if(shipList.getSuggestedHeight()>shopList.getSuggestedHeight()){
				r.setHeight(shipList.getSuggestedHeight() + TITLE_HEIGHT+GAP);	
			}
			else{
				r.setHeight(shopList.getSuggestedHeight() + TITLE_HEIGHT+GAP);	
			}
		}
	}
	
	
	public void draw(Graphics g){
		if(displaying){
			s=(Ship)gc.getActiveE();
			if(s.getTrader()!=null);
			p=s.getPlanetDocked();
			update();
			updateLocation();
			g.setColor(new Color(60,60,60,220));
			g.fillRect(r.getX(),r.getY(),r.getWidth(),r.getHeight());
			g.setColor(Color.white);
			g.drawString(title, r.getX() - g.getFontMetrics().stringWidth(title)/2 +r.getWidth()/2, r.getY() + TITLE_HEIGHT/2);
			String temp1="SELL";
			String temp2="BUY";
			AnimationHelper.draw(r.getX() + r.getWidth()/4 - g.getFontMetrics().stringWidth(temp1), r.getY()+TITLE_HEIGHT/2, (int)(TITLE_HEIGHT*.75), (int)(TITLE_HEIGHT*.75), s.getImage(), g);
			g.drawString(temp1, r.getX() + r.getWidth()/4, r.getY()+TITLE_HEIGHT/2);
			AnimationHelper.draw(r.getX() + (r.getWidth()*3)/4 - g.getFontMetrics().stringWidth(temp2), r.getY()+TITLE_HEIGHT/2, (int)(TITLE_HEIGHT*.75), (int)(TITLE_HEIGHT*.75), p.getImage(), g);
			g.drawString(temp2, r.getX() + (r.getWidth()*3)/4, r.getY()+TITLE_HEIGHT/2);
			shipList.draw(g);
			shopList.draw(g);
		}
	}
	
	public void updateLocation(){
		if(s==null)return ;
		if(p==null)return ;
		super.updateLocation();
		shipList.setDimensions(r.x+GAP, r.y+this.TITLE_HEIGHT,(int)Math.round(r.width/2-GAP*1.5),shipList.getSuggestedHeight());
		shopList.setDimensions(r.x+shipList.getWidth()+GAP*2, r.y +this.TITLE_HEIGHT, (int)Math.round(r.width/2-GAP*1.5), shopList.getSuggestedHeight());
	}
	

	public boolean click(int inX, int inY){
		//System.out.println("Moving?");
		if(!displaying)return false;
		if(shipList==null)return false;
		if(shopList==null)return false;
		if(shipList.click(inX, inY)){
			if(shipList.getActiveB()==null)return true;
			Item temp = new Item(shipList.getActiveB().getId());
			if(temp.getId()==ItemList.ItemNames.Blank)return true;
			gc.getEngine().getPlayerManager().payPlayerMoney(s.getAlliance(), temp.getPrice());
			s.removeItem(temp);			
			update();
			return true;
		}
		else if(shopList.click(inX, inY)&& s.canGetCargo()){
			if(shopList.getActiveB()==null)return true;
			Item temp = new Item(shopList.getActiveB().getId());
			if(temp.getId()==ItemList.ItemNames.Blank)return true;
			if(gc.getEngine().getPlayerManager().getPlayerMoney(s.getAlliance()) > temp.getPrice()){
				gc.getEngine().getPlayerManager().playerPays(s.getAlliance(), temp.getPrice());
				s.addItem(temp);			
				update();
			}
			return true;
		}
		else if(r.isIn(inX, inY)){
			return true;
		}
		return false;
	}
	
	public void drag(int inX, int inY){
		if(inY-r.getY()<TITLE_HEIGHT){
			position=HUD.Position.STATIC;
			r.setX(inX-r.getWidth()/2);
			r.setY(inY-TITLE_HEIGHT/2);	
		}
		
	}
	
}
