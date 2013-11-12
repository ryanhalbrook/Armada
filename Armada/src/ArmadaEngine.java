import java.util.ArrayList;
import java.util.GregorianCalendar;
public class ArmadaEngine implements ChangeListener {

    int turn = 1;
    
    // Information for computing time remaining for current turn
    private static final double TURN_TIME = 50000.0;
    private double mseconds = TURN_TIME;
    private long lastTime = 0;
    
    private GameServer gs;
    
    private PlayerManager pm;
    
    private ArrayList<Element> elements;
    private ArrayList<DynamicElement> delements;
    
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
        }
        
        
    }
    
    public enum MovementStatus {
        SUCCESS, OBJECT_IN_PATH, RANGE, CANNOT_MOVE_PLANET, UNKNOWN_FAILURE;
    }
    
    public enum AttackStatus {
        SUCCESS, OUT_OF_SHOTS, RANGE, BAD_TARGET, UNKNOWN_FAILURE;
    }
    
    private DynamicElement getDEAtPoint(int x, int y) {
        for (DynamicElement d : delements) {
			    if(d.getX() == x && d.getY() == y && d.isTargetable()) {
				    return d;
			    }
		}
		return null;
    }
    
    public ArrayList<DynamicElement> getDynamicElements() {
        return delements;
    }
    
    public PlayerManager getPlayerManager(){
		return pm;
	}
	
	public ArmadaEngine(GameServer server) {
	    System.out.println(this);
	    if (server == null) System.out.println("Server is null");
	    else System.out.println("Server is not null");
	    this.gs = server;
	    gs.setChangeListener(this);
	    if (this.gs == null) System.out.println("Server is null after being assigned");
	    pm = new PlayerManager();
        delements = new ArrayList<DynamicElement>();
        for (DynamicElement d : pm.getHomePlanets()) {
            delements.add(d);
        }
        
        // Add some ships
		delements.add(new NormalShip(750,330,1));
		delements.add(new NormalShip(160,330,1));
		delements.add(new NormalShip(260,330,2));
		delements.add(new NormalShip(60,330,2));
		delements.add(new NormalShip(220,330,2));
		delements.add(new NormalShip(300,330,2));
		delements.add(new NormalShip(350,330,2));
		delements.add(new NormalShip(400,330,2));
		delements.add(new NormalShip(450,330,2));
		Spawner.spawnPlanets(this, 40);
	}
    
    public ArmadaEngine() {
        pm = new PlayerManager();
        delements = new ArrayList<DynamicElement>();
        for (DynamicElement d : pm.getHomePlanets()) {
            delements.add(d);
        }
        
        // Add some ships
		delements.add(new NormalShip(750,330,1));
		delements.add(new NormalShip(160,330,1));
		delements.add(new NormalShip(260,330,2));
		delements.add(new NormalShip(60,330,2));
		delements.add(new NormalShip(220,330,2));
		delements.add(new NormalShip(300,330,2));
		delements.add(new JuggernautShip(350,430,1));
		delements.add(new JuggernautShip(350,630,1));
		delements.add(new JuggernautShip(500,330,2));
		delements.add(new JuggernautShip(650,330,2));
		JuggernautShip s = new JuggernautShip(700,700,1);
		s.addItem(new Item(ItemList.ItemNames.HullPlate));
		delements.add(s);
		Spawner.spawnPlanets(this, 40);
    }
    
    public void add(DynamicElement inDE){
		delements.add(inDE);
	}
	
	private void switchTurn() {
	    if(turn==1) turn=2;
		else turn=1;	
		startTurn();
	}
    
    public void toggleTurn() {
        System.out.println(this);
        GameStateChange gsc = new GameStateChange(null, "Turn Changed");
        if (this.gs != null) gs.commitChange(this, gsc);
        else System.out.println("Null server");
		switchTurn();		
	}
	
	public int getTurn() {
	    return turn;
	}
	
	public void enableDebugSpeed() {
	    for(DynamicElement d: delements){
			d.setSpeed(99999999);
		}
	}
	
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
    
    public double maxSecondsForTurn() {
        
        return TURN_TIME;
    }
	
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
        if(activeE.withinMovement(x,y) && activeE.canMovePath2(x,y, delements) && activeE instanceof Ship){
				Ship temp = (Ship) activeE;
				if(temp.isDocked())
					temp.setPlanetDocked(null);
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
    
    public void dock(DynamicElement activeE, int x, int y) {
        for (DynamicElement d : delements) {
				if(d.isIn(x,y) && (d.getAlliance()==0 || d.getAlliance() == activeE.getAlliance()) && activeE.distanceFrom(x, y) < 100 && d.isTargetable() && d instanceof Planet && activeE instanceof Ship){
					System.out.println("docking attempted");
					Planet p = (Planet)d;
					Ship s = (Ship) activeE;
					p.dock(s);
				}
				else if(d.isIn(x,y) && d instanceof Planet && d.getAlliance() != 0 && d.getAlliance() != activeE.getAlliance()){
					//InformationPopupLayer.getInstance().showPopup("Cannot Dock At Enemy Planets");
				}
				else if(activeE.distanceFrom(x, y) >= 100 ){
					//InformationPopupLayer.getInstance().showPopup("Out Of Range");
				}
					
				if(d.isIn(x,y) && d.getAlliance()!= activeE.getAlliance() && activeE.distanceFrom(x, y) < 100 && d.isTargetable() && d instanceof Ship && activeE instanceof Ship){
					System.out.println("Boarding attempted");
					Ship s = (Ship) activeE;
					Ship t = (Ship) d;
					s.board(t);
				}
				else if( d.isIn(x,y) && d.getAlliance() == activeE.getAlliance()){
					//InformationPopupLayer.getInstance().showPopup("Cannot Board Ally Ships");
				}
				else if(activeE.distanceFrom(x, y) >= 100 ){
					//InformationPopupLayer.getInstance().showPopup("Out Of Range");
				}
		}
	}
}
