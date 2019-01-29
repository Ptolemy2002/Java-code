package cards;

@SuppressWarnings("rawtypes")
public abstract class Player<T extends CardGame> {
	
	private Deck hand;
	private Double money;
	private Double bet;
	private int id;
	
	public Player(int id) {
		this.id = id;
	}
	
	public Player<T> deal(Card card) {
		this.hand.putCardAtBottom(card);
		return this;
	}
	
	public Deck getHand() {
		return this.hand;
	}
	
	public Double getBet() {
		return this.bet;
	}
	
	public Double getMoney() {
		return this.money;
	}
	
	public Player<T> setBet(Double bet) {
		this.bet = bet;
		return this;
	}
	
	public Player<T> addBet(Double bet) {
		this.bet += bet;
		return this;
	}
	
	public Player<T> removeBet(Double bet) {
		this.bet -= bet;
		return this;
	}
	
	public Player<T> pay(Double amount) {
		this.money += amount;
		return this;
	}
	
	public Player<T> collect(Double amount) {
		this.money -= amount;
		return this;
	}
	
	@Override
	public String toString() {
		return "Player " + id;
	}
	
	public abstract void play(T game);
	public abstract Double makeBet(Double min, Double max);

}
