package cards.blackjack;

import cards.EnumCardNumber;
import cards.CardGame;
import cards.CardPlayer;
import main.Tools;

public class BlackjackPlayer extends CardPlayer {

	public BlackjackPlayer(BlackjackGame game, int id) {
		super(id);
		this.gameIn = game;
	}

	public boolean hasNatural() {
		return (this.getHand().getBottom().isTenCard() && this.getHand().getTop().number == EnumCardNumber.ACE)
				|| (this.getHand().getTop().isTenCard() && this.getHand().getBottom().number == EnumCardNumber.ACE);
	}
	
	@Override
	public void play() {
		
	}

	@Override
	public Double makeBet(Double min, Double max) {
		System.out.println(this.toString() + " has $" + this.getMoney() + "The minimum bet is $" + min + ". The maximum bet is $"
				+ (max > this.getMoney() ? this.getMoney() : max));
		this.setBet(Tools.Console.askDouble("How much would" + this.toString() + " like to bet?", true,
				x -> x >= min && x <= (max > this.getMoney() ? this.getMoney() : max),
				this.toString() + " has $" + this.getMoney() + "The minimum bet is $" + min + ". The maximum bet is $"
						+ (max > this.getMoney() ? this.getMoney() : max)));
		return this.getBet();
	}

	public CardGame getGame() {
		return gameIn;
	}

}
