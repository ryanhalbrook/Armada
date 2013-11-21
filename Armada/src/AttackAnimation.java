package src;
import src.view.*;
/**
 * 
 * @author Yun Suk Chang
 * Shows Attack Animation
 */
public class AttackAnimation implements Runnable {

		protected Element de;
		protected DynamicElement target;
		protected int x,y,mode;
		protected String attacked;
		/**
		 * Constructs AttackAnimation
		 * Does the calculation for the animation of attacker attacking the target using a separate thread
		 * At the end of animation, it does damage calculation
		 * @param att attacker
		 * @param target target
		 * @param attacked describes which part of the DynamicElement is attacked
		 */
		public AttackAnimation(Element att, DynamicElement target, String attacked){
			de=att;
			x=target.getX();
			y=target.getY();
			mode=1;
			this.target=target;
			this.attacked=attacked;
			Thread move = new Thread(this);
			move.start();
		}
		/**
		 * Constructs AttackAnimation
		 * This is used to complete the animation.
		 * Do not use mode=2 before mode=1 since laser must be created before it starts its animation
		 * @param att attacker
		 * @param target target
		 * @param m mode for the AttackAnimation (0 to do nothing, 1 to rotate and create lasers, 2 for laser animation)
		 * @param attacked describes which part of the DynamicElement is attacked
		 */
		public AttackAnimation(Element att, DynamicElement target, int m, String attacked){
			de=att;
			x=target.getX();
			y=target.getY();
			mode=m;
			this.target=target;
			this.attacked=attacked;
			Thread move = new Thread(this);
			move.start();
		}
		
		
		@Override
		/**
		 * method that does the animation calculation
		 */
		public void run() {
			//System.out.println("moving to: " + x + ", " + y);
			target.setTargetable(false);
			de.setTargetable(false);
			int mvTime=100;
			int moveX=x;
			int moveY=y;
			double ra = de.getAH().calcRotationAngle(moveX, moveY);
			int deltaX=moveX-de.getX();
			int deltaY=moveY-de.getY();
			de.getAH().setAngleLeft(ra);
			if(deltaX<0)
				de.getAH().setMoveXLeft(deltaX+(int)(de.getWidth()/2.0*Math.abs(Math.cos(Math.atan2(deltaY, deltaX)))));
			else
				de.getAH().setMoveXLeft(deltaX-(int)(de.getWidth()/2.0*Math.abs(Math.cos(Math.atan2(deltaY, deltaX)))));
			if(deltaY<0)
				de.getAH().setMoveYLeft(deltaY+(int)(de.getWidth()/2.0*Math.abs(Math.sin(Math.atan2(deltaY, deltaX)))));
			else
				de.getAH().setMoveYLeft(deltaY-(int)(de.getWidth()/2.0*Math.abs(Math.sin(Math.atan2(deltaY, deltaX)))));
			if(mode==2)
			{
				double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
				if(angle<0)
					angle=360+angle;
				de.setAngle(angle);
				de.getAH().setAngleLeft(0);
			}
			
			while(mode!=0)
			{
				//System.out.println("a= "+getAngleLeft()+" deltaA= "+ra+" angle=" +e.getAngle());
				//System.out.println("xl= "+getMoveXLeft()+" dx= "+deltaX+" x= "+e.getX());
				//System.out.println("yl= "+getMoveYLeft()+" dy= "+deltaY+" y= "+e.getY());
				if(mode==1&&!de.getAH().rotate(ra)&&de instanceof DynamicElement){
						
							mode=0;
							double dx=de.getWidth()/4.0;
							double dy=de.getHeight()/2.0;
							double angle1=Math.toRadians(de.getAngle()+Math.toDegrees(Math.atan2(dx, dy)));
							double angle2=Math.toRadians(de.getAngle()-Math.toDegrees(Math.atan2(dx, dy)));
							int x1=de.getX()+ (int)(Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2))*Math.cos(angle1));
							int y1=de.getY()+ (int)(Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2))*Math.sin(angle1));
							int x2=de.getX()+ (int)(Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2))*Math.cos(angle2));
							int y2=de.getY()+ (int)(Math.sqrt(Math.pow(dx, 2)+Math.pow(dy,2))*Math.sin(angle2));
							
							SoundEffect.LASER.play();
							//SoundEffect.LASER.play();
							((DynamicElement)de).setLaser1(new Element(x1, y1, 40, 5, 0,"laser"));
							((DynamicElement)de).setLaser2(new Element(x2, y2, 40, 5, 0,"laser"));
							((DynamicElement)de).getLaser1().setOwner(de);
							((DynamicElement)de).getLaser2().setOwner(de);
							((DynamicElement)de).getDAH().attack(((DynamicElement)de).getLaser1(),target,2,attacked);
							((DynamicElement)de).getDAH().attack(((DynamicElement)de).getLaser2(),target,2,attacked);
							((DynamicElement)de).setAttack(true);
						
				}
				else if(mode==2 && !de.getAH().moveHelper2(deltaX, deltaY)){
					mode=0;
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
			if(de!=null&&de.getOwner()!=null&&de.getOwner() instanceof DynamicElement){
				if(attacked.equals("hull")){
					((DynamicElement)(de.getOwner())).attackHullHelper(target);
				}
				else if(attacked.equals("engine")){
					((DynamicElement)(de.getOwner())).attackEngineHelper(target);
				}
				((DynamicElement)(de.getOwner())).setAttack(false);
			
			}
			
			target.setTargetable(true);
			de.setTargetable(true);	
		}

	

}
