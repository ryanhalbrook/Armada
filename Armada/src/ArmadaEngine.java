import java.util.*;
public class ArmadaEngine {

    int turn = 1;
    
    // Information for computing time remaining for current turn
    private static final double TURN_TIME = 5000.0;
    private double mseconds = TURN_TIME;
    private long lastTime = 0;
    
    public ArmadaEngine() {
        
    }
    
    public void toggleTurn() {
		if(turn==1) turn=2;
		else turn=1;	
		startTurn();		
	}
	
	public int getTurn() {
	    return turn;
	}
	
	public double secondsRemainingForTurn() {
        return mseconds;
    }
    
    public double maxSecondsForTurn() {
        return TURN_TIME;
    }
	
	public void refresh() {
	    if (lastTime == 0) {
		    lastTime = new GregorianCalendar().getTimeInMillis();
		} else {
		    long newTime = new GregorianCalendar().getTimeInMillis();
		    double delta = (double)(newTime - lastTime);
		    mseconds -= delta;
		    lastTime = newTime;
		}
		if (mseconds < 0.0) toggleTurn();
	}
	
	public void startTurn() {
	    mseconds = TURN_TIME;
	}
    
    public void moveDynamicElement(DynamicElement activeE, int x, int y, ArrayList<DynamicElement> delements) {
        if(activeE.withinMovement(x,y) && activeE.canMovePath2(x,y, delements) && activeE instanceof Ship){
				activeE.moveTo(x, y);
				Ship temp = (Ship) activeE;
				temp.setPlanetDocked(null);
		}
    }
    
    public void attackHull(DynamicElement activeE, int x, int y, ArrayList<DynamicElement> delements) {
        for (DynamicElement d : delements) {
				if(d.isIn(x,y) && d.getAlliance()!=activeE.getAlliance() && activeE.withinRange(x,y) && d.isTargetable()){
						d.hullTakeDamage(activeE);
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
