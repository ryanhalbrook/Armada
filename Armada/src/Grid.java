import java.awt.Graphics;
import java.util.*;
/*
 * Grid paints, moves, stores, and keeps track of everything visual on the panel
 * ie, ships, planets, everything below the frame/menus and above the background
 *  
 * 
 */
public class Grid {

    ArrayList<Element> elements;

    public Grid() {}

	public Grid(ArrayList<Element> elements) {
		this.elements = elements;
	}
	
	/*
	 * @param e1 DynamicElement who's range is used for calculation
	 * @param e2 DynamicElement which is calculated to be within range of DynamicElement e1
	 */
	public boolean inRange(DynamicElement e1, DynamicElement e2){
		return e1.getRange() > distance(e1,e2);
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
		for (Element e : elements) {
		    System.out.print(e);
		    e.draw(g);
		}
	}
}
