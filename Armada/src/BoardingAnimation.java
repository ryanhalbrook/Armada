
public class BoardingAnimation implements Runnable{




	protected DynamicElement de,target;
	protected int x,y,mode;
	
	
	public BoardingAnimation(DynamicElement att, DynamicElement target){
		de=att;
		x=target.getX();
		y=target.getY();
		mode=1;
		this.target=target;
		
		Thread board = new Thread(this);
		board.start();
	}
	
	public BoardingAnimation(DynamicElement att, DynamicElement target, int m){
		de=att;
		x=target.getX();
		y=target.getY();
		mode=m;
		this.target=target;
		
		Thread move = new Thread(this);
		move.start();
	}
	
	
	@Override
	public void run() {
		//System.out.println("moving to: " + x + ", " + y);
		int mvTime=100;
		int moveX=x;
		int moveY=y;
		double ra = de.getAH().calcRotationAngle(moveX, moveY);
		int deltaX=moveX-de.getX();
		int deltaY=moveY-de.getY();
		int deltaX2=0,deltaY2=0;
		if(Math.abs(Math.sqrt(Math.pow(deltaX, 2)+Math.pow(deltaY, 2)))<=10){
			//bridge
			
		}
		de.getAH().setAngleLeft(ra);
		de.getAH().setMoveXLeft(deltaX);
		de.getAH().setMoveYLeft(deltaY);
		
		
		while(mode!=0)
		{
			//System.out.println("a= "+getAngleLeft()+" deltaA= "+ra+" angle=" +e.getAngle());
			//System.out.println("xl= "+getMoveXLeft()+" dx= "+deltaX+" x= "+e.getX());
			//System.out.println("yl= "+getMoveYLeft()+" dy= "+deltaY+" y= "+e.getY());
			if(mode==1&&!de.getAH().rotate(ra)){
					
					mode=2;
					int x1 = de.getX()+(int)(de.getWidth()/2.0 * Math.cos(Math.toRadians(de.getAngle())));
					int y1 = de.getY()+(int)(de.getWidth()/2.0 * Math.sin(Math.toRadians(de.getAngle())));
					int x2 = de.getX()+(int)((de.getWidth()/2.0 + 5) * Math.cos(Math.toRadians(de.getAngle())));
					int y2 = de.getY()+(int)((de.getWidth()/2.0 + 5) * Math.sin(Math.toRadians(de.getAngle())));
					de.setRope(new Element(x1, y1, 1, 5, 0,"rope_straight"));
					de.setHook(new Element(x2, y2, 10, 5, 0,"gold_plunger"));
					de.getRope().setOwner(de);
					de.getHook().setOwner(de);
					
					
					deltaX=moveX-de.getRope().getX();
					deltaX2=moveX-de.getHook().getX();
					deltaY=moveY-de.getRope().getY();
					deltaY2=moveY-de.getHook().getY();
					if(deltaX>0){
						deltaX-=(int)(15*Math.cos(Math.toRadians(de.getAngle())));
					}
					else
						deltaX+=(int)(15*Math.cos(Math.toRadians(de.getAngle())));
					if(deltaY>0){
						deltaY-=(int)(15*Math.sin(Math.toRadians(de.getAngle())));
					}
					else
						deltaY+=(int)(15*Math.sin(Math.toRadians(de.getAngle())));
					
					if(deltaX2>0){
						deltaX2-=(int)(5*Math.cos(Math.toRadians(de.getAngle())));
					}
					else
						deltaX2+=(int)(5*Math.cos(Math.toRadians(de.getAngle())));
					if(deltaY2>0){
						deltaY2-=(int)(5*Math.sin(Math.toRadians(de.getAngle())));
					}
					else
						deltaY2+=(int)(5*Math.sin(Math.toRadians(de.getAngle())));
					de.getRope().getAH().setMoveXLeft(deltaX/2);
					de.getRope().getAH().setMoveYLeft(deltaY/2);
					de.getHook().getAH().setMoveXLeft(deltaX2);
					de.getHook().getAH().setMoveYLeft(deltaY2);
						
					
					double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
					if(angle<0)
						angle=360+angle;
					de.getRope().setAngle(angle);
					de.getRope().getAH().setAngleLeft(0);
					de.getHook().setAngle(angle);
					de.getHook().getAH().setAngleLeft(0);
					
					de.setBoarding(true);
					
			}
			else if(mode==2){
				if(!(de.getRope().getAH().moveHelper2(deltaX/2, deltaY/2)))
					mode=3;
				else{
					de.getRope().setWidth(de.getRope().getWidth()+10);
					de.getHook().getAH().moveHelper2(deltaX2, deltaY2);
					de.getHook().getAH().moveHelper2(deltaX2, deltaY2);
				}
			}
			else if(mode==3){
				
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
		de.setAngle(angle);
		
		de.getAH().setAngleLeft(0);
		de.getAH().setMoveXLeft(0);
		de.getAH().setMoveYLeft(0);
		de.getAH().setMoving(false);
		de.setBoarding(false);
		de.setRope(null);
		de.setHook(null);
		
	}

}
