import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;
/*
 * Grid paints, moves, stores, and keeps track of everything visual on the panel
 * ie, ships, planets, everything below the frame/menus and above the background
 */
public class Grid {

    private ArrayList<Element> elements;
    private ArrayList<DynamicElement> delements;
    private  ArrayList<Menu> menus;
    private  int mode = 0, turn = 1, index =0;
    private  DynamicElement activeE;
    private ArmadaPanel ap;
    private Rectangle viewRegion = new Rectangle(0, 0, 500,500); //The entire grid is 2000 by 2000 pixels. This is the region that the user sees.
    Color player1Color = new Color(1.0f, 0.0f, 0.0f, 0.5f);
    Color player2Color = new Color(0.0f, 0.0f, 1.0f, 0.5f);
    BufferedImage backgroundImage = null;
    static final int GRID_WIDTH = 3840;
    static final int GRID_HEIGHT = 2160;

    private int currentX = 0;
    private int currentY = 0;

    public Grid(ArmadaPanel inAP) {
    	elements = new ArrayList<Element>();
    	delements = new ArrayList<DynamicElement>();
    	menus = new ArrayList<Menu>();
		Ship s = new NormalShip(750,330,1);
		delements.add(s);
		Ship s2 = new NormalShip(160,330,1);
		delements.add(s2);
		Ship s3 = new NormalShip(260,330,2);
		delements.add(s3);
		Ship s4 = new NormalShip(60,330,2);
		delements.add(s4);
		Ship s5 = new NormalShip(220,330,2);
		delements.add(s5);
		ap = inAP;
		backgroundImage = loadImage(new File("ArmadaBackground2.jpg"));
		if (backgroundImage == null) {
		    backgroundImage = loadImage(new File("src/ArmadaBackground2.jpg"));
		}
    }
    
    public void moveViewRegion(int x, int y) {
        viewRegion.setX(viewRegion.getX()+x);
        viewRegion.setY(viewRegion.getY()+y);
        if (viewRegion.getX() < 0) viewRegion.setX(0);
        if (viewRegion.getY() < 0) viewRegion.setY(0);
        if (viewRegion.getX() + ap.getWidth() > GRID_WIDTH) {
            viewRegion.setX(GRID_WIDTH - ap.getWidth());
        }
        if (viewRegion.getY() + ap.getHeight() > GRID_HEIGHT) {
            viewRegion.setY(GRID_HEIGHT - ap.getHeight());
        }
    }
    
    public Rectangle getViewRegion() {
        return viewRegion;
    }

	public Grid(ArrayList<Element> elements) {
		this.elements = elements;
	}
	
	public void cancelMove() {//does not deselect ship.  use setMode(0) to do that 
	    mode = 0;
	}
	
	public void selectNextDEThisTurn(){
		if(delements == null)return;
		if(delements.size() <=0) return;
		if(index >= delements.size()) index=0;
		int initialIndex = index;
		DynamicElement temp = delements.get(index);
		do{
			index++;
			if(index==initialIndex){//if came full circle
				return;
			}
			if(delements.size() == index){//if hit end of list
				index=0;
			}
			temp=delements.get(index);
			//if(temp.getAlliance() == turn)break;
		}while(temp.getAlliance()!=turn || !temp.isTargetable());
		activeE=temp;
	}
	
	public void nextMode(){
		mode++;
		if(mode > 3){
			mode=0;
		}
	}
	
	/*
	 * @param e1 DynamicElement who's range is used for calculation
	 * @param e2 DynamicElement which is calculated to be within range of DynamicElement e1
	 */
	public boolean inRange(DynamicElement e1, DynamicElement e2){
		return e1.getRange() > distance(e1,e2);
	}
	
	public void setMode(int i){
		mode=i;
		if(mode==0){
			activeE=null;
		}
	}
	
	public void mouseMoved(int x, int y) {
	    currentX = x;
	    currentY = y;
	}
	
