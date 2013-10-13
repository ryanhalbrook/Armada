
public class AttackAnimation implements Runnable {



		protected DynamicElement de,target;
		protected int x,y,mode;
		protected String attacked;
		
		public AttackAnimation(DynamicElement att, DynamicElement target, String attacked){
			de=att;
			x=target.getX();
			y=target.getY();
			mode=1;
			this.target=target;
			this.attacked=attacked;
			Thread move = new Thread(this);
			move.start();
		}
		
		public AttackAnimation(DynamicElement att, DynamicElement target, int m, String attacked){
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
		public void run() {
			//System.out.println("moving to: " + x + ", " + y);
			int mvTime=100;
			int moveX=x;
			int moveY=y;
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
			}
			
			while(mode!=0)
			{
				//System.out.println("a= "+getAngleLeft()+" deltaA= "+ra+" angle=" +e.getAngle());
				//System.out.println("xl= "+getMoveXLeft()+" dx= "+deltaX+" x= "+e.getX());
				//System.out.println("yl= "+getMoveYLeft()+" dy= "+deltaY+" y= "+e.getY());
				if(mode==1&&!de.getDAH().rotate(ra)){
						
							mode=0;
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
							de.getLaser1().getDAH().attack(de.getLaser1(),target,2,attacked);
							de.getLaser2().getDAH().attack(de.getLaser2(),target,2,attacked);
							de.setAttack(true);
						
				}
				else if(mode==2 && !de.getDAH().moveHelper(deltaX, deltaY, mvTime)){
					mode=0;
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
			
			de.getDAH().setAngleLeft(0);
			de.getDAH().setMoveXLeft(0);
			de.getDAH().setMoveYLeft(0);
			de.getDAH().setMoving(false);
			if(de!=null&&de.getOwner()!=null){
				if(attacked.equals("hull")){
					target.update();
					de.getOwner().update();
					target.setHull(target.getHull()-de.getOwner().getWeapons());;
					if(target.getHull() <= 0){
						target.setHull(0);
						target.setDead(true);
					}
				}
				else if(attacked.equals("engine")){
					target.update();
					de.getOwner().update();
					target.setEngine(target.getEngine()-de.getOwner().getWeapons());;
					if(target.getEngine() <= 0){
						target.setEngine(0);

					}
				}
				de.getOwner().setAttack(false);
				de.getOwner().setLaser1(null);
				de.getOwner().setLaser2(null);
			}
			
		}

	

}
