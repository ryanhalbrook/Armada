import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
/*
 * Grid paints, moves, stores, and keeps track of everything visual on the panel
 * ie, ships, planets, everything below the frame/menus and above the background
 */
public class Grid {

    private ArrayList<Element> elements;
    private ArrayList<DynamicElement> delements;
    private  ArrayList<Menu> menus;
    private  int mode = 0;
    private  DynamicElement activeE;
    private ArmadaPanel ap;
    private Rectangle viewRegion = new Rectangle(0, 0, 500,500); //The entire grid is 2000 by 2000 pixels. This is the region that the user sees.


    private int currentX = 0;
    private int currentY = 0;

    public Grid(ArmadaPanel inAP) {
    	elements = new ArrayList<Element>();
    	delements = new ArrayList<DynamicElement>();
    	menus = new ArrayList<Menu>();
		Ship s = new Ship(80,80,80,80,"fighter",1);
		delements.add(s);
		ap = inAP;
    }
    
    public void moveViewRegion(int x, int y) {
        viewRegion.setX(viewRegion.getX()+x);
        viewRegion.setY(viewRegion.getY()+y);
        if (viewRegion.getX() < 0) viewRegion.setX(0);
        if (viewRegion.getY() < 0) viewRegion.setY(0);
    }
    
    public Rectangle getViewRegion() {
        return viewRegion;
    }

	public Grid(ArrayList<Element> elements) {
		this.elements = elements;
	}
	
	public void cancelMove() {
	    mode = 0;
	}
	
	/*
	 * @param e1 DynamicElement who's range is used for calculation
	 * @param e2 DynamicElement which is calculated to be within range of DynamicElement e1
	 */
	public boolean inRange(DynamicElement e1, DynamicElement e2){
		return e1.getRange() > distance(e1,e2);
	}
	
	public void mouseMoved(int x, int y) {
	    currentX = x;
	    currentY = y;
	}
	
	/*
	 * received upon any click on ArmadaPanel
	 */
	public void click(int inX, int inY){
		if(mode == 0){
			if(menus != null && menus.size() != 0){
				for (Menu m : menus) {
					//if(m.isIn(inX,inY)){
					//	m.click();
					//}
				}
			}
			if(delements != null && delements.size() != 0){
			inX += viewRegion.getX(); inY += viewRegion.getY();
			System.out.println("x, y:" + inX + ", " + inY);
				for (DynamicElement d : delements) {
					System.out.println("looking for ship 1");
					if(d.isIn(inX,inY)){
						System.out.println("looking for ship 2");
						activeE=d;
						mode = 1;
						menus.add(d.getMenu());
						return;
					}
				}
			}
		}
		/////
		if(mode == 1){
			//move
			inX += viewRegion.getX(); inY += viewRegion.getY();
			activeE.moveTo(inX, inY);
			mode=0;
			
		}
	}
	
	/*
	 * Calculates distance between the two inputs, order does not matter
	 */
	public int distance(DynamicElement e1, DynamicElement e2){
		return (int)Math.sqrt(Math.pow(Math.abs((double)e1.getY()-(double)e2.getY()),2) + Math.pow(Math.abs((double)e1.getX()-(double)e2.getX()),2));
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
	
	/*
	 * draws everything on the Grid
	 */
	public void draw(Graphics g){
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(delements != null && delements.size() != 0){
			for (DynamicElement de : delements) {
				de.draw(g, viewRegion);
			}
		}
		if(menus != null && menus.size() != 0){
			for (Menu m : menus) {
			    m.draw(g);
			}
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(30, 100, 25, 25);
		int x = (int)(viewRegion.getX() / 25.0);
		int y = (int)(viewRegion.getY() / 25.0);
		g.setColor(Color.BLACK);
		g.fillRect(30+x, 100+y, 5, 5);
		g.setColor(Color.GREEN);
		if (mode == 1) {
		    int shipX = activeE.getX();
		    int shipY = activeE.getY();
		    Stroke s = g2d.getStroke();
		    float array[] = {10.0f};
		    g2d.setStroke(new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, array, 0.0f));
		    g.drawLine(shipX-viewRegion.getX(), shipY-viewRegion.getY(), currentX, currentY);
		    g2d.setStroke(s);
		    g.drawString("Move initiated", 30, 145);
		}
	} // End of draw
}
