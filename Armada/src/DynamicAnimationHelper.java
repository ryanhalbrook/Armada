
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/* @Author: Yun Suk Chang
 * @Version: 101313
 * 
 * Class that helps animation of moving, rotating and attacking.
 * 
 * Contains: calcRotation(int x, int y) - for calculating direction and angle the dynamic element needs to turn
 * 			 changeImage(String imageName)
 * 			 draw(Grapics g) - draws DynamicElement (DE) on given center coord. 
 * 							   (scales automatically according to given height and width)
 * 			 getters and setters for DE
 *           isIn(int x, int y) - checks if the parameter coord. is occupied by DE
 *           rotate(double angleLeft, int rotationAngle, Graphics g) -rotates ship
 *           moveHelper(int x, int y, int time, Graphics g) - moves the ship little by little to dest
 *           getters and setters for moveXLeft, moveYLeft, angleLeft <- used for moving/rotating the ship little by little
 *           moveTo methods for moving/attacking thread
 */

public class DynamicAnimationHelper extends AnimationHelper{
	
	
	public DynamicAnimationHelper(DynamicElement el){
		super(el);
	}
	
	public void attack(Element att,DynamicElement target,String attacked){
		AttackAnimation aa = new AttackAnimation(att,target,attacked);
	}
	public void attack(Element att,DynamicElement target,int mode,String attacked){
		AttackAnimation aa = new AttackAnimation(att,target,mode,attacked);
	}
	
	

}
