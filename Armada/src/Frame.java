import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * This is the frame of the entire game.  Mostly, it handles
 * displaying and scrolling the map/JPanel and receiving keyboard input.
 * It is also opens and closes option menus as needed. 
 */


public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArmadaPanel p;
	private ImageIcon img;//Background image.  Also decides size of ArmadaPanel p
	private int x,y;//position in which ArmadaPanel is drawn.
	
	Frame(){
		super();
		initialDisplay();
	}
	
	private void initialDisplay(){
		//p=new ArmadaPanel();
		img = new ImageIcon("space.png");
		JLabel j = new JLabel(img);
		//p.add(j);
		//p.setSize(800,800);
		//p.setBackground(Color.black);
		//p.setSize(img.getIconWidth(),img.getIconHeight());
		//p.setLocation(-3000, -3000);
		//add(p);
		
		
		setBackground(Color.BLACK);
		//setDefaultCloseOperation(3);
		setSize(800,800);
		setVisible(true);
	}
	
}
