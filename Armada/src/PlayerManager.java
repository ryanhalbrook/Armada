import java.util.ArrayList;


public class PlayerManager {
	
	private int numPlayers;
	private ArrayList<Player> players;
	
	public PlayerManager(){
		players=new ArrayList<Player>();
		players.add(new Player(1));
		players.add(new Player(2));
	}
	
	public void payPlayerMoney(int alliance, int money){
		for(Player p: players){
			if(p.getAlliance() == alliance){
				p.incMoney(money);
			}
		}
	}
	
	public boolean canPay(int alliance, int pay){
		for(Player p: players){
			if(p.getAlliance() == alliance){
				if(p.getMoney() >= pay){
					return true;
				}
				else{
					return false;	
				}
			}
		}
		return false;
	}
	
	public int getPlayerMoney(int alliance){
		for(Player p: players){
			if(p.getAlliance() == alliance){
				return p.getMoney();
			}
		}
		return -1;
	}
	
	public void playerPays(int alliance, int pay){
		for(Player p: players){
			if(p.getAlliance() == alliance){
				p.incMoney(-pay);
			}
		}
	}

}
