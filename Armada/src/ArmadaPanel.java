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
	Timer refreshTimer = new Timer(20, this);
	int turn;
	boolean moveMode = false;
	int lastX = -1;
	int lastY = -1;
	
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
	
	//this should be a switch, not a bunch of if else
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
	    } else if (evt.getKeyCode() == KeyEvent.VK_Q) {
	        am.endGame();
	    } else if (evt.getKeyCode() == KeyEvent.VK_C) {
	        grid.cancelMove();
	    }else if (evt.getKeyCode() == KeyEvent.VK_0) {
	    	System.out.println("Mode 0");
	        grid.setMode(0);
	    }else if (evt.getKeyCode() == KeyEvent.VK_1) {
	    	System.out.println("Mode 1");
	        grid.setMode(1);
	    }else if (evt.getKeyCode() == KeyEvent.VK_2) {
	    	System.out.println("Mode 2");
	        grid.setMode(2);
	    }else if (evt.getKeyCode() == KeyEvent.VK_3) {
	    	System.out.println("Mode 3");
	        grid.setMode(3);
	    }
	    else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
	        grid.toggleTurn();
	    }else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
	        grid.selectNextDEThisTurn();
	    }else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
	        grid.nextMode();
	    } else if (evt.getKeyCode() == KeyEvent.VK_M) {
	        if (moveMode) {
	            lastX = -1; lastY = -1;
	        }
	        moveMode = !moveMode;
	    } else if (evt.getKeyCode() == KeyEvent.VK_CONTROL) {
	        if (moveMode) {
	            lastX = -1; lastY = -1;
	        }
	        moveMode = !moveMode;
	    }
	    
	}
	
	public void keyTyped(KeyEvent evt) {
	//System.out.println("Key event");
	}
	
	public void keyReleased(KeyEvent evt) {
	    if (evt.getKeyCode() == KeyEvent.VK_CONTROL) {
	        if (moveMode) {
	            lastX = -1; lastY = -1;
	        }
	        moveMode = !moveMode;
	    }
	//System.out.println("Key event");
	}
	
	public void mouseDragged(MouseEvent evt) {
	    
	}
	
	public void mouseMoved(MouseEvent evt) {
	    if (!moveMode) {
	        grid.mouseMoved(evt.getX(), evt.getY());
	        //repaint();
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
	
	public int turn(){
		return turn;
	}
	
	public void paintComponent(Graphics g) {
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    if (grid != null) grid.draw(g);
	    g.setColor(Color.WHITE);
	    g.drawString("Showing Region: (" + grid.getViewRegion().getX() + ", " + grid.getViewRegion().getY() + ", " + this.getWidth() + ", " + this.getHeight() + ")", 5, this.getHeight()-5);    	
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
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 * if a menu is currently requesting mouse focus, the MouseEvent is forwarded to the Menu.
	 * Else, it is forwarded to the MenuLoader.
	 */
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("Clicked");
		if(arg0.getButton() == MouseEvent.BUTTON3){
			grid.setMode(0);
		}
		this.requestFocus();
		int xx = (int)arg0.getPoint().getX();
		int yy = (int)arg0.getPoint().getY();
		grid.click(xx,yy);
	}
	
    @Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
