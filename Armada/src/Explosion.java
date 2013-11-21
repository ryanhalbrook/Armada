package src; import src.view.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 
 * @author Yun Suk Chang
 *
 * This is used for Explosion Animation
 */

public class Explosion extends Element{
	/** Establishes the string that will represent the explosion image. */
	private static final String IMAGE_NAME="explosions";
	private static final int NUM_FRAME=16;
	/** Establishes the width of the explosion animation. */
	private static final int WIDTH=64;
	/** Establishes the height of the explosion animation. */
	private static final int HEIGHT=64;
	
	private int imgIndex=0;
	private int type;
	private int w,h;
	/**
	 * Creates an explosion at the spot where DynamicElement died
	 * Explosion type is chosen at random (total 8 different types)
	 * @param d dead DynamicElement
	 */
	public Explosion(DynamicElement d){
		super(d.getX(),d.getY(),WIDTH*NUM_FRAME,HEIGHT*8,d.getAngle(),IMAGE_NAME);
		type=(int)(Math.random()*8);
		w=d.getWidth();
		h=d.getHeight();
	}
	/**
	 * Creates an explosion at the spot where DynamicElement died
	 * Explosion type is t.
	 * @param d dead DynamicElement
	 * @param t type of explosion
	 */
	public Explosion(DynamicElement d, int t){
		super(d.getX(),d.getY(),WIDTH*NUM_FRAME,HEIGHT*8,d.getAngle(),IMAGE_NAME);
		if(t<0||t>7)
			type=(int)(Math.random()*8);
		else
			type=t;
	}
	/**
	 * Draws the part of the image used for explosion animation and moves onto the next part of the image
	 * @param g Graphics used to draw the Explosion
	 * @param viewRect BoundingRectangle use to describe the region that is currently viewed
	 */
	public void draw(Graphics g, BoundingRectangle viewRegion){
		BufferedImage temp= ah.getImage().getSubimage(WIDTH*imgIndex, HEIGHT*type, WIDTH, HEIGHT);
		AnimationHelper.draw(x, y, w, h, angle, temp, g, viewRegion);
		imgIndex++;
		
	}
	/**
	 * Checks if explosion animation is done
	 * @return <code>true</code> if explosion animation is done
	 * 			<code>false</code> otherwise
	 */
	public boolean isDone(){
		return imgIndex>=NUM_FRAME;
	}
}