	/*
	 * received upon any click on ArmadaPanel
	 */
	public void click(int inX, int inY){
		if(mode == 0 || activeE==null){//selecting a menu
			if(menus != null && menus.size() != 0){
				for (Menu m : menus) {
					//if(m.isIn(inX,inY)){
					//	m.click();
					//}
				}
			}
			if(delements != null && delements.size() != 0){//selecting a ship
				inX += viewRegion.getX(); inY += viewRegion.getY();
				//System.out.println("x, y:" + inX + ", " + inY);
				for (DynamicElement d : delements) {
					if(d.isIn(inX,inY) && d.isTargetable()){
					    mode = 1;
						activeE=d;
						System.out.println("Returning");
						return;
						//menus.add(d.getMenu());
						
					}
				}
			}
			
		}
		/////
		if(activeE==null || activeE.getAlliance()!=turn || !activeE.isTargetable()){
			return;
		}
		if(mode == 1){
			//move
			inX += viewRegion.getX(); inY += viewRegion.getY();
			if(activeE.withinMovement(inX,inY) && activeE.canMovePath(inX,inY, delements)){
				activeE.moveTo(inX, inY);
				//setMode(0);
			}
			return;
		}
		if(mode == 2){
			//attack hull
			if(delements != null && delements.size() != 0 && activeE.canAttack()){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				//System.out.println("x, y:" + inX + ", " + inY);
					for (DynamicElement d : delements) {
						//System.out.println("looking for ship 1");
						if(d.isIn(inX,inY) && d.getAlliance()!=activeE.getAlliance() && activeE.withinRange(inX,inY) && d.isTargetable()){
							//System.out.println("looking for ship 2");
							d.hullTakeDamage(activeE);
							System.out.println("Hull now at: "+d.getHull());
							
							activeE.setCanAttack(false);
							//setMode(0);
							return;
						}
					}
				}
		}
		if(mode == 3){
			//attack engine
			if(delements != null && delements.size() != 0 && activeE.canAttack()){
				inX += viewRegion.getX(); inY += viewRegion.getY();
				//System.out.println("x, y:" + inX + ", " + inY);
					for (DynamicElement d : delements) {
						//System.out.println("looking for ship 1");
						if(d.isIn(inX,inY) && d.getAlliance()!=activeE.getAlliance() && d.isTargetable() && d.getEngine()>0){
							//System.out.println("looking for ship 2");
							d.engineTakeDamage(activeE);
							System.out.println("Engines now at: "+d.getEngine());
							activeE.setCanAttack(false);
							//setMode(0);
							return;
						}
					}
				}
		}
		
	}
	
	/*
	 * Calculates distance between the two inputs, order does not matter
	 */
	public int distance(DynamicElement e1, DynamicElement e2){
		return (int)Math.sqrt(Math.pow(Math.abs((double)e1.getY()-(double)e2.getY()),2) + Math.pow(Math.abs((double)e1.getX()-(double)e2.getX()),2));
	}
	
	public int distance(DynamicElement e1, int inX, int inY){
		return (int)Math.sqrt(Math.pow(Math.abs((double)e1.getY()-(double)inY),2) + Math.pow(Math.abs((double)e1.getX()-(double)inX),2));
	}
	
	/*
	 * returns a DynamicElement at the specified location
	 */
	public DynamicElement getDynamicElementAt(int x, int y){
		return new DynamicElement();
	}
	
	/*
	 * adds Animation to the Grid, but separate from the DynamicElements so it cannot be selected
	 */
	public void add(Animation a){
		
	}
	
	/*
	 * adds DynamicElement to Grid, separate from Animations
	 */
	public void add(DynamicElement de){
		
	}
	
	public void toggleTurn(){
		if(turn==1){
			turn=2;
		}
		else{
			turn=1;	
		}
		startTurn();		
	}
	
	public void startTurn(){
		setMode(0);
		if(delements != null && delements.size() != 0){//selecting a ship
			for (DynamicElement d : delements) {
				//System.out.println("looking for ship 1");
				d.startOfTurn();
			}
		}
	}
	
