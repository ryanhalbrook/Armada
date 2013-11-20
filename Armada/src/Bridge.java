import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Bridge object used for docking and boarding
 *
 */

public class Bridge extends Element {
	private static final String IMAGE_NAME="lightbridgev_02";
	private static final int EXTEND_RATE=6;
	
	private int length=1;
	//private static final int HEIGHT=480;
	
	/**
	 * creates Bridge object
	 * @param x2 x-coordinate the bridge is going to extend to
	 * @param y2 y-coordinate the bridge is going to extend to
	 * @param a fixed angle of the bridge
	 */
	public Bridge(int x2, int y2, double a){
		super(x2,y2,1,11,a,IMAGE_NAME);
		
	}
	/**
	 * draws the bridge
	 * @param g Graphics used to draw the Bridge
	 * @param viewRect BoundingRectangle use to describe the region that is currently viewed
	 */
	public void draw(Graphics g, BoundingRectangle viewRegion){
		
		ah.draw(length, height, g, viewRegion);
		
	}
	/**
	 * gets the length of the bridge
	 * @return the length of the bridge
	 */
	public int getLength(){
		return length;
	}
	/**
	 * increments the length of the Bridge by EXTEND_RATE
	 */
	public void extend(){
		length+=EXTEND_RATE;
	}
	/**
	 * decrements the length of the Bridge by EXTEND_RATE
	 */
	public void shorten(){
		length-=EXTEND_RATE;
	}
}
