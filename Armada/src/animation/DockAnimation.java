package animation; import element.Bridge;
import element.DynamicElement;
import element.Element;
import element.planet.Planet;
import element.ship.Ship;

/**
 * 
 * @author Yun Suk Chang
 * Shows Docking Animation
 */
public class DockAnimation implements Animation{
	Ship ship;
	DynamicElement target;
	int mode,x,y;
	/**
	 * Constructs DockingAnimation
	 * does calculation for docking animation
	 * @param s ship that is initiating docking
	 * @param p DynamicElement that is getting docked
	 */
	public DockAnimation(Ship s,DynamicElement p){
		ship=s;
		target=p;
		mode=1;
		x=p.getX();
		y=p.getY();
		
	}
	/**
	 * Constructs DockingAnimation
	 * does calculation for docking animation
	 * @param s ship that is initiating docking
	 * @param p DynamicElement that is getting docked
	 * @param m mode for docking animation (0 to do nothing, 1-5 is reserved for docking, 6 is used for undocking)
	 */
	public DockAnimation(Ship s,DynamicElement p,int m){
		ship=s;
		target=p;
		mode=m;
		if(p!=null){
			x=p.getX();
			y=p.getY();
		}
		
	}
	@Override
	/**
	 * does calculation for docking animation 
	 */
	public void run() {
		ship.setDocked(false);
		ship.setDocking(true);
	//	ship.setTargetable(false);
		ship.getAH().setMoving(true);
		int mvTime=100;
		int moveX=x;
		int moveY=y;
		double ra = ship.getAH().calcRotationAngle(moveX, moveY);
		int deltaX=moveX-ship.getX();
		int deltaY=moveY-ship.getY();
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
		int previousMode=mode;
		
		
		
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
					if(target instanceof Planet){
						deltaY-=(int)((10+ship.getWidth()/2+target.getWidth()/2)*Math.sin(Math.toRadians(ship.getAngle())));
						deltaX-=(int)((10+ship.getWidth()/2+target.getWidth()/2)*Math.cos(Math.toRadians(ship.getAngle())));
					}
					else
					{
						deltaX-=(int)((target.getWidth()/2+ship.getWidth()/2)*Math.cos(Math.toRadians(ship.getAngle())));
						deltaY-=(int)((target.getWidth()/2+ship.getWidth()/2)*Math.sin(Math.toRadians(ship.getAngle())));
						//deltaX-=(int)((10+ship.getWidth()/2)*Math.cos(Math.toRadians(ship.getAngle())));
						//deltaY-=(int)((10+ship.getWidth()/2)*Math.sin(Math.toRadians(ship.getAngle())));
					}
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
				if(target instanceof Planet&&ship.getBridge().getLength()+target.getWidth()/4>=Math.sqrt(Math.pow(ship.getX()-target.getX(), 2)+Math.pow(ship.getY()-target.getY(), 2)))
				{
					previousMode=mode;
					mode=0;
					ship.setDocked(true);
					System.out.println("mode5");
				}
				else if(target instanceof Ship&&ship.getBridge().getLength()>=Math.sqrt(Math.pow(ship.getX()-target.getX(), 2)+Math.pow(ship.getY()-target.getY(), 2))){
					previousMode=mode;
					mode=0;
					ship.setDocked(true);
					System.out.println("mode5");
				}
				else{
					ship.getBridge().extend();
					System.out.println(ship.getBridge().getLength());
				}
				try {
	        		Thread.sleep(10);
	        	} catch (InterruptedException e) {
	        		e.printStackTrace();
	        	}
			}
			else if(mode==6){
				if(ship.getBridge()==null){
					previousMode=mode;
					mode=0;
				}
				else if(ship.getBridge().getLength()<=0)
				{
					previousMode=mode;
					mode=0;
					ship.setBridge(null);

					
					System.out.println("mode6");
				}
				else{
					ship.getBridge().shorten();
					
				}
					
				try {
	        		Thread.sleep(10);
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
	/*	if(previousMode==5)
			ship.setTargetable(true);
		else
			ship.setTargetable(false);//this works iff undocking is followed by another animation
	*/	ship.setDocking(false);
		
		
	}
	@Override
	public Element[] getActors() {
		Element[] elist = {ship};
		return elist;
	}

}
