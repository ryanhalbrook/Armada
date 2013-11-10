import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/**
    DEPRECATED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    most of this is handled by GameController.
    
    Responsible for drawing game layers and dispatching mouse and keyboard events.
 */

public class ArmadaPanel extends JPanel implements MouseListener, KeyListener, MouseMotionListener, ActionListener, DynamicSizeBroadcast {

    private static final int DEFAULT_GRID_MOVE_RATE = 200;
    
    private HUDmanager hud = null;
    private ApplicationManager am;
    private BufferedImage backgroundImage = null;
	private Grid grid;
	private Menu focusMenu;
	private Timer refreshTimer = new Timer(30, this);
	private ViewLayer mainLayer = new ViewLayer(new BoundingRectangle(0,0, this.getWidth(), this.getHeight()));
	private boolean moveMode = false, gameOn=true;
	int gridMoveRate = DEFAULT_GRID_MOVE_RATE;
	int lastX = -1;
	int lastY = -1;
	
	/**
	    The only constructor.
	    @param am The application manager to notify when the game is over.
	*/
	public ArmadaPanel(ApplicationManager am) {
	    this.am = am;
	    backgroundImage = ImageLoader.getImage("ArmadaBackground2.jpg");
	    addMouseListener(this);
	    addKeyListener(this);
	    addMouseMotionListener(this);
	    ArmadaEngine engine = new ArmadaEngine();
	    GameController gc = new GameController(am, engine, this);
	    grid = new Grid(gc);
	    grid.setName("Grid Layer");
	    hud = new HUDmanager(grid);
	    mainLayer.setName("Main Layer");
	   
	    mainLayer.addSublayer(hud.getViewLayer());
	    mainLayer.addSublayer(grid);
	    
	    refreshTimer.start();
	}
	
	public void actionPerformed(ActionEvent evt) {
		if(!gameOn)return;
		if(grid.getWinner()!=null){
			//TODO add what happens when there is a winner
			gameOn=false;
			endGame();
			return;
		}
		grid.refresh();
		grid.update();
	    repaint();
	}
	
	public void endGame(){
		JFrame end = new JFrame();
		JTextArea text = new JTextArea(500,500);
		text.setFont(text.getFont().deriveFont(28.0f));
		text.setText(grid.getWinner().getPlayerName() + " has won! \n Now " + grid.getLoser().getPlayerName() +" has to clean the ceiling!" );
		end.add(text);
		end.setBounds(100, 100, 500, 500);
		end.setVisible(true);
	}
	
	public void keyReleased(KeyEvent evt) {
	    int keyCode = evt.getKeyCode();
	    if (keyCode == KeyEvent.VK_CONTROL) {
	        if (moveMode) {
	            lastX = -1; lastY = -1;
	        }
	        moveMode = !moveMode;
	    } else if ((keyCode == KeyEvent.VK_UP) | (keyCode == KeyEvent.VK_DOWN) | (keyCode == KeyEvent.VK_LEFT) | (keyCode == KeyEvent.VK_RIGHT)) {
	        gridMoveRate = DEFAULT_GRID_MOVE_RATE;
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
	    // Turn on anialiasing
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
	    // Draw a black background.
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    
	    // Draw the background image.
	    if (backgroundImage != null) {
	        g.drawImage(backgroundImage, -grid.getViewRegion().getX(), -grid.getViewRegion().getY(), null);
	    }
	    mainLayer.draw(g);
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
	
	/**
	   if a menu is currently requesting mouse focus, the MouseEvent is forwarded to the Menu.
	   Else, it is forwarded to the MenuLoader.
	 */
	public void mousePressed(MouseEvent evt) {
		if(evt.getButton() == MouseEvent.BUTTON3){
			moveMode=false;
			grid.unselect();
		}
		int x = (int)evt.getPoint().getX();
		int y = (int)evt.getPoint().getY();
		mainLayer.click(x, y);
	}
	
	public void keyPressed(KeyEvent evt) {
	    int keycode = evt.getKeyCode();
	    gridMoveRate+=10;
	    switch (keycode) {
	        case KeyEvent.VK_LEFT:
	            grid.moveViewRegion(-gridMoveRate, 0);
	            repaint();
	        break;
	        case KeyEvent.VK_RIGHT:
	            grid.moveViewRegion(gridMoveRate, 0);
	            repaint();
	        break;
	        case KeyEvent.VK_UP:
	            grid.moveViewRegion(0, -gridMoveRate);
	            repaint();
	        break;
	        case KeyEvent.VK_DOWN:
	            grid.moveViewRegion(0, gridMoveRate);
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
	            grid.cycleActiveDE();
	        break;
	        case KeyEvent.VK_SHIFT:
	            grid.nextMode();
	        break;
	        /*case KeyEvent.VK_M:
	            if (moveMode) {
	                lastX = -1; lastY = -1;
	            }
	            moveMode = !moveMode;
	        break;*/
	        case KeyEvent.VK_CONTROL:
	            if (moveMode) {
	                lastX = -1; lastY = -1;
	            }
	            moveMode = !moveMode;
	        break;
	        case KeyEvent.VK_M:
	            hud.getMap().toggleScale();
	        break;
	        case KeyEvent.VK_S:
	            grid.moveCheat();
	        break;
	    }
	}
	
	public void mouseClicked(MouseEvent evt) {}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {
		//grid.setCurrentX(0);
		//grid.setCurrentY(0);
	}
	public void mouseReleased(MouseEvent evt) {}
	public void keyTyped(KeyEvent evt) {}
	public void mouseDragged(MouseEvent evt) {
		grid.setCurrentX(0);
		grid.setCurrentY(0);
		if(hud.getMap().isIn(evt.getX(), evt.getY())){
			hud.getMap().moveMap(evt.getX(), evt.getY());
		}
	}

    /**
        @return True if moving the mouse will move the map, false otherwise.
    */
	public boolean isMoveMode() {
		return moveMode;
	}

    /**
        Sets whether moving the mouse will move the map, or allow the player to perform
        other game actions.
    */
	public void setMoveMode(boolean moveMode) {
		this.moveMode = moveMode;
	}

	public int getLastX() {
		return lastX;
	}

	public void setLastX(int lastX) {
		this.lastX = lastX;
	}

	public int getLastY() {
		return lastY;
	}

	public void setLastY(int lastY) {
		this.lastY = lastY;
	}

	public HUDmanager getHud() {
		return hud;
	}

}


	/*
	    @return The horizontal size, in pixels, of this panel.
	
	public int getWidth() {
	    return this.getWidth();
	}
	
	*/
	
	
	/*
	    @return The vertical size, in pixels, of this panel.
	
	public int getHeightSize() {
	    return this.getHeight();
	}
	*/
