package game.player; 

import java.util.ArrayList;

import element.DynamicElement;


public class PlayerManager {
	
	private int numPlayers;
	private ArrayList<Player> players;
	private ArrayList<DynamicElement> homePlanets = new ArrayList<DynamicElement>();

	public PlayerManager(){
		players=new ArrayList<Player>();
		Player p1 = new Player(1);
		p1.setPlayerName("Player 1");
		players.add(p1);
		homePlanets.add(p1.getHome());
		Player p2 = new Player(2);
		p2.setPlayerName("Player 2");
		players.add(p2);
		homePlanets.add(p2.getHome());
	}
	
	public ArrayList<DynamicElement> getHomePlanets() {
	    return homePlanets;
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
	
	public Player getWinner(){
		for(Player p: players){
			if(p.getHome().isDead()){
				p.setDead(true);
			}
		}
		for(Player p: players){
			if(p.isDead()){
				for(Player pp: players){
					if(!pp.isDead()){
						return pp;
					}
				}
			}
		}
		return null;
	}
	
	public Player getLoser(){
		for(Player p: players){
			if(p.getHome().isDead()){
				p.setDead(true);
				return p;
			}
		}
		return null;
	}

}
