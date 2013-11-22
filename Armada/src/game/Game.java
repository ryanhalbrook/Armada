package game;

/*
 * Responsable for Main. 
 * 
 */

public class Game {
    /** Enables/Disables debug statements */
    private static final boolean DEBUG = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        ApplicationManager AM = ApplicationManager.getInstance();
        AM.start();
	}

	public static boolean isDebug() {
		return DEBUG;
	}
	
}
