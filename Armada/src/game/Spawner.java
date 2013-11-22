package game;
 
import element.DynamicElement;
import element.planet.Planet;
import view.*;
public class Spawner {
	
	static final int MIN_DISTANCE = 900;

public static void spawnPlanets(ArmadaEngine engine, int numPlanets){
		long time = System.currentTimeMillis();
		for(int i = 0; i < numPlanets; i++){
			Planet temp = new Planet();
			Planet temp2 = new Planet();
			if(engine.getDynamicElements() != null && engine.getDynamicElements().size()>0 ){
				for(int h = 0;  h<engine.getDynamicElements().size(); h++){
					while(!Spawner.awayFromEdge(engine, temp) || temp.distanceFrom(engine.getDynamicElements().get(h)) < MIN_DISTANCE){
						if(System.currentTimeMillis() - time > 1000){
							System.out.println("PLANET SPAWNER TIMEOUT ---- DONT TRY SPAWNING SO MANY PLANETS");
							return;
						}
						temp=new Planet();
						h=0;   
					}
				}
				
			}
			temp2.setWidth(temp.getWidth());
			temp2.setHeight(temp.getHeight());
			temp2.setY(Grid.getGridHeight() - temp.getY());
			temp2.setX(Grid.getGridWidth() - temp.getX());
			engine.add(temp);
			engine.add(temp2);
		}
	}
	
	public static boolean awayFromEdge(ArmadaEngine engine, DynamicElement de){
		if(de.getX() > 200 && Math.abs(de.getY()- Grid.getGridHeight()) > 200 && de.getY()> 200 && Math.abs(Grid.getGridWidth() - de.getX()) > 200){
			return true;
		}
		else return false;
	}
	/*
	public static void spawnPlanets(Grid grid, int numPlanets){
		long time = System.currentTimeMillis();
		for(int i = 0; i < numPlanets; i++){
			Planet temp = new Planet();
			Planet temp2 = new Planet();
			if(engine.getDynamicElements() != null && engine.getDynamicElements().size()>0 ){
				for(int h = 0;  h<engine.getDynamicElements().size(); h++){
					while(!Spawner.awayFromEdge(grid, temp) || temp.distanceFrom(engine.getDynamicElements().get(h)) < 200){
						if(System.currentTimeMillis() - time > (50*Grid.GRID_HEIGHT/3000)){
							System.out.println("PLANET SPAWNER TIMEOUT ---- DONT TRY SPAWNING SO MANY PLANETS");
							return;
						}
						temp=new Planet();
						h=0;
					}
				}
				
			}
			temp2.setWidth(temp.getWidth());
			temp2.setHeight(temp.getHeight());
			temp2.setY(Grid.GRID_HEIGHT - temp.getY());
			temp2.setX(Grid.GRID_WIDTH - temp.getX());
			grid.add(temp);
			grid.add(temp2);
		}
	}
	
	public static boolean awayFromEdge(Grid grid, DynamicElement de){
		if(de.getX() > 200 && Math.abs(de.getY()- Grid.GRID_HEIGHT) > 200 && de.getY()> 200 && Math.abs(Grid.GRID_WIDTH - de.getX()) > 200){
			return true;
		}
		else return false;
	}
	
	*/
	
}
