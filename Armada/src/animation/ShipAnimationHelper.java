package animation; import element.DynamicElement;
import element.ship.Ship;

/**
 * 
 * @author Yun Suk Chang
 * 
 * Class that helps animation of boarding and docking.
 * It overrides moveTo
 * It also has the functionalities of DynamicAnimationHelper 
 */
public class ShipAnimationHelper extends DynamicAnimationHelper {
	/**
	 * creates ShipAnimationHelper
	 * @param s ship that uses this ShipAnimationHelper
	 */
	public ShipAnimationHelper(Ship s){
		super(s);
	}
	/**
	 * Launches BoardingAnimation
	 * @param att attacker
	 * @param target target
	 * @see BoardingAnimation
	 */
	public void board(Ship att,Ship target){
		BoardingAnimation ba = new BoardingAnimation(att,target);
	}
	/**
	 * Launches BoardingAnimation
	 * @param att attacker
	 * @param target target
	 * @param mode mode for the BoardingAnimation
	 * @see BoardingAnimation
	 */
	public void board(Ship att,Ship target,int mode){
		BoardingAnimation ba = new BoardingAnimation(att,target,mode);
	}
	/**
	 * Launches DockAnimation
	 * @param s ship that is initiating docking
	 * @param p DynamicElement that is getting docked
	 * @see DockAnimation
	 */
	public void dock(Ship s,DynamicElement p){
		DockAnimation da = new DockAnimation(s,p);
	}
	/**
	 * Launches DockAnimation
	 * @param s ship that is initiating docking
	 * @param p DynamicElement that is getting docked
	 * @param mode mode for DockAnimation
	 * @see DockAnimation
	 */
	public void dock(Ship s,DynamicElement p,int mode){
		DockAnimation da = new DockAnimation(s,p,mode);
	}
	/**
	 * overrides moveTo to use special move animation for ship (checking if ship is docked)
	 * @see MoveAnimation
	 */
	public void moveTo(int x, int y){
		MoveAnimation ma = new MoveAnimation((Ship)(this.getE()),x,y);
	}
}
