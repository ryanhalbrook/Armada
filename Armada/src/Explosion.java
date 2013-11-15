import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
	Creates an explosion animation.
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
	public Explosion(DynamicElement d){
		super(d.getX(),d.getY(),WIDTH*NUM_FRAME,HEIGHT*8,d.getAngle(),IMAGE_NAME);
		type=(int)(Math.random()*8);
		w=d.getWidth();
		h=d.getHeight();
	}
	public Explosion(DynamicElement d, int t){
		super(d.getX(),d.getY(),WIDTH*NUM_FRAME,HEIGHT*8,d.getAngle(),IMAGE_NAME);
		if(t<0||t>7)
			type=(int)(Math.random()*8);
		else
			type=t;
	}
	public void draw(Graphics g, BoundingRectangle viewRegion){
		BufferedImage temp= ah.getImage().getSubimage(WIDTH*imgIndex, HEIGHT*type, WIDTH, HEIGHT);
		AnimationHelper.draw(x, y, w, h, angle, temp, g, viewRegion);
		imgIndex++;
		
	}
	
	public boolean isDone(){
		return imgIndex>=NUM_FRAME;
	}
}
