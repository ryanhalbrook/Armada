import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
/*
 * ArmadaPanel take in mouse input to allow user to click on objects
 * displays all objects present on Grid
 */

public class ArmadaPanel extends JPanel implements MouseListener {

	Frame f;
	Grid g;
	int turn;
	Menu focusMenu;
	
	public void ArmadaPanel(Frame in){
		turn = 1;
		f=in;
	}
	
	public int turn(){
		return turn;
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
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
