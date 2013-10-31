import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/*
 * SCROLL DOWN FOR KEY HANDLING

 * ArmadaPanel take in mouse input to allow user to click on objects
 * displays all objects present on Grid
 
 * Responsible for drawing game elements and dispatching mouse and keyboard events.
 */

public class ArmadaPanel extends JPanel implements MouseListener, KeyListener, MouseMotionListener, ActionListener {

    private HUDmanager hud = null;
    private ApplicationManager am;
    private BufferedImage backgroundImage = null;
	private Grid grid;
	private Menu focusMenu;
	private Timer refreshTimer = new Timer(30, this);
	boolean moveMode = false;
	int lastX = -1;
	int lastY = -1;
	
	public ArmadaPanel(ApplicationManager am) {
	    this.am = am;
	    backgroundImage = ImageLoader.getImage("ArmadaBackground2.jpg");
	    addMouseListener(this);
	    addKeyListener(this);
	    addMouseMotionListener(this);
	    grid = new Grid(this);
	    hud = new HUDmanager(grid);
	    refreshTimer.start();
	}
	
	public void actionPerformed(ActionEvent evt) {
		grid.updateViewRegion();
	    repaint();
	}
	
	public void keyReleased(KeyEvent evt) {
	    if (evt.getKeyCode() == KeyEvent.VK_CONTROL) {
	        if (moveMode) {
	            lastX = -1; lastY = -1;
	        }
	        moveMode = !moveMode;
	    }
	
	}
	
	public void mouseMoved(MouseEvent evt) {
	    if (!moveMode) {
	        grid.mouseMoved(evt.getX(), evt.getY());
	    } else {
	        int xVel = 50;
	        int yVel = 50;
	        if (lastX < 0) {
	            lastX = evt.getX(); lastY = evt.getY();
	        } else {
	            grid.moveViewRegion((evt.getX()-lastX)*xVel, (evt.getY()-lastY)*yVel);
	            lastX = evt.getX(); lastY = evt.getY();
	        }
	    }
	}

	public void paintComponent(Graphics g) {
	    // Draw a black background.
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    
	    // Draw the background image.
	    if (backgroundImage != null) {
	        g.drawImage(backgroundImage, -grid.getViewRegion().getX(), -grid.getViewRegion().getY(), null);
	    }
	    
	    // Draw the grid.
	    if (grid != null) grid.draw(g);
	    
	    // Draw the HUD.
	    hud.draw(g);    	
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
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 * if a menu is currently requesting mouse focus, the MouseEvent is forwarded to the Menu.
	 * Else, it is forwarded to the MenuLoader.
	 */
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON3){
			moveMode=false;
			grid.unselect();
		}
		int xx = (int)arg0.getPoint().getX();
		int yy = (int)arg0.getPoint().getY();
		if(hud.click(xx,yy))return;
		grid.click(xx,yy);
	}
	
	public void keyPressed(KeyEvent evt) {
	    int keycode = evt.getKeyCode();
	    
	    switch (keycode) {
	        case KeyEvent.VK_LEFT:
	            grid.moveViewRegion(-100, 0);
	            repaint();
	        break;
	        case KeyEvent.VK_RIGHT:
	            grid.moveViewRegion(100, 0);
	            repaint();
	        break;
	        case KeyEvent.VK_UP:
	            grid.moveViewRegion(0, -100);
	            repaint();
	        break;
	        case KeyEvent.VK_DOWN:
	            grid.moveViewRegion(0, 100);
	            repaint();
	        break;
	        case KeyEvent.VK_Q:
	            am.endGame();
	        break;
	        case KeyEvent.VK_C:
	            grid.cancelMove();
	        break;
	        case KeyEvent.VK_1:
	            grid.setMode(1);
	        break;
	        case KeyEvent.VK_2:
	            grid.setMode(2);
	        break;
	        case KeyEvent.VK_3:
	            grid.setMode(3);
	        break;
	        case KeyEvent.VK_4:
	            grid.setMode(4);
	        break;
	        case KeyEvent.VK_ESCAPE:
	            grid.toggleTurn();
	        break;
	        case KeyEvent.VK_SPACE:
	            grid.selectNextDEThisTurn();
	        break;
	        case KeyEvent.VK_SHIFT:
	            grid.nextMode();
	        break;
	        case KeyEvent.VK_M:
	            if (moveMode) {
	                lastX = -1; lastY = -1;
	            }
	            moveMode = !moveMode;
	        break;
	        case KeyEvent.VK_CONTROL:
	            if (moveMode) {
	                lastX = -1; lastY = -1;
	            }
	            moveMode = !moveMode;
	        break;
	    }
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}
    @Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	public void keyTyped(KeyEvent evt) {}
	public void mouseDragged(MouseEvent evt) {}

}
