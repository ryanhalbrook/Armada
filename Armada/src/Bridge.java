import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Bridge extends Element {
	private static final String IMAGE_NAME="bridge";
	
	private static final int WIDTH=1;
	//private static final int HEIGHT=480;
	
	private int imgIndex;
	public Bridge(int x2, int y2, double a){
		super(x2,y2,14,10,a,IMAGE_NAME);
		imgIndex=0;
	}
	
	public void draw(Graphics g, BoundingRectangle viewRegion){
		int num=imgIndex/super.getWidth();
		int tempX=x+(int)(width/2*Math.cos(angle));
		int tempY=y+(int)(width/2*Math.sin(angle));
		
		for(int i=0;i<num;i++){
			
			AnimationHelper.draw(tempX, tempY, width, height, angle, ah.getImage(), g, viewRegion);
			tempX+=(int)(width*Math.cos(angle));
			tempY+=(int)(width*Math.sin(angle));
		}
		
		if(imgIndex%super.getWidth()!=0){
			tempX-=(int)(width/2*Math.cos(angle))+(int)(WIDTH*(imgIndex%super.getWidth())/2*Math.cos(angle));
			tempY-=(int)(width/2*Math.sin(angle))+(int)(WIDTH*(imgIndex%super.getWidth())/2*Math.sin(angle));
			BufferedImage temp= ah.getImage().getSubimage(0, 0, WIDTH*(imgIndex%super.getWidth()), 10);
			AnimationHelper.draw(tempX, tempY, WIDTH*imgIndex, height, angle, temp, g, viewRegion);
		}
		
		
		
	}
	public int getWidth(){
		return WIDTH*imgIndex;
	}
	public void extend(){
		imgIndex++;
	}
	public void shorten(){
		imgIndex--;
	}
}
