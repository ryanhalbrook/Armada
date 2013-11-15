import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
	Creates a teleporter animation.
*/

public class Teleporter extends Element{
	/** Establishes the string that will represent the teleporter image. */
	private static final String IMAGE_NAME="teleporter";
	private static final int NUM_FRAME=8;
	/** Establishes the width of the teleporter animation. */
	private static final int WIDTH=140;
	/** Establishes the height of the teleporter animation. */
	private static final int HEIGHT=140;
	
	private int imgIndex=0;
	private Ship s;
	private int destX,destY,w,h,mode;
	private BufferedImage backup;
	public Teleporter(Ship d, int dX,int dY){
		super(d.getX(),d.getY(),WIDTH*NUM_FRAME,HEIGHT,d.getAngle(),IMAGE_NAME);
		mode=0;
		s=d;
		s.setTargetable(false);
		destX=dX;
		destY=dY;
		w=d.getWidth();
		h=d.getHeight();
		backup=s.getAH().getImage();
	}
	public Teleporter(Ship d, int dX,int dY,int mode){
		super(d.getX(),d.getY(),WIDTH*NUM_FRAME,HEIGHT,d.getAngle(),IMAGE_NAME);
		this.mode=mode;
		s=d;
		destX=dX;
		destY=dY;
		s.setTargetable(false);
		if(mode==1)
			imgIndex=NUM_FRAME-1;
		w=d.getWidth();
		h=d.getHeight();
	}
	public void draw(Graphics g, BoundingRectangle viewRegion){
		BufferedImage temp= ah.getImage().getSubimage(WIDTH*imgIndex, 0, WIDTH, HEIGHT);
		RescaleOp rop=null;
		if(mode==0){
			float[] scales = { 1f, 1f, 1f, 1f-1f/(NUM_FRAME)*(imgIndex+1) };
			float[] offsets = new float[4];
			 rop= new RescaleOp(scales, offsets, null);
			imgIndex++;
		}
			
		else if(mode ==1){
			float[] scales = { 1f, 1f, 1f, 1f/(NUM_FRAME)*(NUM_FRAME-(imgIndex)) };
			float[] offsets = new float[4];
			 rop = new RescaleOp(scales, offsets, null);
			imgIndex--;
		}
		if(rop==null)
			System.out.println("transparency fail!!");
		else
			s.getAH().setImage(rop.filter(backup, null));
		AnimationHelper.draw(x, y, w, h, angle, temp, g, viewRegion);
	}
	
	public boolean isDone(){
		if(mode==0&&imgIndex>=NUM_FRAME){
			mode=1;
			imgIndex=NUM_FRAME-1;
			s.setX(destX);
			s.setY(destY);
			x=destX;
			y=destY;
			return false;
		}
		else if(mode==1&&imgIndex<0){
			s.setTargetable(true);
			return true;
		}
		else
			return false;
			
	}
}
