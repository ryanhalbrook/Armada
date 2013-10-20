
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;
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

public class AnimationHelper{
	
	private Element e;
	private BufferedImage image;
	private AffineTransform at;
	private int imageWidth, imageHeight;
	private double angleLeft;
	private int moveXLeft;
	private int moveYLeft;
	private double[] moveVar;//for moving the ship more accurately
	private boolean moving;
	
	private final double ANGLE_PER_FRAME=2;
	private final double MOVE_PER_FRAME=5;
	
	public AnimationHelper(Element el){
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
		if(e.getAngle()==0||e.getAngle()==180)
			return (x<e.getX()+e.getWidth()/2.0 && x>e.getX()-e.getWidth()/2.0) && 
					(y<e.getY()+e.getHeight()/2.0 && y>e.getY()-e.getHeight()/2.0);
		if(e.getAngle()==90||e.getAngle()==270)
			return (x<e.getX()+e.getHeight()/2.0 && x>e.getX()-e.getHeight()/2.0) && 
					(y<e.getY()+e.getWidth()/2.0 && y>e.getY()-e.getWidth()/2.0);
		
		double rectAngle = Math.atan2(e.getHeight()/2.0, e.getWidth()/2.0);
		double halfRectDiagonalLen=Math.sqrt(Math.pow(e.getWidth()/2.0,2)+Math.pow(e.getHeight()/2.0,2));
		double radAngle = Math.toRadians(e.getAngle());
		double x1 =halfRectDiagonalLen*Math.cos(radAngle+rectAngle);
		double y1 =halfRectDiagonalLen*Math.sin(radAngle+rectAngle);
		double x2 = halfRectDiagonalLen*Math.cos(radAngle-rectAngle);
		double y2 =halfRectDiagonalLen*Math.sin(radAngle-rectAngle);
		if(x1<0)
			x1-=0.5;
		else
			x1+=0.5;
		if(x2<0)
			x2-=0.5;
		else
			x2+=0.5;
		if(y1<0)
			y1-=0.5;
		else
			y1+=0.5;
		if(y2<0)
			y2-=0.5;
		else
			y2+=0.5;
		
	
		Point[] rectPoints = new Point[4];
		rectPoints[0]=new Point(e.getX()+(int)x1,e.getY()+(int)y1);
		rectPoints[1]=new Point(e.getX()+(int)x2,e.getY()+(int)y2);
		rectPoints[2]=new Point(e.getX()+(int)(x1*-1),e.getY()+(int)(y1*-1));
		rectPoints[3]=new Point(e.getX()+(int)(x2*-1),e.getY()+(int)(y2*-1));

		int maxX=(int) Math.max(rectPoints[0].getX(), Math.max(rectPoints[1].getX(),Math.max(rectPoints[2].getX(),rectPoints[3].getX())));
		int maxY=(int) Math.max(rectPoints[0].getY(), Math.max(rectPoints[1].getY(),Math.max(rectPoints[2].getY(),rectPoints[3].getY())));
		int minX=(int) Math.min(rectPoints[0].getX(), Math.min(rectPoints[1].getX(),Math.min(rectPoints[2].getX(),rectPoints[3].getX())));
		int minY=(int) Math.min(rectPoints[0].getY(), Math.min(rectPoints[1].getY(),Math.min(rectPoints[2].getY(),rectPoints[3].getY())));
		
		Point[] rectPoints2 =new Point[4];
		for(int i=0;i<rectPoints.length;i++){
			if((int)(rectPoints[i].getX())==minX)
				rectPoints2[0]=rectPoints[i];
			else if((int)(rectPoints[i].getY())==minY)
				rectPoints2[1]=rectPoints[i];
			else if((int)(rectPoints[i].getX())==maxX)
				rectPoints2[2]=rectPoints[i];
			else
				rectPoints2[3]=rectPoints[i];
		}
		rectPoints=rectPoints2;
		
		double m_a = (rectPoints[1].getY()-rectPoints[0].getY())/((double)(rectPoints[1].getX()-rectPoints[0].getX()));
		double y_a = m_a*x + rectPoints[1].getY()-m_a*rectPoints[1].getX();
		double m_b = (rectPoints[2].getY()-rectPoints[1].getY())/((double)(rectPoints[2].getX()-rectPoints[1].getX()));
		double y_b = m_b*x + rectPoints[2].getY()-m_b*rectPoints[2].getX();
		double m_c = (rectPoints[3].getY()-rectPoints[2].getY())/((double)(rectPoints[3].getX()-rectPoints[2].getX()));
		double y_c = m_c*x + rectPoints[3].getY()-m_c*rectPoints[3].getX();
		double m_d = (rectPoints[0].getY()-rectPoints[3].getY())/((double)(rectPoints[0].getX()-rectPoints[3].getX()));
		double y_d = m_d*x + rectPoints[0].getY()-m_d*rectPoints[0].getX();
	
		
		if(rectPoints[3].getX()>rectPoints[1].getX())
			return ( (x>=rectPoints[0].getX() && x<=rectPoints[1].getX()) && (y<=y_d && y>=y_a) )
					||( (x>=rectPoints[1].getX() && x<=rectPoints[3].getX()) && (y<=y_d && y>=y_b) )
					||( (x>=rectPoints[3].getX() && x<=rectPoints[2].getX()) && (y<=y_c && y>=y_b) );
		else
			return ( (x>=rectPoints[0].getX() && x<=rectPoints[3].getX()) && (y<=y_d && y>=y_a) )
					||( (x>=rectPoints[3].getX() && x<=rectPoints[1].getX()) && (y<=y_c && y>=y_a) )
					||( (x>=rectPoints[1].getX() && x<=rectPoints[2].getX()) && (y<=y_c && y>=y_b) );
	
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
		else if(angle<=-180)
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
		if (Game.DEBUG) System.out.println("xl= "+moveXLeft+" yl= "+moveYLeft);
		if ((doneMovingY&&doneMovingX)){
			moveVar[0]=0;
			moveVar[1]=0;
			moving=false;
			return false;
		}
		return true;
			
		
	}
	
	public boolean moveHelper2(int dx, int dy){
		boolean doneMovingX=false;
		boolean doneMovingY=false;
		if(dx!=0 && (double)moveXLeft/dx>0){
			if(dx<0)
				moveVar[0]-=MOVE_PER_FRAME*Math.abs(Math.cos(Math.atan2(dy, dx)));
			else
				moveVar[0]+=MOVE_PER_FRAME*Math.abs(Math.cos(Math.atan2(dy, dx)));
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
			
			if(dy<0)
				moveVar[1]-=MOVE_PER_FRAME*Math.abs(Math.sin(Math.atan2(dy, dx)));
			else
				moveVar[1]+=MOVE_PER_FRAME*Math.abs(Math.sin(Math.atan2(dy, dx)));
			
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

	public Element getE() {
		return e;
	}

	public void setE(Element e) {
		this.e = e;
	}

	

}
