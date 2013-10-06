import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
/* @Author: Yun Suk Chang
 * @Version: 100513
 * 
 * Made for Creating && Testing animation
 * 
 * This class have basics that are needed for animation of moving from one place to another.
 * Implement this to the main panel and change the part that I marked "---default background repaint---"
 * 
 * Please do not alter DynamicAnimationHelper (DAH) except for commenting since the order matters.
 * 
 * Contains: move(int x, int y, int movingTime) 
 *           overriden paintComponent(Graphics g)
 *           and other helper method for moving and rotating
 */
public class TestPanel extends JPanel {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		
		private int mvTime;//total moving time (includes rotation time) <<very inaccurate
		private int status;//0=draw DE on its coord. 1=rotate DE. 2= move DE to new coord.
		private int moveX;
		private int moveY;
		private double rotationAngle;
		private double angleLeft;
		private int deltaX;
		private int deltaY;
		private int moveXLeft;
		private int moveYLeft;
		private int index;
		private ArrayList<DynamicAnimationHelper> da;
		
		public TestPanel(){
			da=new ArrayList<DynamicAnimationHelper>();
			mvTime=0;
			status=0;
			moveX=0;
			moveY=0;
			rotationAngle=0;
			angleLeft=0;
			deltaX=0;
			deltaY=0;
			moveXLeft=0;
			moveYLeft=0;
			index=-1;
		}
		public TestPanel(DynamicElement de){
			da=new ArrayList<DynamicAnimationHelper>();
			da.add( new DynamicAnimationHelper(de));
			de.setIndex(0);
			mvTime=0;
			status=0;
			moveX=0;
			moveY=0;
			rotationAngle=0;
			angleLeft=0;
			deltaX=0;
			deltaY=0;
			moveXLeft=0;
			moveYLeft=0;
			index=-1;
		}
		public void addDE(DynamicElement de){
			da.add(new DynamicAnimationHelper(de));
			de.setIndex(da.size()-1);
		}
		public void move(int i, int x, int y, int t){
			mvTime=t;
			moveX=x;
			moveY=y;
			status=1;
			angleLeft=rotationAngle=da.get(i).calcRotationAngle(moveX, moveY);
			deltaX=moveXLeft=moveX-da.get(i).getDE().getX();
			deltaY=moveYLeft=moveY-da.get(i).getDE().getY();
			index=i;
			while(status!=0)
			{
	        	repaint();
	        	try {
	        		Thread.sleep(10);
	        	} catch (InterruptedException e) {
	        		e.printStackTrace();
	        	}
			}
			double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
			if(angle<0)
				angle=360+angle;
			da.get(i).getDE().setAngle(angle);
			da.get(i).getDE().setX(moveX);
			da.get(i).getDE().setY(moveY);
			repaint();
		}
		public void paintComponent(Graphics g){
			//-------default background repaint--------\\
			Graphics2D g2 = (Graphics2D) g;
			Color c= g2.getColor();
			g2.setColor(getBackground());
			g2.fillRect(0,0,this.getWidth(),this.getHeight());
			g2.setColor(c);
			//------------------------------------------\\
			
			//--------code needed for move()----------------\\
			switch(status){
			case 0:
				
				System.out.println("a= "+angleLeft+" deltaA= "+rotationAngle+" angle=" +da.get(index).getDE().getAngle());
				System.out.println("xl= "+moveXLeft+" dx= "+deltaX+" x= "+da.get(index).getDE().getX());
				System.out.println("yl= "+moveYLeft+" dy= "+deltaY+" y= "+da.get(index).getDE().getY());
				break;
			case 1:				
				System.out.println("a= "+angleLeft+" deltaA= "+rotationAngle+" angle=" +da.get(index).getDE().getAngle());
				rotate(g);
				break;
			case 2:
				System.out.println("xl= "+moveXLeft+" dx= "+deltaX+" x= "+da.get(index).getDE().getX());
				System.out.println("yl= "+moveYLeft+" dy= "+deltaY+" y= "+da.get(index).getDE().getY());
				moveHelper(g);
				break;
			}
			for(int i=0;i<da.size();i++){
				da.get(i).draw(g);
			}
			
			//------------------------------------------\\
		}
		public void rotate(Graphics g){
			
			if(rotationAngle!=0 && angleLeft/rotationAngle>0)
			{
				da.get(index).getDE().setAngle(da.get(index).getDE().getAngle()+rotationAngle/(mvTime/2));
				
	        	angleLeft-=rotationAngle/(mvTime/2);
	        	
	        }
			else
				status=2;
		}
		public void moveHelper(Graphics g){
			boolean doneMovingX=false;
			boolean doneMovingY=false;
			if(deltaX!=0 && (double)moveXLeft/deltaX>0){
				int temp=deltaX/(mvTime/2);
				if(temp==0)
					temp=1;
				da.get(index).getDE().setX(da.get(index).getDE().getX()+temp);
				
				moveXLeft-=temp;
			}
			else
				doneMovingX=true;
			if(deltaY!=0 && (double)moveYLeft/deltaY>0){
				int temp=deltaY/(mvTime/2);
				if(temp==0)
					temp=1;
				da.get(index).getDE().setY(da.get(index).getDE().getY()+temp);
				
				moveYLeft-=temp;
			}
			else
				doneMovingY=true;
			if(doneMovingY&&doneMovingX)
				status=0;
			
		}
		public static void main(String[] args) {
			//---Testing---
			
			int x=200;
			int y=200;
			int w=50;
			int h=50;
			double an=45;
			String img="fighter";
			int s=100;
			int all = 1;
			
			DynamicElement de1 = new DynamicElement(x,y,w,h,an,img,0,0,0,s,all,0,0);
			DynamicElement de2 = new DynamicElement(300,100,w,h,90,img,0,0,0,200,2,0,0);
			TestPanel tp = new TestPanel(de1);
			tp.addDE(de2);
			
			JFrame frame = new JFrame("Animation Test");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			int frameSize=800;
			
			frame.getContentPane().add( BorderLayout.CENTER, tp );
			frame.setSize( frameSize, frameSize );
	        frame.setVisible(true);
	        
	        tp.move(0,500,600,200);//<-change this values to test
	        tp.move(1,500,600,200);
		}
}
