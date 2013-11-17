
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
/** 
 * @Author: Yun Suk Chang
 * @Version: 111613
 * 
 * Class that helps drawing and animation of moving and rotating.
 * It is also responsible for image loading, storing and rendering.
 * 
 */

public class AnimationHelper{
	
	private Element e;
	private BufferedImage image;
	//private AffineTransform at;

	private double angleLeft;//tracks how much more to rotate
	private int moveXLeft;//tracks how much more to move in x-direction
	private int moveYLeft;//tracks how much more to move in y-direction

	

	private double[] moveVar;//for moving the ship more accurately
	private boolean moving;
	
	private final double ANGLE_PER_FRAME=2;
	private final double MOVE_PER_FRAME=5;

	/**
	 * Constructs AnimationHelper and loads image of the Element
	 * @param el Element that uses this AnimationHelper
	 */
	public AnimationHelper(Element el){
		e=el;
		//at= new AffineTransform();
		loadImage(e.getImage());
		
		angleLeft=0;
		moveXLeft=0;
		moveYLeft=0;
		moveVar= new double[2];
		moving = false;
	}
	/**
	 * Scales the given image to given width and height and draws the image at the given location.
	 * The image is rotated to 0 degrees.
	 * @param x x-coordinate relative to the region viewed
	 * @param y y-coordinate relative to the region viewed
	 * @param w width of the scaled image
	 * @param h height of the scaled image
	 * @param image BufferedImage used for the Element
	 * @param g Graphics used to draw the Element
	 * @param viewRect BoundingRectangle use to describe the region that is currently viewed
	 */
	public static void draw(int x,int y,int w,int h,BufferedImage image, Graphics g, BoundingRectangle viewRect){
		
		Graphics2D g2 = (Graphics2D)g;
		
		AffineTransform at= new AffineTransform();
		
		int imageWidth=image.getWidth();
		int imageHeight=image.getHeight();
		
		//g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
		at.translate((x-viewRect.getX()), y-viewRect.getY());
		//at.rotate(Math.toRadians(e.getAngle()));
		at.translate(-w/2.0,-h/2.0);
		at.scale(w/(double)imageWidth, h/(double)imageHeight);
		g2.drawImage(image, at, null);
		
		
	}
	/**
	 * Scales the given image to given width and height and draws the image at the given location.
	 * The image is rotated to the given angle.
	 * @param x x-coordinate relative to the region viewed
	 * @param y y-coordinate relative to the region viewed
	 * @param w width of the scaled image
	 * @param h height of the scaled image
	 * @param a angle the image is rotated to
	 * @param image BufferedImage used for the Element
	 * @param g Graphics used to draw the Element
	 * @param viewRect BoundingRectangle use to describe the region that is currently viewed
	 */
	public static void draw(int x,int y,int w,int h,double a,BufferedImage image, Graphics g, BoundingRectangle viewRect){

		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at= new AffineTransform();
		
		int imageWidth=image.getWidth();
		int imageHeight=image.getHeight();
		
		//g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
		at.translate((x-viewRect.getX()), y-viewRect.getY());
		at.rotate(Math.toRadians(a));
		at.translate(-w/2.0,-h/2.0);
		at.scale(w/(double)imageWidth, h/(double)imageHeight);
		g2.drawImage(image, at, null);

	}
	/**
	 * Loads the image with given name, scales the image to given width and height and draws the image at the given location.
	 * The image is rotated to 0 degrees.
	 * @param x x-coordinate relative to the region viewed
	 * @param y y-coordinate relative to the region viewed
	 * @param w width of the scaled image
	 * @param h height of the scaled image
	 * @param image BufferedImage used for the Element
	 * @param g Graphics used to draw the Element
	 * @param viewRect BoundingRectangle use to describe the region that is currently viewed
	 */
	public static void draw(int x,int y,int w,int h,String img, Graphics g, BoundingRectangle viewRect){

		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at= new AffineTransform();
		BufferedImage image = loadImg(img, w, h);
		int imageWidth=image.getWidth();
		int imageHeight=image.getHeight();
		
		//g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
		at.translate((x-viewRect.getX()), y-viewRect.getY());
		//at.rotate(Math.toRadians(e.getAngle()));
		at.translate(-w/2.0,-h/2.0);
		at.scale(w/(double)imageWidth, h/(double)imageHeight);
		g2.drawImage(image, at, null);

	}
	/**
	 * Loads the image with given name, scales the image to given width and height and draws the image at the given location.
	 * The image is rotated to the given angle.
	 * @param x x-coordinate relative to the region viewed
	 * @param y y-coordinate relative to the region viewed
	 * @param w width of the scaled image
	 * @param h height of the scaled image
	 * @param a angle the image is rotated to
	 * @param image BufferedImage used for the Element
	 * @param g Graphics used to draw the Element
	 * @param viewRect BoundingRectangle use to describe the region that is currently viewed
	 */
	public static void draw(int x,int y,int w,int h,double a,String img, Graphics g, BoundingRectangle viewRect){

		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at= new AffineTransform();
		BufferedImage image = loadImg(img, w, h);
		int imageWidth=image.getWidth();
		int imageHeight=image.getHeight();
		
		//g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
		at.translate((x-viewRect.getX()), y-viewRect.getY());
		at.rotate(Math.toRadians(a));
		at.translate(-w/2.0,-h/2.0);
		at.scale(w/(double)imageWidth, h/(double)imageHeight);
		g2.drawImage(image, at, null);

	}
	/**
	 * Gets the image with given name from the ImageLoader and scales the image to given width and height
	 * @param img image name
	 * @param w width of the scaled image
	 * @param h height of the scaled image
	 * @return scaled BufferedImage
	 * @see ImageLoader
	 */
	private static BufferedImage loadImg(String img, int w, int h){
		BufferedImage image = ImageLoader.getImage(img+".png");
		AffineTransform at = new AffineTransform();
		at.scale(w/(double)image.getWidth(), h/(double)image.getHeight());
		AffineTransformOp scaleOp = 
		   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return scaleOp.filter(image, null);
	}
	/**
	 * Gets the image with given name from the ImageLoader and scales the image to element's width and height
	 * @param img image name
	 */
	private void loadImage(String img) {

		image = ImageLoader.getImage(img+".png");
		AffineTransform at = new AffineTransform();
		at.scale(e.getWidth()/(double)image.getWidth(), e.getHeight()/(double)image.getHeight());
		AffineTransformOp scaleOp = 
		   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		image=scaleOp.filter(image, null);
		
	}
	/**
	 * Calls loadImage(String img) to change image
	 * @param img image name
	 */
	public void changeImage(String img){
		loadImage(e.getImage());
	}
	/**
	 * changes the width of the image
	 * @param w width of the scaled image
	 */
	public void setImageWidth(int w){
		AffineTransform at = new AffineTransform();
		at.scale(w/(double)image.getWidth(), 1);
		AffineTransformOp scaleOp = 
		   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		image=scaleOp.filter(image, null);
	}
	/**
	 * changes the image's height
	 * @param h height of the scaled image
	 */
	public void setImageHeight(int h){
		AffineTransform at = new AffineTransform();
		at.scale(1, h/(double)image.getHeight());
		AffineTransformOp scaleOp = 
		   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		image=scaleOp.filter(image, null);
	}
	/**
	 * Checks if the given coordinate is in the region that Element covers
	 * @param x x-coordinate relative to the region viewed
	 * @param y y-coordinate relative to the region viewed
	 * @return <code>true</code> if the given coordinate is in the region that Element covers
	 * 			<code>false</code> otherwise
	 */
	public boolean isIn(int x, int y){
		if(moving)
			return false;
		//return (x<e.getX()+e.getWidth()/2.0 && x>e.getX()-e.getWidth()/2.0) && 
		//		(y<e.getY()+e.getHeight()/2.0 && y>e.getY()-e.getHeight()/2.0);
		
		if(e.getAngle()==0||e.getAngle()==180)
			return (x<e.getX()+e.getWidth()/2.0 && x>e.getX()-e.getWidth()/2.0) && 
					(y<e.getY()+e.getHeight()/2.0 && y>e.getY()-e.getHeight()/2.0);
		if(e.getAngle()==90||e.getAngle()==270)
			return (x<e.getX()+e.getHeight()/2.0 && x>e.getX()-e.getHeight()/2.0) && 
					(y<e.getY()+e.getWidth()/2.0 && y>e.getY()-e.getWidth()/2.0);
		
		double BoundingRectangle = Math.atan2(e.getHeight()/2.0, e.getWidth()/2.0);
		double halfRectDiagonalLen=Math.sqrt(Math.pow(e.getWidth()/2.0,2)+Math.pow(e.getHeight()/2.0,2));
		double radAngle = Math.toRadians(e.getAngle());
		double x1 =halfRectDiagonalLen*Math.cos(radAngle+BoundingRectangle);
		double y1 =halfRectDiagonalLen*Math.sin(radAngle+BoundingRectangle);
		double x2 = halfRectDiagonalLen*Math.cos(radAngle-BoundingRectangle);
		double y2 =halfRectDiagonalLen*Math.sin(radAngle-BoundingRectangle);
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
		for(int i=0;i<rectPoints.length;i++){
		//	System.out.println(rectPoints[i]);
			if(rectPoints[i]==null){
				if(maxX-minX==e.getWidth())
					return (x<e.getX()+e.getWidth()/2.0 && x>e.getX()-e.getWidth()/2.0) && 
							(y<e.getY()+e.getHeight()/2.0 && y>e.getY()-e.getHeight()/2.0);
				else
					return (x<e.getX()+e.getHeight()/2.0 && x>e.getX()-e.getHeight()/2.0) && 
							(y<e.getY()+e.getWidth()/2.0 && y>e.getY()-e.getWidth()/2.0);
			}
		}
		
		
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
	/**
	 * Draws the Element based on its state
	 * @param g Graphics used to draw the Element
	 * @param viewRect BoundingRectangle use to describe the region that is currently viewed
	 */
	public void draw(Graphics g, BoundingRectangle viewRect){
		if(!viewRect.isIn(e.getX(), e.getY())) return;
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at= new AffineTransform();
		//AffineTransform ori = g2.getTransform();
		//g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
		at.translate((e.getX()-viewRect.getX()), e.getY()-viewRect.getY());
		at.rotate(Math.toRadians(e.getAngle()));
		at.translate(-e.getWidth()/2.0,-e.getHeight()/2.0);
		
		g2.drawImage(image, at, null);
		//at=ori;
	}
	/**
	 * Draws the scaled Element based on its state and given width and height
	 * It does not changes the Element's image nor Element's width and height
	 * @param width the width of the scaled image
	 * @param height the height of the scaled image
	 * @param g Graphics used to draw the Element
	 * @param viewRect BoundingRectangle use to describe the region that is currently viewed
	 */
	public void draw(int width,int height, Graphics g, BoundingRectangle viewRect){
		if(!viewRect.isIn(e.getX(), e.getY())) return;
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at= new AffineTransform();
		//AffineTransform ori = g2.getTransform();
		//g.fillRect(x-viewRect.getX(), y-viewRect.getY(), width, height);
		at.translate((e.getX()-viewRect.getX()), e.getY()-viewRect.getY());
		at.rotate(Math.toRadians(e.getAngle()));
		at.translate(-e.getWidth()/2.0,-e.getHeight()/2.0);
		at.scale(width/(double)image.getWidth(), height/(double)image.getHeight());
		g2.drawImage(image, at, null);
		//at=ori;
	}
	 
	/**
	 * calculates the amount of angle the element has to rotate based on the coordinate of the destination
	 * @param x	x-coordinate of the destination
	 * @param y	x-coordinate of the destination
	 * @return	angle the element has to rotate
	 */
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
	/**
	 * calculates the amount of angle the element has to rotate based on the final angle after the rotation
	 * @param a final angle after the rotation
	 * @return angle the element has to rotate
	 */
	public double calcRotationAngle(double a){
		
		double angle = a;
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
	/**
	 * gradually changes element's angle
	 * rotating speed is determined by ANGLE_PER_FRAME
	 * <p>
	 * Make sure to setAngleLeft before using this method
	 * @param ra the angle element has to rotate
	 * @return <code>true</code> if the element is done rotating
	 * 			<code>false</code> otherwise
	 */
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
	/**
	 * gradually changes the element's coordinate
	 * total moving time is equal to t
	 * <p>
	 * Make sure to setMoveXLeft and setMoveYLeft before using this method
	 * @param dx amount of x coordinate element has to move
	 * @param dy amount of y coordinate element has to move
	 * @param t total moving time
	 * @return <code>true</code> if the element is done moving
	 * 			<code>false</code> otherwise
	 */
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
	/**
	 * gradually changes the element's coordinate
	 * moving speed is determined by MOVE_PER_FRAME
	 * <p>
	 * Make sure to setMoveXLeft and setMoveYLeft before using this method
	 * @param dx amount of x coordinate element has to move
	 * @param dy amount of y coordinate element has to move
	 * @return <code>true</code> if the element is done moving
	 * 			<code>false</code> otherwise
	 */
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
	/**
	 * setter for moving
	 * @param moving
	 */
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	/**
	 * Launches MoveAnimation
	 * @param inX x-coordinate the ship has to move to
	 * @param inY y-coordinate the ship has to move to
	 * @see MoveAnimation
	 */
	public void moveTo(int inX, int inY){
		MoveAnimation move = new MoveAnimation(e,inX,inY);
		
	}
	/**
	 * Launches MoveAnimation
	 * @param inX x-coordinate the ship has to move to
	 * @param inY y-coordinate the ship has to move to
	 * @param t total time of the moving part of the animation
	 * @see MoveAnimation
	 */
	public void moveTo(int inX, int inY,int t){
		MoveAnimation move = new MoveAnimation(e,inX,inY,t);
		
	}
	/**
	 * Launches MoveAnimation
	 * @param inX x-coordinate the ship has to move to
	 * @param inY y-coordinate the ship has to move to
	 * @param t total time of the moving part of the animation
	 * @param m mode for the MoveAnimation
	 * @see MoveAnimation
	 */
	public void moveTo(int inX, int inY,int t,int m){
		MoveAnimation move = new MoveAnimation(e,inX,inY,t,m);
		
	}
	/**
	 * getter for angleLeft
	 * @return remaining angle the element has to rotate
	 */
	public double getAngleLeft() {
		return angleLeft;
	}
	/**
	 * sets the remaining angle the element has to rotate
	 * @param angleLeft remaining angle the element has to rotate
	 */
	public void setAngleLeft(double angleLeft) {
		this.angleLeft = angleLeft;
	}
	/**
	 * gets the remaining x distance element has to move
	 * @return remaining x distance element has to move
	 */
	public int getMoveXLeft() {
		return moveXLeft;
	}
	/**
	 * sets the remaining x distance element has to move
	 * @param moveXLeft remaining x distance element has to move
	 */
	public void setMoveXLeft(int moveXLeft) {
		this.moveXLeft = moveXLeft;
	}
	/**
	 * gets the remaining y distance element has to move
	 * @return remaining y distance element has to move
	 */
	public int getMoveYLeft() {
		return moveYLeft;
	}
	/**
	 * sets the remaining y distance element has to move
	 * @param moveYLeft remaining y distance element has to move
	 */
	public void setMoveYLeft(int moveYLeft) {
		this.moveYLeft = moveYLeft;
	}
	/**
	 * Gets the element using this AnimationHelper
	 * @return element using this AnimationHelper
	 */
	public Element getE() {
		return e;
	}
	/**
	 * sets the element using this AnimationHelper
	 * @param e element using this AnimationHelper
	 */
	public void setE(Element e) {
		this.e = e;
	}
	/**
	 * gets the image element is using
	 * @return	BufferedImage that element is using
	 */
	public BufferedImage getImage(){
		return image;
	}
	/**
	 * sets the image element is using
	 * @param img	BufferedImage that element is using
	 */
	public void setImage(BufferedImage img){
		image=img;
	}
}
