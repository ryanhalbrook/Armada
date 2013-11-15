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
			x=500;
			y=Grid.GRID_HEIGHT/2;
			setImage("homeplanet_01");
			ah.changeImage(getImage());
			
		}
		if(alliance == 2){
			x=Grid.GRID_WIDTH-500;
			y=Grid.GRID_HEIGHT/2;
			setImage("homeplanet_02");
			ah.changeImage(getImage());
			
		}
		setWidth(300);
		setHeight(300);
		this.alliance = alliance;
	}

}
