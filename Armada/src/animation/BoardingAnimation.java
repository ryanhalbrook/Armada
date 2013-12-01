package animation;
import element.Bridge;
import element.Element;
import element.ship.Ship;
import av.audio.SoundEffect;
/**
 * 
 * @author Yun
 * The class that does boarding animation
 */
public class BoardingAnimation implements Animation{




	protected Ship de,target;
	protected int x,y,mode;
	
	/**
	 * Creates BoardingAnimation
	 * does calculation for boarding animation
	 * it also changes the alliance of the target if boarding is successful 
	 * @param att attacker
	 * @param target target
	 */
	public BoardingAnimation(Ship att, Ship target){
		de=att;
		x=target.getX();
		y=target.getY();
		mode=1;
		this.target=target;
		
		
	}
	/**
	 * Creates BoardingAnimation
	 * does calculation for boarding animation
	 * it also changes the alliance of the target if boarding is successful 
	 * @param att attacker
	 * @param target target
	 * @param m mode for boarding animation (0 to do nothing, 1-5 is reserved for boarding, 6 is unboarding)
	 */
	public BoardingAnimation(Ship att, Ship target, int m){
		de=att;
		if(target!=null){
			x=target.getX();
			y=target.getY();
		}
		else{
			x=0;
			y=0;
		}
		mode=m;
		this.target=target;
		
		
	}
	
	
	@Override
	/**
	 * method that does calculation for boarding animation
	 */
	public void run() {
		//System.out.println("moving to: " + x + ", " + y);
		int mvTime=100;
		int moveX=x;
		int moveY=y;
		double ra = de.getAH().calcRotationAngle(moveX, moveY);
		double angle=0;
		int deltaX=moveX-de.getX();
		int deltaY=moveY-de.getY();
		int deltaX2=0,deltaY2=0,deltaX3=0,deltaY3=0;
		
		//de.setTargetable(false);
		
/*		if(mode!=6&&Math.abs(Math.sqrt(Math.pow(deltaX, 2)+Math.pow(deltaY, 2)))+de.getWidth()/2<=10){
			//bridge
			mode=5;
			ra2=de.getAH().calcRotationAngle(target.getAngle());
			de.getAH().setAngleLeft(ra2);
		}
		else
*/			de.getAH().setAngleLeft(ra);
		
		
		while(mode!=0)
		{
			//System.out.println("a= "+getAngleLeft()+" deltaA= "+ra+" angle=" +e.getAngle());
			//System.out.println("xl= "+getMoveXLeft()+" dx= "+deltaX+" x= "+e.getX());
			//System.out.println("yl= "+getMoveYLeft()+" dy= "+deltaY+" y= "+e.getY());
			if(mode==1&&!de.getAH().rotate(ra)){
					
					mode=2;
					angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
					if(angle<0)
						angle=360+angle;
					de.setAngle(angle);
					
					int x1 = de.getX()+(int)(de.getWidth()/2.0 * Math.cos(Math.toRadians(de.getAngle())));
					int y1 = de.getY()+(int)(de.getWidth()/2.0 * Math.sin(Math.toRadians(de.getAngle())));
					int x2 = de.getX()+(int)((de.getWidth()/2.0 + 5) * Math.cos(Math.toRadians(de.getAngle())));
					int y2 = de.getY()+(int)((de.getWidth()/2.0 + 5) * Math.sin(Math.toRadians(de.getAngle())));
					de.setRope(new Element(x1, y1, 1, 1, 0,"ropev_01"));
					de.setHook(new Element(x2, y2, 10, 5, 0,"plunger"));
					de.getRope().setOwner(de);
					de.getHook().setOwner(de);
					
					
					deltaX2=moveX-de.getRope().getX();
					deltaX3=moveX-de.getHook().getX();
					deltaY2=moveY-de.getRope().getY();
					deltaY3=moveY-de.getHook().getY();
					
					
					if(Math.abs(deltaX)>Math.abs(deltaY)){
						if(target.getWidth()/2>10)
							deltaX-=(int)((target.getWidth()/2+de.getWidth()/2)*Math.cos(Math.toRadians(de.getAngle())));
						else
							deltaX-=(int)((10+de.getWidth()/2)*Math.cos(Math.toRadians(de.getAngle())));
						deltaY-=(int)((10+de.getWidth()/2)*Math.sin(Math.toRadians(de.getAngle())));
					}
					else{
						if(target.getHeight()/2>10)
							deltaY-=(int)((target.getWidth()/2+de.getWidth()/2)*Math.sin(Math.toRadians(de.getAngle())));
						else
							deltaY-=(int)((10+de.getWidth()/2)*Math.sin(Math.toRadians(de.getAngle())));
						deltaX-=(int)((10+de.getWidth()/2)*Math.cos(Math.toRadians(de.getAngle())));
						
					}
					
					deltaX3-=(int)(5*Math.cos(Math.toRadians(de.getAngle())));
					deltaX2-=(int)(10*Math.cos(Math.toRadians(de.getAngle())));
					
			
					deltaY3-=(int)(5*Math.sin(Math.toRadians(de.getAngle())));
					deltaY2-=(int)(10*Math.sin(Math.toRadians(de.getAngle())));
					
					de.getAH().setMoveXLeft(deltaX);
					de.getAH().setMoveYLeft(deltaY);
					
					de.getRope().getAH().setMoveXLeft(deltaX2/2);
					de.getRope().getAH().setMoveYLeft(deltaY2/2);
					de.getHook().getAH().setMoveXLeft(deltaX3);
					de.getHook().getAH().setMoveYLeft(deltaY3);
						
					
					angle = Math.toDegrees(Math.atan2(deltaY2, deltaX2));
					if(angle<0)
						angle=360+angle;
					de.getRope().setAngle(angle);
					de.getRope().getAH().setAngleLeft(0);
					de.getHook().setAngle(angle);
					de.getHook().getAH().setAngleLeft(0);
					
					
					
					de.setBoarding(true);
					
			}
			else if(mode==2){
				if(!(de.getRope().getAH().moveHelper2(deltaX2, deltaY2))){
					mode=3;
					de.getRope().getAH().setMoveXLeft(deltaX2/2);
					de.getRope().getAH().setMoveYLeft(deltaY2/2);
				}
					
				else{
					de.getRope().setWidth(de.getRope().getWidth()+10);
					de.getHook().getAH().moveHelper2(deltaX3, deltaY3);
					de.getHook().getAH().moveHelper2(deltaX3, deltaY3);
				}
			}
			else if(mode==3){
				int tempX=de.getRope().getX();
				int tempY=de.getRope().getY();
				de.getRope().getAH().moveHelper(deltaX, deltaY,mvTime*2);
				boolean d =de.getAH().moveHelper(deltaX, deltaY,mvTime);
				if(!d){
					mode=4;
					
					de.getAH().setAngleLeft(90);
					de.setRope(null);
					de.setHook(null);
					
				}
				else{
					int dist = (int)Math.sqrt(Math.pow(tempX-de.getRope().getX(), 2)+Math.pow(tempY-de.getRope().getY(), 2));
					if(de.getRope().getWidth()-dist*2>0)
						de.getRope().setWidth(de.getRope().getWidth()-dist*2);
					else
						de.getRope().setWidth(1);
				}
			}
			else if(mode==4&&!de.getAH().rotate(90)){
				if(angle+90<360)
					de.setAngle(angle+90);
				else
					de.setAngle(angle+90-360);
				de.setBridge(new Bridge(de.getX(), de.getY(),de.getAngle()-90));
				mode=5;
			}
			else if(mode==5){
				if(de.getBridge().getLength()>=Math.sqrt(Math.pow(de.getX()-target.getX(), 2)+Math.pow(de.getY()-target.getY(), 2)))
				{
					try {
		        		Thread.sleep(100);
		        	} catch (InterruptedException e) {
		        		e.printStackTrace();
		        	}
					if( calculateBoarding(de,target)){
						
						target.setAlliance(de.getAlliance());
						if(target.isTrading()){
							target.untrade();
						}
					}
					
					mode=6;
				}
				else
					de.getBridge().extend();
				try {
	        		Thread.sleep(10);
	        	} catch (InterruptedException e) {
	        		e.printStackTrace();
	        	}
			}
			else if(mode==6){
				
				if(de.getBridge().getLength()<=0)
				{
					mode=0;
					de.setBridge(null);
					de.setBoarding(false);
					de.getAH().setMoving(false);
				//	de.setTargetable(true);
					System.out.println("mode6");
				}
				else{
					de.getBridge().shorten();
					
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
		
		
		de.getAH().setAngleLeft(0);
		de.getAH().setMoveXLeft(0);
		de.getAH().setMoveYLeft(0);
		
		de.setRope(null);
		de.setHook(null);
		
	}
	/**
	 * does possibility calculation for boarding
	 * @param att attacker
	 * @param target target
	 * @return <code>true</code> if boarding was successful
	 * 			<code>false</code> otherwise
	 */
	private boolean calculateBoarding(Ship att, Ship target){
		//stub
		SoundEffect.PIRATEH.play();
		return true;
	}
	public Element[] getActors() {
		Element[] elist = {de,target};
		return elist;
	}
}
