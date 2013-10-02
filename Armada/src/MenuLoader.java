import java.awt.Graphics;
import java.awt.event.MouseEvent;

/*
 * Decides which menu is to be created, opened, and displayed
 * 
 */

public class MenuLoader {

	/*
	 * constructor takes in an ArmadaPanel and a Grid for later use
	 */
	public MenuLoader(ArmadaPanel apIn, Grid gridIn){
		
	}
	
	/*
	 * given the MouseEvent, makeMenu() will find what is on the Grid at the given location
	 * and create the appropriate menu based on the type of element present, the alliance of the element,
	 * or nothing at all if the location is empty. This will be called every time there is a click, unless a Menu is requesting click information
	 * It should also take into account who is playing at the time (if it's Player 1's turn or Player 2's turn)
	 * this can be found by using ArmadaPanel.turn(), which will return an int 1 for P1, 2 for P2
	 * The menu created should be of type Menu and be added to a list of current open menus
	 */
	public void makeMenu(MouseEvent arg0){
		
	}
	
	/*
	 * Menu's created should be able to call this method to remove itself from the list of active Menus
	 */
	public void closeMenu(){
		
	}
	
	/*
	 * tells all the menus that are in the active list to draw themselves with the given Graphics
	 * The Menus should know where they are to be drawn based on information given to it by the MenuLoader and
	 * the ArmadaPanel.  Note that the size of the frame can change, so using set coordinates will not work
	 */
	public void draw(Graphics g){
		
	}
	
	/*
	 * this will be called often.  The dimensions of the Frame may have been changed.
	 * You can obtain the current dimensions through obtaining the Frame by using ArmadaPanel.getFrame() then .getWidth() or .getHeight()
	 * This will tell the menus to update their position and any display information that may be subject to change such as stats  
	 */
	public void update(){
		
	}
	
	
	
}
