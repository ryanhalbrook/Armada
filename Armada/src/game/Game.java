package game;

/*
 * Responsable for Main. 
 * 
 */

public class Game {
    /** Enables/Disables debug statements */
    private static final boolean DEBUG = false;

	/**
	    Starting point of the program. Tells the application manager to start.
	 * @param args
	 */
	public static void main(String[] args) {
        ApplicationManager AM = ApplicationManager.getInstance();
        AM.start();
	}
    /**
    Returns true if debugging is enabled.
    */
	public static boolean isDebug() {
		return DEBUG;
	}
	
}
