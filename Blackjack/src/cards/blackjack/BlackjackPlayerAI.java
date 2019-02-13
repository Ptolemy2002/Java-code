package cards.blackjack;


import main.Tools;

public class BlackjackPlayerAI extends BlackjackPlayer {

	public BlackjackPlayerAI(BlackjackGame game, int id) {
		super(game, id);
	}
	
	@Override
	public void play() {
		System.out.println("It's " + this.toString() + "'s turn!");
		int maxHits = ((BlackjackGame) gameIn).getMaxHits();
		int hits = 0;
		int dealerValue = ((BlackjackGame) gameIn).getVisibleValue();
		
		if (dealerValue >= 7) {
			while (hits < maxHits && this.getValue() < 17) {
				this.deal(gameIn.getDeck().drawTop());
				hits++;
				System.out.println(this.toString() + " has hit!");
			}
			
			if (hits == maxHits) {
				System.out.println(this.toString() + " has passed.");
			} else {
				System.out.println(this.toString() + " has run out of hits!");
			}
			
		} else if (dealerValue >= 4) {
			while (hits < maxHits && this.getValue() < 12) {
				this.deal(gameIn.getDeck().drawTop());
				hits++;
				System.out.println(this.toString() + " has hit!");
			}
			
			if (hits < maxHits) {
				System.out.println(this.toString() + " has passed.");
			} else {
				System.out.println(this.toString() + " has run out of hits!");
			}
		} else {
			while (hits < maxHits && this.getValue() < 13) {
				this.deal(gameIn.getDeck().drawTop());
				hits++;
				System.out.println(this.toString() + " has hit!");
			}
			
			if (hits < maxHits) {
				System.out.println(this.toString() + " has passed.");
			} else {
				System.out.println(this.toString() + " has run out of hits!");
			}
		}
	}
	
	@Override
	public Double makeBet(Double min, Double max) {
		return Tools.Numbers.roundDouble(Tools.Numbers.randomDouble(min, (max > this.getMoney() ? this.getMoney() : max)), 2);
	}

}
