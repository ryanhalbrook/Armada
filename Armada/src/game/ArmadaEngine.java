package game;

import view.*;
import game.player.PlayerManager;
import game.server.GameServer;
import item.Item;
import item.ItemList;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import element.DynamicElement;
import element.Element;
import element.planet.Planet;
import element.ship.CargoShip;
import element.ship.FlagShip;
import element.ship.JuggernautShip;
import element.ship.NormalShip;
import element.ship.ScoutShip;
import element.ship.Ship;

/**
Class that handles most of the game logic.
*/
public class ArmadaEngine implements ChangeListener {

    int turn = 1;
    public static final int GRID_WIDTH = 7680; // The width of the grid in pixels.
    public static final int GRID_HEIGHT = 4320; // The height of the grid in pixels.
    int player = 0;
    // Information for computing time remaining for current turn
    private static final double TURN_TIME = 50000.0;
    private double mseconds = TURN_TIME;
    private long lastTime = 0;
    
    private GameServer gs;
    
    private PlayerManager pm;
    
    private ArrayList<Element> elements;
    private ArrayList<DynamicElement> delements;
    
    /**
    Callback for the networking to inform the engine that a change has occurred on another
    system.
    */
    public void changeOccurred() {
    
        //System.out.println("Change Occurred");
        /*
        ArrayList<GameStateChange> changes = gs.getChanges(this);
        
        if (changes == null) return;
        if (changes.size() > 0) gsc = changes.get(0);
        */
        GameStateChange gsc = null;
        if (gs.getSize() > 0) gsc = gs.getGameStateChange(0);
        
        if (gsc != null) {
            System.out.println("Engine got message: " + gsc.getMessage());
            if (gsc.getMessage().equals("Turn Changed")) switchTurn();
            if (gsc.getMessage().equals("MOVE")) {
                DynamicElement e = getDEAtPoint(gsc.x1, gsc.y1);
                moveDE(e, gsc.x2, gsc.y2);
            }
            if (gsc.getMessage().equals("NEW_PLANET")) {
                Planet p = new Planet(gsc.x3);
                p.setX(gsc.x1); p.setY(gsc.y1);
                p.setWidth(gsc.x2); p.setHeight(gsc.y2);
                addDE(p);
            }
        }
        
        
    }
    
    /**
    Describes the status of an attempted movement. This is either a success or the reason
    for failure.
    */
    public enum MovementStatus {
        SUCCESS, OBJECT_IN_PATH, RANGE, CANNOT_MOVE_PLANET, UNKNOWN_FAILURE, NOT_TURN;
    }
    
    /**
    Describes the status of an attempted attack of another element. This is either a 
    success or the reason for failure.
    */
    public enum AttackStatus {
        SUCCESS, OUT_OF_SHOTS, RANGE, BAD_TARGET, UNKNOWN_FAILURE;
    }
    
    /**
    Returns the dynamic element at the given point or null if there is none.
    */
    private DynamicElement getDEAtPoint(int x, int y) {
        for (DynamicElement d : delements) {
			    if(d.getX() == x && d.getY() == y && d.isTargetable()) {
				    return d;
			    }
		}
		return null;
    }
    
    /**
    Returns a reference to the list of DynamicElements that are part of the game's state.
    */
    public ArrayList<DynamicElement> getDynamicElements() {
        return delements;
    }
    
    /**
    Returns the player manager with the players of this game.
    */
    public PlayerManager getPlayerManager(){
		return pm;
	}
	
	/**
	Creates an instance of ArmadaEngine with a server and the player that this
	instance of the game.
	*/
	public ArmadaEngine(GameServer server, int player) {
	    System.out.println(this);
	    this.player = player;
	    this.gs = server;
	    gs.setChangeListener(this);
	    pm = new PlayerManager();
        delements = new ArrayList<DynamicElement>();
        for (DynamicElement d : pm.getHomePlanets()) {
            delements.add(d);
        }
        
        // Add some ships
	/*	delements.add(new NormalShip(750,330,1));
		delements.add(new NormalShip(160,330,1));
		delements.add(new NormalShip(260,330,2));
		delements.add(new NormalShip(60,330,2));
		delements.add(new NormalShip(220,330,2));
		delements.add(new NormalShip(300,330,2));
		delements.add(new NormalShip(350,330,2));
		delements.add(new NormalShip(400,330,2));
		delements.add(new NormalShip(450,330,2));
	*/
        delements.add(new FlagShip(200,1900,1));
        delements.add(new FlagShip(7400,1900,2));
    }
    
