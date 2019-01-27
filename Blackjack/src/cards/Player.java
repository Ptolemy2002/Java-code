package cards;

public abstract class Player {
	
	private Deck hand;
	private Double money;
	private Double bet;
	private int id;
	
	public Player(int id) {
		this.id = id;
	}
	
	public Player deal(Card card) {
		this.hand.putCardAtBottom(card);
		return this;
	}
	
	public Deck getHand() {
		return this.hand;
	}
	
	public Double getBet() {
		return this.bet;
	}
	
	public Player setBet(Double bet) {
		this.bet = bet;
		return this;
	}
	
	public Player addBet(Double bet) {
		this.bet += bet;
		return this;
	}
	
	public Player removeBet(Double bet) {
		this.bet -= bet;
		return this;
	}
	
	public Player pay(Double amount) {
		this.money += amount;
		return this;
	}
	
	public Player collect(Double amount) {
		this.money -= amount;
		return this;
	}
	
	@Override
	public String toString() {
		return "Player " + id;
	}
	
	public abstract void play(CardGame game);

}
