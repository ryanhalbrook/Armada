import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.*;
/*
 * Grid paints, moves, stores, and keeps track of everything visual on the panel
 * ie, ships, planets, everything below the frame/menus and above the background
 *  
 * 
 */
public class Grid {

    private ArrayList<Element> elements;
    private  ArrayList<Menu> menus;
    private  int mode = 0;
    private  Element activeE;

    public Grid() {
    	elements = new ArrayList<Element>();
    	menus = new ArrayList<Menu>();
		Ship s = new Ship(1);
		s.setX(130);
		s.setY(130);
		s.setWidth(130);
		s.setHeight(160);
		elements.add(s);
    }

	public Grid(ArrayList<Element> elements) {
		
	}
	
	/*
	 * @param e1 DynamicElement who's range is used for calculation
	 * @param e2 DynamicElement which is calculated to be within range of DynamicElement e1
	 */
	public boolean inRange(DynamicElement e1, DynamicElement e2){
		return e1.getRange() > distance(e1,e2);
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
			if(elements != null && elements.size() != 0){
				for (Element e : elements) {
					if(e.isIn(inX,inY)){
						activeE=e;
						mode = 1;
						menus.add(e.getMenu());
						return;
					}
				}
			}
		}
		/////
		if(mode == 1){
			//move
			activeE.move(inX, inY);
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
		System.out.println("Drawing");
		if(elements != null && elements.size() != 0){
			for (Element e : elements) {
		    e.draw(g);
			}
		}
		if(menus != null && menus.size() != 0){
			for (Menu m : menus) {
			    m.draw(g);
			}
		}
	}
}
