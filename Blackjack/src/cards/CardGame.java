package cards;

import java.util.ArrayList;

public abstract class CardGame {
	private ArrayList<Player> players;
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public void removePlayer(int player) {
		players.remove(player - 1);
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public abstract void addNewPlayer();
	public abstract void start();
}
