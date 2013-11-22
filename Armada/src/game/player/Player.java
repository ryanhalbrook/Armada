package game.player; 
import element.planet.HomePlanet;

/*
 * should keep track of money.  Since the Grid keeps track of Ships and Planets and each of those keeps track of their alliance, it is unknown if the Player will do anything else
 */
public class Player {
	private int money;
    private int alliance;
    private boolean dead;
    private String playerName;
    private HomePlanet home;
    
    public Player(int alliance){
    	this.alliance=alliance;
    	home = new HomePlanet(alliance);
    	dead = false;
    	money=5000;
    	playerName="";
    }

    public void incMoney(int i){
    	money+=i;
    }
    
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getAlliance() {
		return alliance;
	}

	public void setAlliance(int alliance) {
		this.alliance = alliance;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public HomePlanet getHome() {
		return home;
	}

	public void setHome(HomePlanet home) {
		this.home = home;
	}
}
