import java.awt.Graphics;

/*
 * this class will never be used, but to help you make all the menu's, this is how it should work
 */
public class ExampleMenu extends Menu {

	
	/*
	 * takes same input as the parent class,
	 * whatever will be needed to make this particular Menu
	 * lets pretend it was given an element that was decided by the MenuLoader to be a ship of the current player
	 * You may also want t consider giving the MenuLoader in the constructor
	 */
	public ExampleMenu(Element e){
		construct();
	}
	
	/*
	 * see parent comment
	 * method should be defined here, not parent 
	 */
	public void draw(Graphics g){
		
	}
	
	private void construct(){
		/*
		 * lets pretend this is a menu for clicking on ships on the side of the current player.  The MenuLoader decided
		 * that the mouse was clicked on a ship of the current player.
		 * here, the menu is created including buttons like "Attack" and "Move" are seen.
		 * Also, given information from the MenuLoader when it was constructed,
		 * it should display information about the ship such as its Hull, Engines, Attack, etc.
		 * OR it should have options to open up additional menus to view such things.
		 * As long as Menus have a .draw() and are added to the MenuLoader list, they will display.
		 */
	}
	
	
	/*
	 * should update all display stats and position of menu
	 */
	public void update(){
		
	}
	
	/*
	 * You should also have a bunch of button choices as well and program their events/methods here
	 * For example, if I choose "Attack", the use should be prompted to choose an enemy ship/base.
	 * Since I need to know where they are clicking, I need to ask the ArmadaPanel for the next click location.  
	 * To get the next click forwarded to this class, use ArmadaPanel.requestClick(this).  Next time the user clicks
	 * somewhere on the ArmadaPanel, the MouseEvent will be given to this menu via Menu.click(). If the click was invalid
	 * display feedback and/or exit request mode.  Take into account every situation.  what do you do when
	 * the click was on nothing? a friendly ship? planet? enemy? etc.  When done, you should alert the ArmadaPanel that
	 * you no longer need control of the MouseEvents by doing ArmadaPanel.endRequest().
	 * 
	 * To continue the example, I may want to allow the user to click several times until they find a valid target and I
	 * should probably allow a way for the user to cancel attacking if they have not yet selected a valid target.  In doing so, I should use ArmadaPanel.endRequest() so I 
	 * can free the mouse and then close or stop displaying any information directly related to selecting an attack target if there is any.
	 */
	
}
