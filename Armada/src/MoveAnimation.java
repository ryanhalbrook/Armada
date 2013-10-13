import java.awt.Graphics;


public class MoveAnimation implements Runnable {

	protected DynamicElement de;
	protected int x,y,t,mode;//0= rotate&move, 1= rotate only, 2=move only, 3= attack animation
	
	
	public MoveAnimation(DynamicElement inDE, int inX, int inY){
		de=inDE;
		x=inX;
		y=inY;
		t=100;
		mode=0;
		Thread move = new Thread(this);
		move.start();
	}
	
	public MoveAnimation(DynamicElement inDE, int inX, int inY, int t){
		de=inDE;
		x=inX;
		y=inY;
		this.t=t;
		mode=0;
		Thread move = new Thread(this);
		move.start();
	}
	public MoveAnimation(DynamicElement inDE, int inX, int inY, int t,int m){
		de=inDE;
		x=inX;
		y=inY;
		this.t=t;
		mode=m;
		Thread move = new Thread(this);
		move.start();
	}
	
	@Override
	public void run() {
		//System.out.println("moving to: " + x + ", " + y);
		int mvTime=t;
		int moveX=x;
		int moveY=y;
		int status=1;
		double ra = de.getDAH().calcRotationAngle(moveX, moveY);
		int deltaX=moveX-de.getX();
		int deltaY=moveY-de.getY();
		de.getDAH().setAngleLeft(ra);
		de.getDAH().setMoveXLeft(deltaX);
		de.getDAH().setMoveYLeft(deltaY);
		
		if(mode==2)
		{
			double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
			if(angle<0)
				angle=360+angle;
			de.setAngle(angle);
			de.getDAH().setAngleLeft(0);
			status=2;
		}
		
		while(status!=0)
		{
			//System.out.println("a= "+getAngleLeft()+" deltaA= "+ra+" angle=" +e.getAngle());
			//System.out.println("xl= "+getMoveXLeft()+" dx= "+deltaX+" x= "+e.getX());
			//System.out.println("yl= "+getMoveYLeft()+" dy= "+deltaY+" y= "+e.getY());
			if((mode==0||mode==1||mode==3)&&status==1&&!de.getDAH().rotate(ra)){
					if(mode==0)
						status=2;
					else if(mode==1){
						status=0;
					}
					else{
						status=0;
						double dx=de.getWidth()/4.0;
						double dy=de.getHeight()/2.0;
						double angle1=Math.toRadians(de.getAngle()+Math.atan2(dx, dy));
						double angle2=Math.toRadians(de.getAngle()-Math.atan2(dx, dy));
						int x1=de.getX()+ (int)(Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2))*Math.cos(angle1));
						int y1=de.getY()+ (int)(Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2))*Math.sin(angle1));
						int x2=de.getX()+ (int)(Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2))*Math.cos(angle2));
						int y2=de.getY()+ (int)(Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2))*Math.sin(angle2));
						
						de.setLaser1(new DynamicElement(x1, y1, 40, 10, "fighter_red", 0));
						de.setLaser2(new DynamicElement(x2, y2, 40, 10, "fighter_blue", 0));
						de.getLaser1().setOwner(de);
						de.getLaser2().setOwner(de);
						de.getLaser1().getDAH().moveTo(de.getTarget().getX(), de.getTarget().getY(),100,2);
						de.getLaser2().getDAH().moveTo(de.getTarget().getX(), de.getTarget().getY(),100,2);
						de.setAttack(true);
					}
			}
			else if(status==2 && !de.getDAH().moveHelper(deltaX, deltaY, mvTime)){
				status=0;
			}
			
			try {
        		Thread.sleep(10);
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        	//de.getDAH().draw(g, rect);
		}
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
		if(angle<0)
			angle=360+angle;
		de.setAngle(angle);
		if(mode!=1&&mode!=3){
			de.setX(moveX);
			de.setY(moveY);
		}
		de.getDAH().setAngleLeft(0);
		de.getDAH().setMoveXLeft(0);
		de.getDAH().setMoveYLeft(0);
		de.getDAH().setMoving(false);
		if(de!=null&&de.getOwner()!=null){
			de.getOwner().setAttack(false);
			de.getOwner().setLaser1(null);
			de.getOwner().setLaser2(null);
		}
		
	}

}
