package src; import src.view.*;
import javax.swing.JFrame;

/*
 * Responsable for Main. 
 * 
 */

public class Game {
    /** Enables/Disables debug statements */
    static final boolean DEBUG = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        ApplicationManager AM = ApplicationManager.getInstance();
        AM.start();
	}
	
}
