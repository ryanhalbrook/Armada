import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class GameController extends ViewLayerController {
    private static final int DEFAULT_GRID_MOVE_RATE = 200;
    int gridMoveRate = DEFAULT_GRID_MOVE_RATE;
    Grid grid;
    private ArmadaEngine engine;
    private DynamicSizeBroadcast dsb;
    static final int GRID_WIDTH = 7680; // The width of the grid in pixels.
    static final int GRID_HEIGHT = 4320; // The height of the grid in pixels.
    private HUDmanager hud = null;
    private boolean moveMode = false, gameOn=true;
    ApplicationManager am = null;
    private int lastTurn = 0;
    private int quadrant = 2;
    
    private InformationPopupLayer ipl;
    
    int lastX = -1;
	int lastY = -1;
	
	private BGMPlayer bgm;
    
    // Should eventually be dynamic through the observer pattern.
    static final int PANEL_WIDTH = 960;
    static final int PANEL_HEIGHT = 540;
    
    private BoundingRectangle viewRegion = new BoundingRectangle(0, 0, 500,500); //The entire grid is 2000 by 2000 pixels. This is the region that the user sees.
    
    public GameController(ApplicationManager am, ArmadaEngine engine, DynamicSizeBroadcast dsb) {
        super(null);
        this.am = am;
        this.dsb = dsb;
        this.engine = engine;
        grid = new Grid(this);
        hud = new HUDmanager(grid, this);
        viewLayer = new ViewLayer(new BoundingRectangle(0,0, dsb));
        ipl =  new InformationPopupLayer(new BoundingRectangle(5, 45, 200, 35));
        //ArmadaHUD armadaLayer = new ArmadaHUD(new BoundingRectangle(0, 0, 100, 100), this);
        viewLayer.addSublayer(ipl);
        //viewLayer.addSublayer(armadaLayer);
        viewLayer.addSublayer(hud);
	    viewLayer.addSublayer(grid);
	    BufferedImage img = ImageLoader.getImage("GamePlayBackground.jpg");
	    viewLayer.addSublayer(new BackgroundImageViewLayer(new BoundingRectangle(0, 0, dsb), viewRegion, img));
	    bgm= new BGMPlayer();
	    bgm.play();
	    
    }
    
    public int getCurrentX() {
        return grid.getCurrentX();
    }
    public int getCurrentY() {
        return grid.getCurrentY();
    }
    
    public ArmadaEngine getEngine() {
        return engine;
    }
    
    public DynamicElement getActiveE() {
        return grid.getActiveE();
    }
    
    public void refresh(long previousTime, long currentTime) {
        super.refresh(previousTime, currentTime);
        int turn = grid.getTurn();
        if (lastTurn != turn) {
            newTurn();
        }
        lastTurn = turn;
        
        if(!gameOn) {
            return;
        }
		if(grid.getWinner()!=null){
		    viewLayer.setDrawingEnabled(false);
			//TODO add what happens when there is a winner
			gameOn=false;
			endGame();
			return;
		}
		grid.refresh(previousTime, currentTime);
		grid.update();
	    //repaint();
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
	
	public void modeChanged() {
	    ipl.showPopup("Mode: " + grid.getModeString(), new Color(0.0f, 0.5f, 0.0f));
	}
    
    public DynamicSizeBroadcast getViewSize() {
        return dsb;
    }
    
    public int getViewWidth() {
        return dsb.getWidth();
    }
    
    public int getViewHeight() {
        return dsb.getHeight();
    }
    
    public void newTurn() {
        hud.getTurnHUD().showTransition();
    }
    
    public void invalidMoveAttempt(String info) {
        ipl.showPopup(info, new Color(0.8f, 0.0f, 0.0f));
    }
    /*
        NOTE: use this sparingly, try to give the grid more info on what happened.
    */
    public void showInfo(String info) {
        ipl.showPopup(info, Color.BLUE);
    }
    
    public void mousePressed(MouseEvent evt) {
		if(evt.getButton() == MouseEvent.BUTTON3){
			moveMode=false;
			grid.unselect();
		}
		int x = (int)evt.getPoint().getX();
		int y = (int)evt.getPoint().getY();
		viewLayer.click(x, y);
	}
    
    public void update() {
		viewRegion.setWidth(getViewWidth());
		viewRegion.setHeight(getViewHeight());
	}
	
	public void moveViewRegionToPoint(int x, int y) {
	    int dx = x - viewRegion.getX();
	    int dy = y - viewRegion.getY();
	    moveViewRegion(dx, dy);
	}
    
    /**
        Moves the viewing region. Stops at boundaries.
        @param x The number of pixels to move in the x direction.
        @param y The number of pixels to move in the y direction.
    */
    public void moveViewRegion(int x, int y) {
        viewRegion.setX(viewRegion.getX()+x);
        viewRegion.setY(viewRegion.getY()+y);
        if (viewRegion.getX() < 0) viewRegion.setX(0);
        if (viewRegion.getY() < 0) viewRegion.setY(0);
        if (viewRegion.getX() + getViewWidth() > GRID_WIDTH) {
            viewRegion.setX(GRID_WIDTH - getViewWidth());
        }
        if (viewRegion.getY() + getViewHeight() > GRID_HEIGHT) {
            viewRegion.setY(GRID_HEIGHT - getViewHeight());
        }
        int q = 0;
        if (viewRegion.getX() < GRID_WIDTH / 2.0f && viewRegion.getY() < GRID_HEIGHT / 2.0f) {
            q = 2;
        } else if (viewRegion.getX() >= GRID_WIDTH / 2.0f && viewRegion.getY() >= GRID_HEIGHT / 2.0f) {
            q = 4;
        } else if (viewRegion.getX() < GRID_WIDTH / 2.0f && viewRegion.getY() >= GRID_HEIGHT / 2.0f) {
            q = 3;
        } else if (viewRegion.getX() >= GRID_WIDTH / 2.0f && viewRegion.getY() < GRID_HEIGHT / 2.0f) {
            q = 1;
        }
        
        if (quadrant != q) showInfo("Entered Quadrant: " + q);
        quadrant = q;
        
    }
    
    public BoundingRectangle getViewRegion() {
        return viewRegion;
    }
    
    public void keyPressed(KeyEvent evt) {
	    int keycode = evt.getKeyCode();
	    gridMoveRate+=10;
	    switch (keycode) {
	        case KeyEvent.VK_LEFT:
	            moveViewRegion(-gridMoveRate, 0);
	            //repaint();
	        break;
	        case KeyEvent.VK_RIGHT:
	            moveViewRegion(gridMoveRate, 0);
	            //repaint();
	        break;
	        case KeyEvent.VK_UP:
	            moveViewRegion(0, -gridMoveRate);
	            //repaint();
	        break;
	        case KeyEvent.VK_DOWN:
	            moveViewRegion(0, gridMoveRate);
	            //repaint();
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
	        case KeyEvent.VK_5:
	            grid.setMode(5);
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
	        case KeyEvent.VK_CONTROL:
	            if (moveMode) {
	                lastX = -1; lastY = -1;
	            }
	            moveMode = !moveMode;
	        break;
	        case KeyEvent.VK_M:
	            hud.getMap().toggleScale();
	        break;
	        case KeyEvent.VK_P:
	            bgm.toggleStop();
	        break;
	        case KeyEvent.VK_S:
	            grid.moveCheat();
	        break;
	    }
	}
	
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