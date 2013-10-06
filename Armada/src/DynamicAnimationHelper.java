import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/* @Author: Yun Suk Chang
 * @Version: 100513
 * 
 * Class that helps animation of moving and rotating.
 * 
 * Contains: calcRotation(int x, int y) - for calculating direction and angle the dynamic element needs to turn
 * 			 changeImage(String imageName)
 * 			 draw(Grapics g) - draws DynamicElement (DE) on given center coord. 
 * 							   (scales automatically according to given height and width)
 * 			 getters and setters for DE
 *           isIn(int x, int y) - checks if the parameter coord. is occupied by DE
 */

public class DynamicAnimationHelper{
	
	private DynamicElement e;
	private BufferedImage image;
	private AffineTransform at;
	private int imageWidth, imageHeight;
	

	
	public DynamicAnimationHelper(DynamicElement el){
		e=el;
		at= new AffineTransform();
		loadImage(e.getImage());
		imageWidth=image.getWidth();
		imageHeight=image.getHeight();
		
	}
	
	private void loadImage(String img) {
		
		try{
			File f= new File("image/"+img+".png");
			image= ImageIO.read(f);
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("IMAGE COULD NOT BE FOUND");
		}
	}
	public void changeImage(String img){
		loadImage(e.getImage());
		imageWidth=image.getWidth();
		imageHeight=image.getHeight();
	}
	public boolean isIn(int x, int y){
		return (x<e.getX()+e.getWidth()/2 && x>e.getX()-e.getWidth()/2) && 
				(y<e.getY()+e.getWidth()/2 && y>e.getY()-e.getWidth()/2);
	}
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform ori = g2.getTransform();
		
		at.translate(e.getX(), e.getY());
		at.rotate(Math.toRadians(e.getAngle()));
		at.translate(-e.getWidth()/2,-e.getHeight()/2);
		at.scale(e.getWidth()/(double)imageWidth, e.getHeight()/(double)imageHeight);
		g2.drawImage(image, at, null);
		
		
		at=ori;
	}
	//Returns direction and angle the dynamic element needs to turn
	public double calcRotationAngle(int x, int y){
		int deltaX = x-e.getX();
		int deltaY = y-e.getY();
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
		double deltaA;
		if(angle<0)
			angle=360+angle;
		
		angle-=e.getAngle();
		if(angle>=0&&angle<=180)
			deltaA=angle;
		else if(angle<0&&angle>-180)
			deltaA=angle;
		else if(angle<-180)
			deltaA=360+angle;
		else
			deltaA=angle-360;
		
		return deltaA;
		
	}
	
	public DynamicElement getDE() {
		return e;
	}

	public void setDE(DynamicElement e) {
		this.e = e;
	}

}
