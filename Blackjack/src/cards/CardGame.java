package cards;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public abstract class CardGame<T extends Player> {
	private ArrayList<T> players;
	private Deck deck;
	
	public CardGame<T> addPlayer(T player) {
		players.add(player);
		return this;
	}
	
	public CardGame<T> removePlayer(T player) {
		players.remove(player);
		return this;
	}
	
	public CardGame<T> removePlayer(int player) {
		players.remove(player - 1);
		return this;
	}
	
	public ArrayList<T> getPlayers() {
		return players;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public CardGame<T> setDeck(Deck deck) {
		this.deck = deck;
		return this;
	}
	
	public abstract void addNewPlayer();
	public abstract void start();
	public abstract void makeBets(Double min, Double max);
	public abstract void dealHands();
}
