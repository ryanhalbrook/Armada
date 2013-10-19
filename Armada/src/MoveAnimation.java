import java.awt.Graphics;


public class MoveAnimation implements Runnable {

	protected Element e;
	protected int x,y,t,mode;//0= rotate&move, 1= rotate only, 2=move only
	
	
	public MoveAnimation(Element inE, int inX, int inY){
		e=inE;
		x=inX;
		y=inY;
		t=100;
		mode=0;
		Thread move = new Thread(this);
		move.start();
	}
	
	public MoveAnimation(Element inE, int inX, int inY, int t){
		e=inE;
		x=inX;
		y=inY;
		this.t=t;
		mode=0;
		Thread move = new Thread(this);
		move.start();
	}
	public MoveAnimation(Element inE, int inX, int inY, int t,int m){
		e=inE;
		x=inX;
		y=inY;
		this.t=t;
		mode=m;
		Thread move = new Thread(this);
		move.start();
	}
	
	@Override
	public void run() {
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
