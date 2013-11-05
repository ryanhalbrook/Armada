import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Explosion extends Element{
	
	private static final String IMAGE_NAME="explosions";
	private static final int NUM_FRAME=16;
	private static final int WIDTH=64;
	private static final int HEIGHT=64;
	
	private int imgIndex=0;
	private int type;
	public Explosion(DynamicElement d){
		super(d.getX(),d.getY(),d.getWidth(),d.getHeight(),d.getAngle(),IMAGE_NAME);
		type=(int)(Math.random()*8);
	}
	public Explosion(DynamicElement d, int t){
		super(d.getX(),d.getY(),d.getWidth(),d.getHeight(),d.getAngle(),IMAGE_NAME);
		if(t<0||t>7)
			type=(int)(Math.random()*8);
		else
			type=t;
	}
	public void draw(Graphics g, BoundingRectangle viewRegion){
		BufferedImage temp= ah.getImage().getSubimage(WIDTH*imgIndex, HEIGHT*type, WIDTH, HEIGHT);
		AnimationHelper.draw(x, y, width, height, angle, temp, g, viewRegion);
		imgIndex++;
		
	}
	
	public boolean isDone(){
		return imgIndex>=NUM_FRAME;
	}
}
