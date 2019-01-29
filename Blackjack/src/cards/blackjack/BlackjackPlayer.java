package cards.blackjack;

import cards.EnumCardNumber;
import cards.Player;
import main.Tools;

public class BlackjackPlayer extends Player<BlackjackGame> {

	private BlackjackGame gameIn;

	public BlackjackPlayer(BlackjackGame game, int id) {
		super(id);
		this.gameIn = game;
	}

	public boolean hasNatural() {
		return (this.getHand().getBottom().isTenCard() && this.getHand().getTop().number == EnumCardNumber.ACE)
				|| (this.getHand().getTop().isTenCard() && this.getHand().getBottom().number == EnumCardNumber.ACE);
	}

	@Override
	public void play(BlackjackGame game) {
		
	}

	@Override
	public Double makeBet(Double min, Double max) {
		this.setBet(Tools.Console.askDouble("How much would you like to bet?", true,
				x -> x >= min && x <= (max > this.getMoney() ? this.getMoney() : max), "The minimum bet is $" + min
						+ ". The maximum bet is $" + (max > this.getMoney() ? this.getMoney() : max)));
		return this.getBet();
	}

	public BlackjackGame getGame() {
		return gameIn;
	}

}
