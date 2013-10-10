import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import javax.swing.*;
/*
 * ArmadaPanel take in mouse input to allow user to click on objects
 * displays all objects present on Grid
 
 * Responsible for drawing game elements and dispatching mouse and keyboard events.
 */

public class ArmadaPanel extends JPanel implements MouseListener {

    ApplicationManager am;
	Frame f;
	Grid grid;
	Menu focusMenu;
	int turn;
	
	public ArmadaPanel(ApplicationManager am, GameManager gm) {
	    //grid = new Grid(gm.getElements());
	    this.am = am;
	    addMouseListener(this);
	    grid = new Grid();
	}
	
	public ArmadaPanel(JFrame in, ApplicationManager am) {
	this.am = am;
		turn = 1;
		addMouseListener(this);
	}
	public ArmadaPanel(Frame in, ApplicationManager am){
	this.am = am;
		turn = 1;
		f=in;
	}
	
	public int turn(){
		return turn;
	}
	
	public void paintComponent(Graphics g) {
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    if (grid != null) grid.draw(g);
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
