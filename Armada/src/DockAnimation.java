
public class DockAnimation implements Runnable{
	Ship ship;
	DynamicElement planet;
	int mode,x,y;
	public DockAnimation(Ship s,DynamicElement p){
		ship=s;
		planet=p;
		mode=1;
		x=p.getX();
		y=p.getY();
		Thread dock = new Thread(this);
		dock.start();
	}
	public DockAnimation(Ship s,DynamicElement p,int m){
		ship=s;
		planet=p;
		mode=m;
		if(p!=null){
			x=p.getX();
			y=p.getY();
		}
		Thread dock = new Thread(this);
		dock.start();
	}
	@Override
	public void run() {
		int mvTime=100;
		int moveX=x;
		int moveY=y;
		double ra = ship.getAH().calcRotationAngle(moveX, moveY);
		int deltaX=moveX-ship.getX();
		int deltaY=moveY-ship.getY();
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
		
		ship.setTargetable(false);
		ship.getAH().setMoving(true);
		ship.setDocking(true);
		
		ship.getAH().setAngleLeft(ra);
		while(mode!=0)
		{
			//System.out.println("a= "+getAngleLeft()+" deltaA= "+ra+" angle=" +e.getAngle());
			//System.out.println("xl= "+getMoveXLeft()+" dx= "+deltaX+" x= "+e.getX());
			//System.out.println("yl= "+getMoveYLeft()+" dy= "+deltaY+" y= "+e.getY());
			if(mode==1&&!ship.getAH().rotate(ra)){
					
					mode=3;
					if(angle<0)
						angle=360+angle;
					ship.setAngle(angle);
					
					deltaY-=(int)((10+ship.getWidth()/2+planet.getWidth()/2)*Math.sin(Math.toRadians(ship.getAngle())));
					deltaX-=(int)((10+ship.getWidth()/2+planet.getWidth()/2)*Math.cos(Math.toRadians(ship.getAngle())));
					
					ship.getAH().setMoveXLeft(deltaX);
					ship.getAH().setMoveYLeft(deltaY);
					
			}
			else if(mode==3&&!ship.getAH().moveHelper(deltaX, deltaY,mvTime)){
				
				mode=4;
				ship.getAH().setAngleLeft(90);	
				
			}
			else if(mode==4&&!ship.getAH().rotate(90)){
				if(angle+90<360)
					ship.setAngle(angle+90);
				else
					ship.setAngle(angle+90-360);
				ship.setBridge(new Bridge(ship.getX(), ship.getY(),ship.getAngle()-90));
				mode=5;
			}
			else if(mode==5){
				if(ship.getBridge().getLength()+planet.getWidth()/4>=Math.sqrt(Math.pow(ship.getX()-planet.getX(), 2)+Math.pow(ship.getY()-planet.getY(), 2)))
				{
					mode=0;
					
					System.out.println("mode5");
				}
				else{
					ship.getBridge().extend();
					System.out.println(ship.getBridge().getLength());
				}
				try {
	        		Thread.sleep(100);
	        	} catch (InterruptedException e) {
	        		e.printStackTrace();
	        	}
			}
			else if(mode==6){
				
				if(ship.getBridge().getLength()<=0)
				{
					mode=0;
					ship.setBridge(null);
					
					System.out.println("mode6");
				}
				else{
					ship.getBridge().shorten();
					
				}
					
				try {
	        		Thread.sleep(100);
	        	} catch (InterruptedException e) {
	        		e.printStackTrace();
	        	}
			}
			try {
        		Thread.sleep(10);
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        	
		}
		
		
		ship.getAH().setAngleLeft(0);
		ship.getAH().setMoveXLeft(0);
		ship.getAH().setMoveYLeft(0);
		ship.getAH().setMoving(false);
		ship.setTargetable(true);
		ship.setDocking(false);
		
		
	}

}
