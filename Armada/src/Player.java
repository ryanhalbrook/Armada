/*
 * should keep track of money.  Since the Grid keeps track of Ships and Planets and each of those keeps track of their alliance, it is unknown if the Player will do anything else
 */
public class Player {
    int money;
    int alliance;
    String playerName;
    
    public Player(int alliance){
    	this.alliance=alliance;
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
}
