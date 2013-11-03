import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Explosion extends Element{
	
	private static final String IMAGE_NAME="explosions";
	private static final int NUM_FRAME=16;
	private static final int WIDTH=64;
	private static final int HEIGHT=64;
	
	private int imgIndex=0;
	public Explosion(DynamicElement d){
		super(d.getX(),d.getY(),d.getWidth(),d.getHeight(),d.getAngle(),IMAGE_NAME);
		
	}
	public void draw(Graphics g, BoundingRectangle viewRegion){
		BufferedImage temp= ah.getImage().getSubimage(WIDTH*imgIndex, 0, WIDTH, HEIGHT);
		AnimationHelper.draw(x, y, width, height, angle, temp, g, viewRegion);
		imgIndex++;
		

	}
	public boolean isDone(){
		return imgIndex>=NUM_FRAME;
	}
}