    /**
    Creates an instance of ArmadaEngine for a two player local game.
    */
    public ArmadaEngine() {
        pm = new PlayerManager();
        delements = new ArrayList<DynamicElement>();
        for (DynamicElement d : pm.getHomePlanets()) {
            delements.add(d);
        }
        
        // Add some ships
		/*delements.add(new NormalShip(750,330,1));
		delements.add(new NormalShip(160,330,1));
		delements.add(new NormalShip(450,330,2));
		delements.add(new ScoutShip(60,330,2));
		delements.add(new NormalShip(220,330,2));
		delements.add(new NormalShip(900,330,2));
		delements.add(new JuggernautShip(350,2000,1));
		delements.add(new JuggernautShip(1000,630,1));
		delements.add(new JuggernautShip(500,3000,2));
		delements.add(new JuggernautShip(650,700,2));
		delements.add(new CargoShip(650,900,2));
		JuggernautShip s = new JuggernautShip(4000,700,1);
		*/
        //s.addItem(new Item(ItemList.ItemNames.HullPlate));
		//delements.add(s);
        delements.add(new FlagShip(200,1900,1));
        delements.add(new FlagShip(7400,1900,2));
    }
    
    /**
    Returns true if it is the player for this instance's turn (used in networking)
    */
    private boolean isPlayersTurn() {
        return (turn == player);
    }
    
    /**
    Adds a planet to the state of the game.
    */
    public void addPlanet(int x, int y, int width, int height) {
        Planet p = new Planet();
        p.setX(x);
        p.setY(y);
        p.setWidth(width);
        p.setHeight(height);
    }
    
    /**
    Adds a DynamicElement to the state of the game.
    */
    public void add(DynamicElement inDE) {
        if (inDE instanceof Planet) {
            Planet planet = (Planet)inDE;
            GameStateChange change = new GameStateChange(null, "NEW_PLANET");
            change.x1 = planet.getX();
            change.y1 = planet.getY();
            change.x2 = planet.getWidth();
            change.y2 = planet.getHeight();
            change.x3 = planet.getImageNum();
            if (gs != null) gs.commitChange(this, change);
        }
		addDE(inDE);
	}
	
	private void addDE(DynamicElement inDE) {
	    delements.add(inDE);
	}
	
	private void switchTurn() {
	    if(turn==1) turn=2;
		else turn=1;	
		startTurn();
	}
    /**
    Changes the current turn.
    */
    public void toggleTurn() {
        System.out.println(this);
        GameStateChange gsc = new GameStateChange(null, "Turn Changed");
        if (this.gs != null) gs.commitChange(this, gsc);
		switchTurn();		
	}
	/**
	Get the current turn.
	*/
	public int getTurn() {
	    return turn;
	}
	/**
	Enable extreme speed used to debug the system and for quick demonstrations.
	*/
	public void enableDebugSpeed() {
	    for(DynamicElement d: delements){
			d.setSpeed(99999999);
		}
	}
	/**
	Get the seconds left in the current turn.
	*/
	public double secondsRemainingForTurn() {
	    if (lastTime == 0) {
		    lastTime = new GregorianCalendar().getTimeInMillis();
		} else {
		    long newTime = new GregorianCalendar().getTimeInMillis();
		    double delta = (double)(newTime - lastTime);
		    mseconds -= delta;
		    lastTime = newTime;
		}
		if (mseconds < 0.0) toggleTurn();
        return mseconds;
    }
    /**
    Returns the seconds that each turn starts with.
    */
    public double maxSecondsForTurn() {
        
        return TURN_TIME;
    }
	/**
	Initializes the state for the beginning of the turn.
	*/
	public void startTurn() {
	    for (DynamicElement d : delements) {
				d.startOfTurn();
				if (d instanceof Planet) {
				    Planet p = (Planet)d;
				    p.startOfTurn(this);
				}
		}
		
	    mseconds = TURN_TIME;
	}
    /**
    Request that a dynamic element be moved.
    */
    public MovementStatus moveDynamicElement(DynamicElement activeE, int x, int y) {
        if (gs != null) {
            GameStateChange gsc = new GameStateChange(null, "MOVE");
            gsc.x1 = activeE.getX();
            gsc.y1 = activeE.getY();
            gsc.x2 = x;
            gsc.y2 = y;
            gs.commitChange(this, gsc);
        }
        return moveDE(activeE, x, y);
    }
    
