package cards;

public abstract class CardPlayer {
	
	protected CardGame gameIn;
	protected Deck hand;
	protected Double money;
	protected Double bet;
	protected int id;
	
	public CardPlayer(int id) {
		this.id = id;
	}
	
	public CardPlayer deal(Card card) {
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
	
	public CardPlayer setBet(Double bet) {
		this.bet = bet;
		return this;
	}
	
	public CardPlayer addBet(Double bet) {
		this.bet += bet;
		return this;
	}
	
	public CardPlayer removeBet(Double bet) {
		this.bet -= bet;
		return this;
	}
	
	public CardPlayer pay(Double amount) {
		this.money += amount;
		return this;
	}
	
	public CardPlayer collect(Double amount) {
		this.money -= amount;
		return this;
	}
	
	@Override
	public String toString() {
		return "CardPlayer " + id;
	}
	
	public abstract void play();
	public abstract Double makeBet(Double min, Double max);

}
