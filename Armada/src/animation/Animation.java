package animation;

import element.Element;
/**
 * Abstract class for all animation components.
 */
public interface Animation {
	/**
	 * does necessary calculation for animation
	 */
	public void run();
	/**
	 * @return Array of Elements that is involved in the animation
	 */
	public Element[] getActors();
}
