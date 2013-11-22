package animation; 

import element.DynamicElement;
import element.Element;
/** @Author: Yun Suk Chang
 * @Version: 111613
 * 
 * Class that helps animation of attacking.
 * It also has the functionalities of AnimationHelper
 */

public class DynamicAnimationHelper extends AnimationHelper{
	
	/**
	 * Creates DynamicAnimationHelper
	 * @param el DynamicElement using this DynamicAnimationHelper
	 */
	public DynamicAnimationHelper(DynamicElement el){
		super(el);
	}
	/**
	 * Launches AttackAnimation
	 * @param att attacker
	 * @param target target
	 * @param attacked describes which part of the DynamicElement is attacked
	 * @see AttackAnimation
	 */
	public void attack(Element att,DynamicElement target,String attacked){
		AttackAnimation aa = new AttackAnimation(att,target,attacked);
	}
	/**
	 * Launches AttackAnimation
	 * @param att attacker
	 * @param target target
	 * @param mode mode for AttackAnimation
	 * @param attacked describes which part of the DynamicElement is attacked
	 * @see AttackAnimation
	 */
	public void attack(Element att,DynamicElement target,int mode,String attacked){
		AttackAnimation aa = new AttackAnimation(att,target,mode,attacked);
	}
	
	

}
