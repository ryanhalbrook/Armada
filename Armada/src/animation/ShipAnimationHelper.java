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
		AnimationQueue aq = new AnimationQueue();
		
			if(att.getPlanetDocked()!=null){
				att.setPlanetDocked(null);
				aq.add(new DockAnimation(att,null,6));
			}
			if(att.getTrader()!=null){
				if(att.getTrader().isDocked()){
					att.getTrader().undock();
				}
				else{
					aq.add(new DockAnimation(att,null,6));
					att.getTrader().setTrading(false);
					att.setTrading(false);
					att.getTrader().setTrader(null);
					att.setTrader(null);
					
				}
				
			}
		
		aq.add(ba);
		new AnimationRunner(aq);
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
		AnimationQueue aq = new AnimationQueue();
		
			if(att.getPlanetDocked()!=null){
				att.setPlanetDocked(null);
				aq.add(new DockAnimation(att,null,6));
			}
			if(att.getTrader()!=null){
				if(att.getTrader().isDocked()){
					att.getTrader().undock();
				}
				else{
					aq.add(new DockAnimation(att,null,6));
					att.getTrader().setTrading(false);
					att.setTrading(false);
					att.getTrader().setTrader(null);
					att.setTrader(null);
					
				}
			}
		
		aq.add(ba);
		new AnimationRunner(aq);
	}
	/**
	 * Launches DockAnimation
	 * @param s ship that is initiating docking
	 * @param p DynamicElement that is getting docked
	 * @see DockAnimation
	 */
	public void dock(Ship s,DynamicElement p){
		DockAnimation da = new DockAnimation(s,p);
		AnimationQueue aq = new AnimationQueue();
		
			if(s.getPlanetDocked()!=null){
				s.setPlanetDocked(null);
				aq.add(new DockAnimation(s,null,6));
			}
			if(s.getTrader()!=null){
				if(s.getTrader().isDocked()){
					s.getTrader().undock();
				}
				else{
					aq.add(new DockAnimation(s,null,6));
					s.getTrader().setTrading(false);
					s.setTrading(false);
					s.getTrader().setTrader(null);
					s.setTrader(null);
					
				}
			}
		
		aq.add(da);
		new AnimationRunner(aq);
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
		AnimationQueue aq = new AnimationQueue();
		
			if(s.getPlanetDocked()!=null){
				s.setPlanetDocked(null);
				aq.add(new DockAnimation(s,null,6));
			}
			if(s.getTrader()!=null){
				if(s.getTrader().isDocked()){
					s.getTrader().undock();
				}
				else{
					aq.add(new DockAnimation(s,null,6));
					s.getTrader().setTrading(false);
					s.setTrading(false);
					s.getTrader().setTrader(null);
					s.setTrader(null);
					
				}
			}
		
		if(p!=null && mode!=6)
			aq.add(da);
		new AnimationRunner(aq);
	}
	/**
	 * overrides moveTo to use special move animation for ship (checking if ship is docked)
	 * @see MoveAnimation
	 */
	public void moveTo(int x, int y){
		
		MoveAnimation ma = new MoveAnimation(this.getE(),x,y);
		AnimationQueue aq = new AnimationQueue();
		Ship s = (Ship)(this.getE());
		
			if(s.getPlanetDocked()!=null){
				s.setPlanetDocked(null);
				aq.add(new DockAnimation(s,null,6));
			}
			if(s.getTrader()!=null){
				if(s.getTrader().isDocked()){
					s.getTrader().undock();
				}
				else{
					aq.add(new DockAnimation(s,null,6));
					s.getTrader().setTrading(false);
					s.setTrading(false);
					s.getTrader().setTrader(null);
					s.setTrader(null);
					
				}
			}
		
		aq.add(ma);
		new AnimationRunner(aq);
	}
	
}
