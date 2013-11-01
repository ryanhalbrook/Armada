
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.*;
import java.io.*;

import javax.imageio.ImageIO;

import java.awt.image.*;

public class ArmadaEngine {
    private ArrayList<Element> elements;
    private ArrayList<DynamicElement> delements;
    private  int mode = 0, turn = 1, index =0;
    private int currentPlayer = 0;
    private ArrayList<Player> players;
    
    static final int GRID_WIDTH = 3840;
    static final int GRID_HEIGHT = 2160;
    
    public ArmadaEngine() {
    	delements = new ArrayList<DynamicElement>();
    	
    	// Add some ships
		addShip(new NormalShip(750,330,1));
		addShip(new NormalShip(160,330,1));
		addShip(new NormalShip(260,330,2));
		addShip(new NormalShip(60,330,2));
		addShip(new NormalShip(220,330,2));
		addPlanet(new Planet());
		addPlanet(new Planet());
    }
    
    public ArrayList<DynamicElement> getDynamicElements() {
        return delements;
    }
    
    public void attackHull(DynamicElement attacker, int x, int y) {
        
    }
    
    public boolean legalHullAttack(DynamicElement attacker, int x, int y) {
        return false;
    }
    
    public void attackEngine(DynamicElement attacker, int x, int y) {
    }
    
    public boolean legalEngineAttack(DynamicElement attacker, int x, int y) {
        return false; // STUB
    }
    
    public boolean legalDocking(DynamicElement docker, int x, int y) {
        return false; // STUB
    }
    
    public void dock(DynamicElement docker, int x, int y) {
    
    }
    
    public boolean legalMove(DynamicElement activeE, int x, int y) {
        return activeE.withinMovement(x,y) && activeE.canMovePath2(x,y, delements) && activeE instanceof Ship;
    }
    
    public void move(DynamicElement activeE, int x, int y) {
        if (legalMove(activeE, x, y)) {
			activeE.moveTo(x, y);
			Ship temp = (Ship) activeE;
			temp.setPlanetDocked(null);
		}
    }
    
    public boolean inRange(DynamicElement e1, DynamicElement e2){
		return e1.getRange() > distance(e1,e2);
	}
    
    
    private void addShip(Ship s) {
        if (s!= null)
            delements.add(s);
    }
    
    private void addPlanet(Planet p) {
        if (p != null) {
            delements.add(p);
        }
    }
    
    public void startTurn(){
	    for (DynamicElement d : delements) {
			//d.startOfTurn();
		}
	}
    
    	/*
	 * Calculates distance between the two inputs, order does not matter
	 */
	public int distance(DynamicElement e1, DynamicElement e2){
		return (int)Math.sqrt(Math.pow(Math.abs((double)e1.getY()-(double)e2.getY()),2) + Math.pow(Math.abs((double)e1.getX()-(double)e2.getX()),2));
	}
	
	public int distance(DynamicElement e1, int inX, int inY){
		return (int)Math.sqrt(Math.pow(Math.abs((double)e1.getY()-(double)inY),2) + Math.pow(Math.abs((double)e1.getX()-(double)inX),2));
	}
}