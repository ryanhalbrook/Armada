package view.controller;

import av.audio.BGMPlayer;
import av.visual.ImageLoader;
import view.BackgroundImageViewLayer;
import view.BoundingRectangle;
import view.DynamicSize;
import view.Grid;
import view.InformationPopupLayer;
import view.ViewLayer;
import view.hud.HUDmanager;
import view.hud.MapHUD;

import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.*;

import javax.swing.*;

import element.DynamicElement;
import game.ApplicationManager;
import game.ArmadaEngine;

import java.awt.image.*;

public class GameController extends ViewLayerController {
    private static final int DEFAULT_GRID_MOVE_RATE = 200;
    int gridMoveRate = DEFAULT_GRID_MOVE_RATE;
    private Grid grid;
    private ArmadaEngine engine;
    private DynamicSize dsb;
    static final int GRID_WIDTH = ArmadaEngine.GRID_WIDTH;
    static final int GRID_HEIGHT = ArmadaEngine.GRID_HEIGHT;
    private HUDmanager hud = null;
    private boolean moveMode = false, gameOn=true;
    ApplicationManager am = null;
    private int lastTurn = 0;
    private int quadrant = 2;
    private boolean mouseOffScreen = false;
    
    private InformationPopupLayer ipl;
    
    int lastX = -1;
	int lastY = -1;
	
	private BGMPlayer bgm;
    
    private BoundingRectangle viewRegion = new BoundingRectangle(0, 0, 500,500); //The entire grid is 2000 by 2000 pixels. This is the region that the user sees.
    
