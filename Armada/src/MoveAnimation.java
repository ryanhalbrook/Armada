package src; import src.view.*;
import java.awt.Graphics;

/**
 * 
 * @author Yun Suk Chang
 * Does Move Animation
 */
public class MoveAnimation implements Runnable {

	protected Element e;
	protected int x,y,t,mode;//0= rotate&move, 1= rotate only, 2=move only
	boolean canMove;
	/**
	 * Creates MoveAnimation
	 * does calculation for move animation 
	 * @param inE Element that is going to move
	 * @param inX x destination
	 * @param inY y destination
	 */
	public MoveAnimation(Element inE, int inX, int inY){
		e=inE;
		x=inX;
		y=inY;
		t=100;
		mode=0;
		canMove=true;
		Thread move = new Thread(this);
		move.start();
	}
	/**
	 * Creates MoveAnimation
	 * does calculation for move animation 
	 * @param inE Element that is going to move
	 * @param inX x destination
	 * @param inY y destination
	 * @param t total time for moving part of the animation
	 */
	public MoveAnimation(Element inE, int inX, int inY, int t){
		e=inE;
		x=inX;
		y=inY;
		this.t=t;
		mode=0;
		canMove=true;
		Thread move = new Thread(this);
		move.start();
	}
	/**
	 * Creates MoveAnimation
	 * does calculation for move animation 
	 * @param inE Element that is going to move
	 * @param inX x destination
	 * @param inY y destination
	 * @param t total time for moving part of the animation
	 * @param m mode for the MoveAnimation (0 to move and rotate, 1 to rotate, 2 to move)
	 */
	public MoveAnimation(Element inE, int inX, int inY, int t,int m){
		e=inE;
		x=inX;
		y=inY;
		this.t=t;
		mode=m;
		canMove=true;
		Thread move = new Thread(this);
		move.start();
	}
	/**
	 * Creates special MoveAnimation for Ship
	 * does calculation for move animation 
	 * @param inE Ship that is going to move
	 * @param inX x destination
	 * @param inY y destination
	 */
	public MoveAnimation(Ship inE, int inX, int inY){
		e=inE;
		x=inX;
		y=inY;
		t=100;
		mode=0;
		canMove=(!inE.isDocking());
	
		Thread move = new Thread(this);
		move.start();
	}
	@Override
	/**
	 * does calculation for move animation
	 */
	public void run() {
		while(!canMove){
			if(e instanceof Ship)
				canMove=(!((Ship)e).isDocking());
			else
				break;
			
			try {
        		Thread.sleep(10);
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
		}
		e.setTargetable(false);
		//System.out.println("moving to: " + x + ", " + y);
		int mvTime=t;
		int moveX=x;
		int moveY=y;
		int status=1;
		double ra = e.getAH().calcRotationAngle(moveX, moveY);
		int deltaX=moveX-e.getX();
		int deltaY=moveY-e.getY();
		e.getAH().setAngleLeft(ra);
		e.getAH().setMoveXLeft(deltaX);
		e.getAH().setMoveYLeft(deltaY);
		
		
		
		if(mode==2)
		{
			double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
			if(angle<0)
				angle=360+angle;
			e.setAngle(angle);
			e.getAH().setAngleLeft(0);
			status=2;
		}
		
		while(status!=0)
		{
			//System.out.println("a= "+getAngleLeft()+" deltaA= "+ra+" angle=" +e.getAngle());
			//System.out.println("xl= "+getMoveXLeft()+" dx= "+deltaX+" x= "+e.getX());
			//System.out.println("yl= "+getMoveYLeft()+" dy= "+deltaY+" y= "+e.getY());
			
			if((mode==0||mode==1)&&status==1&&!e.getAH().rotate(ra)){
					if(mode==0)
						status=2;
					else if(mode==1){
						status=0;
					}
			}
			else if(status==2 && !e.getAH().moveHelper(deltaX, deltaY, mvTime)){
				status=0;
			}
			
			try {
        		Thread.sleep(10);
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        	
		}
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
		if(angle<0)
			angle=360+angle;
		e.setAngle(angle);
		if(mode!=1){
			e.setX(moveX);
			e.setY(moveY);
		}
		e.getAH().setAngleLeft(0);
		e.getAH().setMoveXLeft(0);
		e.getAH().setMoveYLeft(0);
		e.getAH().setMoving(false);

		e.setTargetable(true);
	}

}