	private void drawMiniMap(Graphics g) {
	    g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.1f));
		int miniMapWidth = 250;
		int miniMapHeight = 125;
		int x = ap.getWidth()-5-miniMapWidth;
		int y = ap.getHeight()-5-miniMapHeight;
		g.fillRect(x, y, miniMapWidth, miniMapHeight);
		
		int insetWidth = (int)((ap.getWidth() / (float)GRID_WIDTH)*(miniMapWidth*1.0));
		int insetHeight = (int)((ap.getHeight() / (float)GRID_HEIGHT)*(miniMapHeight*1.0));
		double dxf = (miniMapWidth*1.0)*(viewRegion.getX()/(GRID_WIDTH*1.0));
		double dyf = (miniMapHeight*1.0)*(viewRegion.getY()/(GRID_HEIGHT*1.0));
		
		int dx = (int)dxf; int dy = (int)dyf;
		g.setColor(Color.WHITE);
		g.drawRect(x+dx, y+dy, insetWidth, insetHeight);
	}
	
	/*
	 * draws everything on the Grid
	 */
	public void draw(Graphics g){
	    if (backgroundImage != null) {
	        g.drawImage(backgroundImage, -viewRegion.getX(), -viewRegion.getY(), null);
	    }
	
	    Color currentPlayerColor = Color.WHITE;
	    String playerName = "";
	    if (turn == 1) {
	        currentPlayerColor = player1Color;
	        playerName = "Player 1";
	    } else {
	        currentPlayerColor = player2Color;
	        playerName = "Player 2";
	    }
	
	    if (mode == 0) {
	        ap.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    } else {
	        ap.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	    }
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
		if(delements != null && delements.size() != 0){
			for (int i=0;i<delements.size();i++) {
				if(delements.get(i).isDead()){
					delements.remove(i);
					i--;
				}
			}
			for (DynamicElement de : delements) {
				de.draw(g, viewRegion);
			}
		}
		if(menus != null && menus.size() != 0){
			for (Menu m : menus) {
			    m.draw(g);
			}
		}
		
		// Current Player Indicator
		g.setColor(currentPlayerColor);
		g.fillRect(0, 0, ap.getWidth(), 20);
		g.setColor(Color.WHITE);
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(playerName);
		g.drawString(playerName, ap.getWidth() - 5 - textWidth, 15);
		
		drawMiniMap(g);
		/**
		if(turn==1){
			g.setColor(Color.RED);
			g.drawString("Player 1's turn - Press ESC to end turn", 30, 15);
		}
		else{
			g.setColor(Color.BLUE);
			g.drawString("Player 2's turn  - Press ESC to end turn", 30, 15);
		}
		*/
		if(activeE!=null){
			g.setColor(Color.WHITE);
			g.drawString("1-Move | 2-Attack Hull | 3-Attack Engines | 4-Unselect", 5, 15);
		}
		
		if (activeE!=null) {
		    
		    int shipX = activeE.getX();
		    int shipY = activeE.getY();
		    Stroke s = g2d.getStroke();
		    float array[] = {10.0f};
		    g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, array, 0.0f));
		    if(mode==1 && activeE.canMovePath(currentX + viewRegion.getX(),currentY + viewRegion.getY(),delements) && activeE.canMove()){
		    	g.setColor(Color.GREEN);
		    }
		    else if(mode == 1){
		    	g.setColor(Color.RED);
		    }
		    else if((mode == 2 ) && activeE.withinRange(currentX + viewRegion.getX(),currentY + viewRegion.getY()) && activeE.canAttack()){
		    	g.setColor(new Color(250,100,0));
		    }
		    else if((mode == 2 )){
		    	g.setColor(Color.RED);
		    }
		    else if(( mode == 3) && activeE.withinRange(currentX + viewRegion.getX(),currentY + viewRegion.getY()) && activeE.canAttack()){
		    	g.setColor(Color.YELLOW);
		    }
		    else if(( mode == 3)){
		    	g.setColor(Color.RED);
		    }
		    g.drawLine(shipX-viewRegion.getX(), shipY-viewRegion.getY(), currentX, currentY);
		    int radius = 20;
		    
		    g.drawOval(currentX-radius, currentY-radius, radius*2, radius*2);
		    g2d.setStroke(s);
		    
		    
		    switch(mode){
		    case 0:
		    	g.drawString("No Move Selected", 30, 145);
		    	break;
		    case 1:
		    	g.drawString("Move initiated", 30, 145);
		    	break;
		    case 2:
		    	g.drawString("Attacking Hull", 30, 145);
		    	break;
		    case 3:
		    	g.drawString("Attacking Engine", 30, 145);
		    	break;
		    }   
		}
		else{
			g.setColor(Color.WHITE);
			g.drawString("No Ship Selected", 5, 15);
		}
		
		//I intend for this part to be its own class, but I haven't decided how I want it to work with Grid yet
		////////////
		if(activeE != null){
			int dx= ap.getWidth() - 150;
			int dy= ap.getHeight() - 90;
			g.setColor(Color.WHITE);
			g.drawString("Hull: " + activeE.getHull(), dx, dy);
			g.drawString("Engine: " + activeE.getEngine(), dx, dy+15);
			g.drawString("Damage: " + activeE.getWeapons(), dx, dy+30);	
			g.drawString("Speed: " + activeE.getAdjustedSpeed(), dx, dy+45);
			g.drawString("Movement Left: " + (activeE.getAdjustedSpeed() - activeE.getMoved()), dx, dy+60);
			g.drawString("Can Attack: " + activeE.canAttack(), dx, dy+75);	
		}
		
		
		
		///////////
		
	} // End of draw
	
	private static BufferedImage loadImage(File f) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Failed to load background image");
        }
        return bi;
        
    }
}

