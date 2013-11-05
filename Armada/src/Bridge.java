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
		int num=imgIndex/width;
		int tempX=x+(int)(width/2*Math.cos(Math.toRadians(angle)));
		int tempY=y+(int)(width/2*Math.sin(Math.toRadians(angle)));
		System.out.println(imgIndex);
		for(int i=0;i<num;i++){
			System.out.println("Called g="+g.hashCode());
			AnimationHelper.draw(tempX, tempY, width, height, angle, ah.getImage(), g, viewRegion);
			tempX+=(int)(width*Math.cos((Math.toRadians(angle))));
			tempY+=(int)(width*Math.sin((Math.toRadians(angle))));
		}
		
		if(imgIndex%width!=0){
			
			tempX-=(int)(width/2*Math.cos((Math.toRadians(angle))))+(int)(WIDTH*(imgIndex%width)/2*Math.cos((Math.toRadians(angle))));
			tempY-=(int)(width/2*Math.sin((Math.toRadians(angle))))+(int)(WIDTH*(imgIndex%width)/2*Math.sin((Math.toRadians(angle))));
			BufferedImage temp= ah.getImage().getSubimage(0, 0, WIDTH*(imgIndex%width), ah.getImage().getHeight());
			AnimationHelper.draw(tempX, tempY, temp.getWidth(), temp.getHeight(), angle, temp, g, viewRegion);
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
