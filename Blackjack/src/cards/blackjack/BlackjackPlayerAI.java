package cards.blackjack;


import main.Tools;

public class BlackjackPlayerAI extends BlackjackPlayer {
	private int target = 0;
	
	public BlackjackPlayerAI(BlackjackGame game, int id)  {
		super(game, id);
		this.valuableAce = false;
	}
	
	public void drawUntilTarget(int maxHits) {
		this.valuableAce = this.getValue() + 10 >= target;
		if (this.valuableAce) {
			System.out.println(this.toString() + " has decided to count their ace as 11!");
		}
		
		int hits = 0;
		while (hits < maxHits && this.getValue() < 12) {
			this.deal(gameIn.getDeck().drawTop());
			hits++;
			System.out.println(this.toString() + " has hit!");
		}
	}
	
	@Override
	public void play() {
		
		System.out.println("It's " + this.toString() + "'s turn!");
		int maxHits = ((BlackjackGame) gameIn).getMaxHits();
		int hits = 0;
		int dealerValue = ((BlackjackGame) gameIn).getVisibleDealerValue();
		
		if (dealerValue >= 7) {
			target = 17;
		} else if (dealerValue >= 4) {
			target = 12;
		} else {
			target = 13;
		}
		
		drawUntilTarget(maxHits);
		if (hits < maxHits) {
			System.out.println(this.toString() + " has passed.");
		} else {
			System.out.println(this.toString() + " has run out of hits!");
		}
	}
	
	@Override
	public Double makeBet(Double min, Double max) {
		return Tools.Numbers.roundDouble(Tools.Numbers.randomDouble(min, (max > this.getMoney() ? this.getMoney() : max)), 2);
	}

}
