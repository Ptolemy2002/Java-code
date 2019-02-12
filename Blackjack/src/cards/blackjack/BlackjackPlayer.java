package cards.blackjack;

import cards.EnumCardNumber;

import java.util.ArrayList;

import cards.CardGame;
import cards.CardPlayer;
import main.Tools;

@SuppressWarnings("serial")
public class BlackjackPlayer extends CardPlayer {

	public BlackjackPlayer(BlackjackGame game, int id) {
		super(id);
		this.gameIn = game;
	}

	public BlackjackPlayer(BlackjackGame game, int id, int maxHits) {
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
		while (hits <= maxHits) {
			if (Tools.Console.askBoolean("Would you like to view your stats?", true)) {
				System.out.println("Your hand is " + this.getHand().toString());
				System.out.println("You have hit " + hits + " times.");
				System.out.println("You can hit up to " + (maxHits == Integer.MAX_VALUE ? "Infinity" : maxHits) + " times.");
			}
			
			String choice = Tools.Console.askSelection("Choices", new ArrayList<String>() {
				{
					add("hit");
					add("pass");
					add("surrender");
				}
			}, true, "Do you want to pass, hit, or surrender?", null, true, false, false);
			
			if (choice.equalsIgnoreCase("hit")) {
				this.deal(gameIn.getDeck().drawTop());
				System.out.println(this.toString() + " has hit!");
			} else if (choice.equalsIgnoreCase("pass")) {
				break;
			} else if (choice.equalsIgnoreCase("surrender")) {
				this.collect(this.getBet() * 0.5);
				gameIn.removePlayer(this);
				System.out.println(this.toString() + " has surrendered and took back half their bet ($" + this.getBet() * 0.5 + ")");
			}
		}
	}

	@Override
	public Double makeBet(Double min, Double max) {
		System.out.println(this.toString() + " has $" + this.getMoney() + "The minimum bet is $" + min
				+ ". The maximum bet is $" + (max > this.getMoney() ? this.getMoney() : max));
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
