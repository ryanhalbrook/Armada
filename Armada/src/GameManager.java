import java.util.*;
/*
 * will likely hold onto information such as who's turn it is
 
 * Responsible for holding game state
 */
public class GameManager {
    private int turn;
    //private Players[];
    private ArrayList<Element> elements;
    
    public GameManager() {
        elements = new ArrayList<Element>();
        Ship s = new Ship(1);
        s.setX(100);
        s.setY(100);
        s.setWidth(100);
        s.setHeight(100);
        elements.add(s);
    }
    
    public ArrayList<Element> getElements() {
        return elements;
    }
    
    public void startTurn() {
        
    }
    
}
