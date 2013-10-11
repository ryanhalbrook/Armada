import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
/*
 * ArmadaPanel take in mouse input to allow user to click on objects
 * displays all objects present on Grid
 
 * Responsible for drawing game elements and dispatching mouse and keyboard events.
 */

public class ArmadaPanel extends JPanel implements MouseListener, KeyListener, MouseMotionListener, ActionListener {

    ApplicationManager am;
	Frame f;
	Grid grid;
	Menu focusMenu;
	Timer refreshTimer = new Timer(1, this);
	int turn;
	
	public ArmadaPanel(ApplicationManager am, GameManager gm) {
	    //grid = new Grid(gm.getElements());
	    this.am = am;
	    addMouseListener(this);
	    addKeyListener(this);
	    addMouseMotionListener(this);
	    grid = new Grid(this);
	    this.setFocusable(true);
	    this.requestFocus();
	    this.requestFocusInWindow();
	    refreshTimer.start();
	}
	
	public void actionPerformed(ActionEvent evt) {
	    repaint();
	}
	/*
	public ArmadaPanel(JFrame in, ApplicationManager am) {
	this.am = am;
		turn = 1;
		addMouseListener(this);
		addKeyListener(this);
		this.setFocusable(true);
	    this.requestFocus();
	}
	public ArmadaPanel(Frame in, ApplicationManager am){
	this.am = am;
		turn = 1;
		f=in;
	}
	*/
	public void keyPressed(KeyEvent evt) {
	    System.out.println("Key pressed");
	    if(evt.getKeyCode() == KeyEvent.VK_LEFT) {
	        grid.moveViewRegion(-100, 0);
	        System.out.println("Left key");
	        repaint();
	    } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
	        grid.moveViewRegion(100, 0);
	        repaint();
	    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
	        grid.moveViewRegion(0, -100);
	        repaint();
	    } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
	        grid.moveViewRegion(0, 100);
	        repaint();
	    }
	}
	
	public void keyTyped(KeyEvent evt) {
	//System.out.println("Key event");
	}
	
	public void keyReleased(KeyEvent evt) {
	//System.out.println("Key event");
	}
	
	public void mouseDragged(MouseEvent evt) {
	    
	}
	
	public void mouseMoved(MouseEvent evt) {
	    grid.mouseMoved(evt.getX(), evt.getY());
	    repaint();
	}
	
	public int turn(){
		return turn;
	}
	
	public void paintComponent(Graphics g) {
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    if (grid != null) grid.draw(g);
	    g.setColor(Color.WHITE);
	    g.drawString("Showing Region: (" + grid.getViewRegion().getX() + ", " + grid.getViewRegion().getY() + ", " + this.getWidth() + ", " + this.getHeight() + ")", 30, 30);    	
	}
	
	/*
	 * sets the menu that will get a mouse event
	 */
	public void requestClick(Menu m){
		focusMenu=m;
	}
	
	public void endRequest(){
		focusMenu=null;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
		System.out.println("Clicked");
		this.requestFocus();
		int xx = (int)arg0.getPoint().getX();
		int yy = (int)arg0.getPoint().getY();
		grid.click(xx,yy);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 * if a menu is currently requesting mouse focus, the MouseEvent is forwarded to the Menu.
	 * Else, it is forwarded to the MenuLoader.
	 */
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
