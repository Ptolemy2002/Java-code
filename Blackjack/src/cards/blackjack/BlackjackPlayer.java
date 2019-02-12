package cards.blackjack;

import cards.EnumCardNumber;

import java.util.ArrayList;

import cards.Card;
import cards.CardGame;
import cards.CardPlayer;
import main.Tools;

@SuppressWarnings("serial")
public class BlackjackPlayer extends CardPlayer {

	public BlackjackPlayer(BlackjackGame game, int id) {
		super(id);
		this.gameIn = game;
	}

	public boolean hasNatural() {
		return this.getHand().getCards().size() == 2
				&& (this.getHand().getBottom().isTenCard() && this.getHand().getTop().number == EnumCardNumber.ACE)
				|| (this.getHand().getTop().isTenCard() && this.getHand().getBottom().number == EnumCardNumber.ACE);
	}

	@Override
	public void play() {
		System.out.println("It's " + this.toString() + "'s turn!");
		int maxHits = ((BlackjackGame) gameIn).getMaxHits();
		int hits = 0;
		while (hits < maxHits) {
			if (Tools.Console.askBoolean("Would you like to view your stats?", true)) {
				System.out.println("Your hand is " + this.getHand().toString());
				System.out.println("You have hit " + hits + " times.");
				System.out.println(
						"You can hit up to " + (maxHits == Integer.MAX_VALUE ? "Infinity" : maxHits) + " times.");
			}

			String choice = Tools.Console.askSelection("Choices", new ArrayList<String>() {
				{
					add("hit");
					add("pass");
					add("surrender");
				}
			}, true, "Does " + this.toString() + " want to pass, hit, or surrender?", null, true, false, false);

			if (choice.equalsIgnoreCase("hit")) {
				this.deal(gameIn.getDeck().drawTop());
				System.out.println(this.toString() + " has hit!");
			} else if (choice.equalsIgnoreCase("pass")) {
				System.out.println(this.toString() + " has passed.");
				break;
			} else if (choice.equalsIgnoreCase("surrender")) {
				this.collect(this.getBet() * 0.5);
				gameIn.removePlayer(this);
				System.out.println(this.toString() + " has surrendered and took back half their bet ($"
						+ this.getBet() * 0.5 + ")");
			}
		}
		
		if (hits == maxHits) {
			System.out.println(this.toString() + " has run out of hits!");
		}
	}

	@Override
	public Double makeBet(Double min, Double max) {
		System.out.println(this.toString() + " has $" + this.getMoney() + "The minimum bet is $" + min
				+ ". The maximum bet is $" + (max > this.getMoney() ? this.getMoney() : max));
		this.setBet(
				Tools.Numbers.roundDouble(
						Tools.Console.askDouble("How much would " + this.toString() + " like to bet?", true,
								x -> x >= min && x <= (max > this.getMoney() ? this.getMoney() : max),
								this.toString() + " has $" + this.getMoney() + "The minimum bet is $" + min
										+ ". The maximum bet is $" + (max > this.getMoney() ? this.getMoney() : max)),
						2));
		return this.getBet();
	}

	public CardGame getGame() {
		return gameIn;
	}
	
	public int getValue() {
		int res = 0;
		for (Card i : this.hand.getCards()) {
			if (EnumCardNumber.isFace(i.number) || i.number == EnumCardNumber.TEN) {
				res += 10;
			} else {
				res += i.number.ordinal() + 1;
			}
		}
		
		return res;
	}
	
	public boolean hasSoftHand() {
		for (Card i : this.hand.getCards()) {
			if (i.number == EnumCardNumber.ACE) return true;
		}
		
		return false;
	}

}
