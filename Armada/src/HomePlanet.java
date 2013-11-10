/**
    Represents a player's home planet. If one of these gets destroyed, the game ends.
*/
public class HomePlanet extends Planet {
	/**
	    The only constructor. Creates a home planet.
	    @param alliance Indicates the player that this planet belongs to.
	*/
	public HomePlanet(int alliance){
		super();
		maxHull=1000;
		hull=maxHull;
		if(alliance == 1){
			x=Grid.GRID_WIDTH/2;
			y=500;
			
		}
		if(alliance == 2){
			x=Grid.GRID_WIDTH/2;
			y=Grid.GRID_HEIGHT - 500;
			
		}
		setWidth(100);
		setHeight(100);
		this.alliance = alliance;
	}

}