    /**
    Creates a new instance of GameController
    */
    public GameController(ApplicationManager am, ArmadaEngine engine, DynamicSize dsb) {
        super(null);
        this.am = am;
        this.dsb = dsb;
        this.engine = engine;
        grid = new Grid(this);
        hud = new HUDmanager(grid, this);
        viewLayer = new ViewLayer(new BoundingRectangle(0,0, dsb));
        ipl =  new InformationPopupLayer(new BoundingRectangle(5, 125, 200, 35));
        //ArmadaHUD armadaLayer = new ArmadaHUD(new BoundingRectangle(0, 0, 100, 100), this);
        viewLayer.addSublayer(ipl);
        //viewLayer.addSublayer(armadaLayer);
        viewLayer.addSublayer(hud);
	    viewLayer.addSublayer(grid);
	    BufferedImage img = ImageLoader.getInstance().getImage("GamePlayBackground2.jpg");
	    viewLayer.addSublayer(new BackgroundImageViewLayer(new BoundingRectangle(0, 0, dsb), viewRegion, img));
	    bgm= new BGMPlayer();
	    bgm.play();
	    
    }
    /**
    Get the current x coordinate of the mouse.
    */
    public int getCurrentX() {
        return grid.getCurrentX();
    }
    /**
    Get the current y coordinate of the mouse.
    */
    public int getCurrentY() {
        return grid.getCurrentY();
    }
    /**
    Returns the engine of the game.
    */
    public ArmadaEngine getEngine() {
        return engine;
    }
    /**
    Returns the selected element.
    */
    public DynamicElement getActiveE() {
        return grid.getActiveE();
    }
    /**
    Refreshes the grid at every call by the refresh timer.
    */
    public void refresh(long previousTime, long currentTime) {
        super.refresh(previousTime, currentTime);
        int turn = grid.getTurn();
        if (lastTurn != turn) {
            newTurn();
            grid.turnTimedOut();
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
    /**
    Ends the game and shows a winner screen.
    */
    public void endGame(){
    	am.endGame();
		//am.endGame(grid.getWinner().getAlliance());
    	JFrame end = new JFrame();
		/*JTextArea text = new JTextArea(500,500);
		text.setFont(text.getFont().deriveFont(28.0f));
		text.setText(grid.getWinner().getPlayerName() + " has won! \n Now " + grid.getLoser().getPlayerName() +" has to clean the ceiling!" );
		end.add(text);
		*/
		if(grid.getWinner().getAlliance()==1)
			end.add(new WinPanel("redwin.png"));
		if(grid.getWinner().getAlliance()==2)
			end.add(new WinPanel("bluewin.png"));
		end.setSize(198*5,108*5+20);
        end.setMinimumSize(new Dimension(198*5,108*5+20));
		end.setVisible(true);
		
	}
	
	/**
	A panel that shows who is the winner of the game.
	*/
	public class WinPanel extends JPanel{
		private BufferedImage backgroundImage;
		public WinPanel(String img){
			super();
			backgroundImage = ImageLoader.getInstance().getImage(img);
		}
		public void paintComponent(Graphics g){
			Graphics2D g2d = (Graphics2D)g;
	        AffineTransform at = g2d.getTransform();
	        if (backgroundImage != null) {
	            int imageWidth = backgroundImage.getWidth();
	            int panelWidth = this.getWidth();
	            
	            double scale = panelWidth / (imageWidth * 1.0);
	            g2d.scale(scale, scale);
	            g2d.drawImage(backgroundImage, 0, 0, null);
	            
	        } else {
	            System.out.println("Repainting");
	            //repaint();
	        }
	        g2d.setTransform(at);
		}
	}
	
	/**
	Handles key released events, updating state accordingly.
	*/
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
	
	/**
	Handles mouse move events, updating state accordingly.
	*/
	public void mouseMoved(MouseEvent evt) {
	    //System.out.println("Mouse Moved");
	    if (!moveMode) {
	        grid.mouseMoved(evt.getX(), evt.getY());
	    } else {
	        int xVel = 50;
	        int yVel = 50;
	        if (lastX < 0) {
	            lastX = evt.getX(); lastY = evt.getY();
	        } else {
	            if (mouseOffScreen != true) {
	                grid.moveViewRegion((evt.getX()-lastX)*xVel, (evt.getY()-lastY)*yVel);
	                //System.out.println("Moving view region");    
	            }
	            lastX = evt.getX(); lastY = evt.getY();
	        }
	    }
	}
	
	/**
	Handles mouse exit events, updating state accordingly.
	*/
	public void mouseExited(MouseEvent evt) {
	    //System.out.println("Mouse Exited");
	    mouseOffScreen = true;
	}
	
	/**
	Handles mouse entered events, updating state accordingly.
	*/
	public void mouseEntered(MouseEvent evt) {
	    //System.out.println("Mouse Entered");
	    mouseOffScreen = false;
	}

	/**
	Gets called to inform the controller that the game's mode was changed. Currently
	does nothing, but could be useful in the future.
	*/
	public void modeChanged() {
	    //ipl.showPopup("Mode: " + grid.getModeString(), new Color(0.0f, 0.5f, 0.0f));
	}
    
    /**
    Returns the size of the display.
    */
    public DynamicSize getViewSize() {
        return dsb;
    }
    
    /**
    Returns the width in pixels of the display.
    */
    public int getViewWidth() {
        return dsb.getWidth();
    }
    
    /**
    Returns the height in pixels of the display.
    */
    public int getViewHeight() {
        return dsb.getHeight();
    }
    /**
    Shows an error message.
    */
    public void userError(String msg) {
    	ipl.showPopup(msg, new Color(0.8f, 0.1f, 0.1f));
    
    }
    /**
    Tells the turn HUD to animate the change in turn.
    */
    public void newTurn() {
        hud.getTurnHUD().showTransition();
    }
    /**
    Shows an error message when the user tries to make an invalid move.
    */
    public void invalidMoveAttempt(String info) {
        ipl.showPopup(info, new Color(0.8f, 0.0f, 0.0f));
    }
    /*
        NOTE: use this sparingly, try to give the grid more info on what happened.
    */
    public void showInfo(String info) {
        ipl.showPopup(info, Color.BLUE);
    }
    
    /**
    Handles mouse events and forwards them to the view layer hierarchy.
    */
    public void mousePressed(MouseEvent evt) {
		if(evt.getButton() == MouseEvent.BUTTON3){
			moveMode=false;
			grid.unselect();
		}
		int x = (int)evt.getPoint().getX();
		int y = (int)evt.getPoint().getY();
		viewLayer.click(x, y);
	}
	
	/**
	Handles component resizing events.
	*/
	public void componentResized(ComponentEvent evt) {
	    viewRegion.setWidth(evt.getComponent().getWidth());
	    viewRegion.setHeight(evt.getComponent().getHeight());
	    forceValidViewRegion();
	}
	/**
	Ensures that the view region is valid. Useful to make sure that moving the view
	reason does not make it go past the edge.
	*/
	public void forceValidViewRegion() {
	    if (viewRegion.getX() < 0) viewRegion.setX(0);
        if (viewRegion.getY() < 0) viewRegion.setY(0);
	    if (viewRegion.getX() + getViewWidth() > GRID_WIDTH) {
            viewRegion.setX(GRID_WIDTH - getViewWidth());
        }
        if (viewRegion.getY() + getViewHeight() > GRID_HEIGHT) {
            viewRegion.setY(GRID_HEIGHT - getViewHeight());
        }
	}
    /**
    Updates the size of the view region to the size of the display component.
    */
    public void update() {
		viewRegion.setWidth(getViewWidth());
		viewRegion.setHeight(getViewHeight());
	}
	/**
	Moves the view region to the specified point if valid.
	*/
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
        forceValidViewRegion();
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
    /**
    Returns true if the mouse is on screen and false otherwise.
    */
    public boolean mouseIsOnScreen() {
        return !mouseOffScreen;
    }   
    /**
    Get the current view region (the part of the map that is displayed).
    */
    public BoundingRectangle getViewRegion() {
        return viewRegion;
    }
    
    /**
    Handles key press events.
    */
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
	            if (hud.getMap().getScaleType() == MapHUD.Scale.SMALL) {
//	                hud.showAll();
	            } else {
//	                hud.hideOther("MAP_HUD");
//	                hud.show("TURN_HUD");
	            }
	        break;
	        case KeyEvent.VK_P:
	            bgm.toggleStop();
	        break;
	        case KeyEvent.VK_S:
	            grid.moveCheat();
	        break;
	        case KeyEvent.VK_I:
//	            hud.toggleInventory();
	        break;
	    }
	}
	
	/**
	Handles mouse dragged events.
	*/
	public void mouseDragged(MouseEvent evt) {
		grid.setCurrentX(evt.getX());
		grid.setCurrentY(evt.getY());
		hud.drag(evt.getX(),evt.getY());
		/*
		if(hud.getMap().isIn(evt.getX(), evt.getY())){
			hud.getMap().moveMap(evt.getX(), evt.getY());
		}*/
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

    /**
    Get the last x coordinate of the mouse.
    */
	public int getLastX() {
		return lastX;
	}

    /**
    Set the last x coordinate of the mouse.
    */
	public void setLastX(int lastX) {
		this.lastX = lastX;
	}
    /**
    Get the last y coordinate of the mouse.
    */
	public int getLastY() {
		return lastY;
	}
    /**
    Set the last y coordinate of the mouse
    */
	public void setLastY(int lastY) {
		this.lastY = lastY;
	}
    /**
    Get the HUDmanager that displays game state and view information.
    */
	public HUDmanager getHud() {
		return hud;
	}
    /**
    Returns the grid.
    */
	public Grid getGrid() {
		return grid;
	}
}