    private MovementStatus moveDE(DynamicElement activeE, int x, int y) {
        if (player != 0 && !isPlayersTurn()) return MovementStatus.NOT_TURN;
        if(activeE.withinMovement(x,y) && activeE.canMovePath2(x,y, delements) && activeE instanceof Ship){
				Ship temp = (Ship) activeE;
				/*if(temp.isDocked()){
					temp.setPlanetDocked(null);
				}
				*//*if(temp.isTrading()){
					temp.setTrading(false);
				}*/
        		activeE.moveTo(x, y);
        		return MovementStatus.SUCCESS;
		}
        else if(!(activeE instanceof Ship)){
        	//InformationPopupLayer.getInstance().showPopup("Only Ships Can Move");
        	return MovementStatus.CANNOT_MOVE_PLANET;
        }
        else if(!activeE.withinMovement(x,y)){
        	//InformationPopupLayer.getInstance().showPopup("Out Of Movement Range");
        	return MovementStatus.RANGE;
        }
        else if(!activeE.canMovePath2(x,y, delements)){
        	//InformationPopupLayer.getInstance().showPopup("Object In The Way");
        	return MovementStatus.OBJECT_IN_PATH;
        }
        return MovementStatus.UNKNOWN_FAILURE;
    }
    
    /**
    Request that the element at x,y have its hull attacked by activeE.
    */
    public AttackStatus attackHull(DynamicElement activeE, int x, int y) {
        for (DynamicElement d : delements) {
				if(d.isIn(x,y) && d.getAlliance()!=activeE.getAlliance() && activeE.withinRange(x,y) && d.isTargetable()){
						d.hullTakeDamage(activeE);
						activeE.setCanAttack(false);
						return AttackStatus.SUCCESS;
				}
				else if(!activeE.withinRange(x,y)){
				    return AttackStatus.RANGE;
					//InformationPopupLayer.getInstance().showPopup("Out Of Range");
				}
				
		}
		return AttackStatus.UNKNOWN_FAILURE;
    }
    
    /**
    Request that the element at x,y have its engine attacked by activeE.
    */
    public AttackStatus attackEngine(DynamicElement activeE, int x, int y) {
        for (DynamicElement d : delements) {
			if(d.isIn(x,y) && d.getAlliance()!=activeE.getAlliance() && activeE.withinRange(x,y) && d.isTargetable() && d.getEngine()>0){
			    d.engineTakeDamage(activeE);
			    activeE.setCanAttack(false);
			    return AttackStatus.SUCCESS;
			}
			else if(!activeE.withinRange(x,y)){
				//InformationPopupLayer.getInstance().showPopup("Out Of Range");
				return AttackStatus.RANGE;
			}
			
		}
		return AttackStatus.UNKNOWN_FAILURE;
    }
    
    /**
    Request that activeE dock at the planet at x,y or board the ship at x,y.
    */
    public void dock(DynamicElement activeE, int x, int y) {
        for (DynamicElement d : delements) {
				if(d.isIn(x,y) && (d.getAlliance()==0 || d.getAlliance() == activeE.getAlliance()) && activeE.distanceFrom(x, y) < Ship.getDockRange() && d.isTargetable() && d instanceof Planet && activeE instanceof Ship){
					System.out.println("docking attempted");
					Planet p = (Planet)d;
					Ship s = (Ship) activeE;
					p.dock(s);
					return;
				}
				else if(d.isIn(x,y) && d instanceof Planet && d.getAlliance() != 0 && d.getAlliance() != activeE.getAlliance()){
					//InformationPopupLayer.getInstance().showPopup("Cannot Dock At Enemy Planets");
				}
				else if(activeE.distanceFrom(x, y) >= Ship.getDockRange() ){
					//InformationPopupLayer.getInstance().showPopup("Out Of Range");
				}
					
				else if(d.isIn(x,y) && d.getAlliance()!= activeE.getAlliance() && activeE.distanceFrom(x, y) < Ship.getDockRange() && d.isTargetable() && d instanceof Ship && activeE instanceof Ship){
					System.out.println("Boarding attempted");
					Ship s = (Ship) activeE;
					Ship t = (Ship) d;
					s.board(t);
					return;
				}
				else if( d.isIn(x,y) && d.getAlliance() == activeE.getAlliance()){
					Ship s = (Ship) activeE;
					Ship t = (Ship) d;
					s.trade(t);
					return;
				}
				else if(activeE.distanceFrom(x, y) >= Ship.getDockRange() ){
					//InformationPopupLayer.getInstance().showPopup("Out Of Range");
				}
		}
	}
    /**
    Returns the height of the grid in pixels.
    */
	public static int getGridHeight() {
		return GRID_HEIGHT;
	}
    /**
    Returns the width of the grid in pixels.
    */
	public static int getGridWidth() {
		return GRID_WIDTH;
	}
    
}
