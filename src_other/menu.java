import java.awt.Graphics;
import java.awt.event.MouseEvent;

/*
 * Menu is to be extended but never directly used. (atm I dnt remember how to make an abstract class, so thats you're job)  It's an abstract class. Each type of menu should have it's own class
 * that extends this one.
 * Since MenuLoader and Menu should be programmed by the same person, there is some flexibility here
 * You should also note the use of requestFocus(), which will make sure the menu is being listened to by the computer inputs (makes sure the layer being clicked on is the layer of this menu)
 */
public class Menu {

	/*
	 * Since everything clicked on should be an Element, this should probably take in an Element
	 * Should be empty and overriden by all subclasses
	 */
	public void Menu(){
	}
	
	/*
	 * should be called in the constructor in all subclasses
	 * main purpose is to create the menu here, such as dimensions and buttons
	 * The buttons themselves should be programmed within subclasses of Menu
	 */
	private void construct(){
		
	}
	
	
	/*
	 * Draws self
	 * Should be called by MenuLoader in it's .draw()
	 * Should be empty and overriden by all subclasses 
	 */
	public void draw(Graphics g){
		
	}
	
	/*
	 * updates display position and stats
	 */
	public void update(){
		
	}
	
	/*
	 * if a Menu has requested receiving clicks from the ArmadaPanel ( via ArmadaPanel.requestClick(this) )
	 * then ArmadaPanel will call this method.
	 * If the Menu no longer needs click information, it should call ArmadaPanel.endRequest();
	 * Ex: Mouse clicks -> info is sent to MenuLoader -> MenuLoader checks Grid -> decides the click was on a ship -> opens ship menu -> attack button is selected, calls ArmadaPanel.requestClick(this) -> a click is made and is sent to this Menu via Menu.click() -> decides if selection was valid and either returns (in order to wait for the next click), calls ArmadaPanel.endRequest() and cancels "Attack", or (on a valid selection) tells Grid to make ship attack second ship then calls ArmadaPanel.endRequest 
	 */
	public void click(MouseEvent arg0){
		
	}
	
	
	
}
