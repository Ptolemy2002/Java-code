package cards;

public class Player {
	
	private Deck hand;
	private Double money;
	private Double bet;
	
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
	
	public void setBet(Double bet) {
		this.bet = bet;
	}
	
	public void pay(Double amount) {
		this.money += amount;
	}
	
	public void collect(Double amount) {
		this.money -= amount;
	}

}
