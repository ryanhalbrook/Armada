
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/* @Author: Yun Suk Chang
 * @Version: 101313
 * 
 * Class that helps animation of moving, rotating and attacking.
 * 
 * Contains: calcRotation(int x, int y) - for calculating direction and angle the dynamic element needs to turn
 * 			 changeImage(String imageName)
 * 			 draw(Grapics g) - draws DynamicElement (DE) on given center coord. 
 * 							   (scales automatically according to given height and width)
 * 			 getters and setters for DE
 *           isIn(int x, int y) - checks if the parameter coord. is occupied by DE
 *           rotate(double angleLeft, int rotationAngle, Graphics g) -rotates ship
 *           moveHelper(int x, int y, int time, Graphics g) - moves the ship little by little to dest
 *           getters and setters for moveXLeft, moveYLeft, angleLeft <- used for moving/rotating the ship little by little
 *           moveTo methods for moving/attacking thread
 */

public class DynamicAnimationHelper{
	
	private DynamicElement e;
	private BufferedImage image;
	private AffineTransform at;
	private int imageWidth, imageHeight;
	private double angleLeft;
	private int moveXLeft;
	private int moveYLeft;
	private double[] moveVar;//for moving the ship more accurately
	private boolean moving;
	
	private final double ANGLE_PER_FRAME=2;
	
	public DynamicAnimationHelper(DynamicElement el){
		e=el;
		at= new AffineTransform();
		loadImage(e.getImage());
		imageWidth=image.getWidth();
		imageHeight=image.getHeight();
		angleLeft=0;
		moveXLeft=0;
		moveYLeft=0;
		moveVar= new double[2];
		moving = false;
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
		if(moving)
			return false;
		return (x<e.getX()+e.getWidth()/2 && x>e.getX()-e.getWidth()/2) && 
				(y<e.getY()+e.getWidth()/2 && y>e.getY()-e.getWidth()/2);
	}
	public void draw(Graphics g, Rectangle viewRect){
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform ori = g2.getTransform();
		//g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
		at.translate((e.getX()-viewRect.getX()), e.getY()-viewRect.getY());
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
	public boolean rotate(double ra){
		
		if(ra!=0 && angleLeft/ra>0)
		{
			moving=true;
			if(ra<0){
				e.setAngle(e.getAngle()-ANGLE_PER_FRAME);
				angleLeft+=ANGLE_PER_FRAME;
			}
			else{
				e.setAngle(e.getAngle()+ANGLE_PER_FRAME);
				angleLeft-=ANGLE_PER_FRAME;
			}
			
        	return true;
        }
		else
			return false;
	}
	
	public boolean moveHelper(int dx, int dy, int t){
		boolean doneMovingX=false;
		boolean doneMovingY=false;
		if(dx!=0 && (double)moveXLeft/dx>0){
			moveVar[0]+=dx/((double)t);
			int temp=0;
			if(Math.abs(moveVar[0])<0)
				temp=0;
			else{
				temp=(int)moveVar[0];
				moveVar[0]-=temp;
			}
			e.setX(e.getX()+temp);
			
			moveXLeft-=temp;
		}
		else
			doneMovingX=true;
		if(dy!=0 && (double)moveYLeft/dy>0){
			
			moveVar[1]+=dy/((double)t);
			
			int temp2=0;
			if(Math.abs(moveVar[1])<0)
				temp2=0;
			else{
				temp2=(int)moveVar[1];
				moveVar[1]-=temp2;
			}
			
			e.setY(e.getY()+temp2);
			
			moveYLeft-=temp2;
		}
		else
			doneMovingY=true;
		System.out.println("xl= "+moveXLeft+" yl= "+moveYLeft);
		if ((doneMovingY&&doneMovingX)){
			moveVar[0]=0;
			moveVar[1]=0;
			moving=false;
			return false;
		}
		return true;
			
		
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void moveTo(int inX, int inY){
		MoveAnimation move = new MoveAnimation(e,inX,inY);
		
	}
	public void moveTo(int inX, int inY,int t){
		MoveAnimation move = new MoveAnimation(e,inX,inY,t);
		
	}
	public void moveTo(int inX, int inY,int t,int m){
		MoveAnimation move = new MoveAnimation(e,inX,inY,t,m);
		
	}
	public void attack(DynamicElement att,DynamicElement target,String attacked){
		AttackAnimation aa = new AttackAnimation(att,target,attacked);
	}
	public void attack(DynamicElement att,DynamicElement target,int mode,String attacked){
		AttackAnimation aa = new AttackAnimation(att,target,mode,attacked);
	}
	public double getAngleLeft() {
		return angleLeft;
	}

	public void setAngleLeft(double angleLeft) {
		this.angleLeft = angleLeft;
	}

	public int getMoveXLeft() {
		return moveXLeft;
	}

	public void setMoveXLeft(int moveXLeft) {
		this.moveXLeft = moveXLeft;
	}

	public int getMoveYLeft() {
		return moveYLeft;
	}

	public void setMoveYLeft(int moveYLeft) {
		this.moveYLeft = moveYLeft;
	}

	public DynamicElement getDE() {
		return e;
	}

	public void setDE(DynamicElement e) {
		this.e = e;
	}

	

}
