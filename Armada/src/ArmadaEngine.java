import java.util.*;
public class ArmadaEngine {

    int turn = 1;
    
    // Information for computing time remaining for current turn
    private static final double TURN_TIME = 50000.0;
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
		delements.add(new NormalShip(350,330,2));
		delements.add(new NormalShip(400,330,2));
		delements.add(new NormalShip(450,330,2));
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
				if (d instanceof Planet) {
				    Planet p = (Planet)d;
				    p.startOfTurn(this);
				}
		}
		
	    mseconds = TURN_TIME;
	}
    
    public void moveDynamicElement(DynamicElement activeE, int x, int y) {
        if(activeE.withinMovement(x,y) && activeE.canMovePath2(x,y, delements) && activeE instanceof Ship){
				Ship temp = (Ship) activeE;
				if(temp.isDocked())
					temp.setPlanetDocked(null);
        		activeE.moveTo(x, y);
		}
        else if(!(activeE instanceof Ship)){
        	//InformationPopupLayer.getInstance().showPopup("Only Ships Can Move");
        }
        else if(!activeE.withinMovement(x,y)){
        	//InformationPopupLayer.getInstance().showPopup("Out Of Movement Range");
        }
        else if(!activeE.canMovePath2(x,y, delements)){
        	//InformationPopupLayer.getInstance().showPopup("Object In The Way");
        }
        
    }
    
    public void attackHull(DynamicElement activeE, int x, int y) {
        for (DynamicElement d : delements) {
				if(d.isIn(x,y) && d.getAlliance()!=activeE.getAlliance() && activeE.withinRange(x,y) && d.isTargetable()){
						d.hullTakeDamage(activeE);
						activeE.setCanAttack(false);
				}
				else if(!activeE.withinRange(x,y)){
					//InformationPopupLayer.getInstance().showPopup("Out Of Range");
				}
		}
    }
    
    public void attackEngine(DynamicElement activeE, int x, int y) {
        for (DynamicElement d : delements) {
			if(d.isIn(x,y) && d.getAlliance()!=activeE.getAlliance() && activeE.withinRange(x,y) && d.isTargetable() && d.getEngine()>0){
			    d.engineTakeDamage(activeE);
			    activeE.setCanAttack(false);
			}
			else if(!activeE.withinRange(x,y)){
				//InformationPopupLayer.getInstance().showPopup("Out Of Range");
			}
		}
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
