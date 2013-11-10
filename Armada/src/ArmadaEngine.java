import java.util.*;
public class ArmadaEngine {

    int turn = 1;
    
    // Information for computing time remaining for current turn
    private static final double TURN_TIME = 5000.0;
    private double mseconds = TURN_TIME;
    private long lastTime = 0;
    
    private PlayerManager pm;
    
    private ArrayList<Element> elements;
    private ArrayList<DynamicElement> delements;
    
    public ArrayList<DynamicElement> getDynamicElements() {
        return delements;
    }
    
    public PlayerManager getPlayerManager(){
		return pm;
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
		Spawner.spawnPlanets(this, 40);
    }
    
    public void add(DynamicElement inDE){
		delements.add(inDE);
	}
    
    public void toggleTurn() {
		if(turn==1) turn=2;
		else turn=1;	
		startTurn();		
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
		}
	    mseconds = TURN_TIME;
	}
    
    public void moveDynamicElement(DynamicElement activeE, int x, int y, ArrayList<DynamicElement> delements) {
        if(activeE.withinMovement(x,y) && activeE.canMovePath2(x,y, delements) && activeE instanceof Ship){
				activeE.moveTo(x, y);
				Ship temp = (Ship) activeE;
				if(temp.isDocked())
					temp.setPlanetDocked(null);
		}
    }
    
    public void attackHull(DynamicElement activeE, int x, int y, ArrayList<DynamicElement> delements) {
        for (DynamicElement d : delements) {
				if(d.isIn(x,y) && d.getAlliance()!=activeE.getAlliance() && activeE.withinRange(x,y) && d.isTargetable()){
						d.hullTakeDamage(activeE);
						activeE.setCanAttack(false);
				}
		}
    }
    
    public void attackEngine(DynamicElement activeE, int x, int y, ArrayList<DynamicElement> delements) {
        for (DynamicElement d : delements) {
			if(d.isIn(x,y) && d.getAlliance()!=activeE.getAlliance() && activeE.withinRange(x,y) && d.isTargetable() && d.getEngine()>0){
			    d.engineTakeDamage(activeE);
			    activeE.setCanAttack(false);
			}
		}
    }
    
    public void dock(DynamicElement activeE, int x, int y, ArrayList<DynamicElement> delements) {
        for (DynamicElement d : delements) {
				if(d.isIn(x,y) && (d.getAlliance()==0 || d.getAlliance() == activeE.getAlliance()) && activeE.distanceFrom(x, y) < 100 && d.isTargetable() && d instanceof Planet && activeE instanceof Ship){
					System.out.println("docking attempted");
					Planet p = (Planet)d;
					Ship s = (Ship) activeE;
					p.dock(s);
				}
					
				if(d.isIn(x,y) && d.getAlliance()!= activeE.getAlliance() && activeE.distanceFrom(x, y) < 100 && d.isTargetable() && d instanceof Ship && activeE instanceof Ship){
					System.out.println("Boarding attempted");
					Ship s = (Ship) activeE;
					Ship t = (Ship) d;
					s.board(t);
				}
		}
	}
}
