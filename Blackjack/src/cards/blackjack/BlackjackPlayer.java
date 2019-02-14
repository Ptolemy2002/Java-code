package cards.blackjack;

import cards.EnumCardNumber;

import java.util.ArrayList;

import cards.Card;
import cards.CardGame;
import cards.CardPlayer;
import main.Tools;

@SuppressWarnings("serial")
public class BlackjackPlayer extends CardPlayer {
	protected boolean valuableAce = false;
	protected boolean surrendered = false;

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
		if (surrendered) {
			System.out.println(this.toString() + " has surrendered, so they can't play.");
		} else {
			System.out.println("It's " + this.toString() + "'s turn!");
			int maxHits = ((BlackjackGame) gameIn).getMaxHits();
			int hits = 0;
			while (hits < maxHits) {
				if (Tools.Console.askBoolean("Would you like to view their stats?", true)) {
					System.out.println(this.toString() + "'s hand is " + this.getHand().toString());
					if (getValue() + 10 > 21) {
						valuableAce = false;
					}
					
					System.out.println(this.toString() + "'s current value is " + getValue());
					System.out.println(this.toString() + " has hit " + hits + " times.");
					System.out.println(
							this.toString() + " can hit up to " + (maxHits == Integer.MAX_VALUE ? "Infinity" : maxHits) + " times.");
				}
				
				if (!(getValue(true) + 10 > 21)) {
					System.out.println(this.toString() + "'s value will be " + (this.getValue(true) + 10) + " if they count their ace as 11.");
				} else {
					valuableAce = false;
					System.out.println("If " + this.toString() + " counts their ace as 11, they will go bust!");
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
					surrendered = true;
					System.out.println(this.toString() + " has surrendered and took back half their bet ($"
							+ this.getBet() * 0.5 + ")");
				}
			}
			
			if (hits == maxHits) {
				System.out.println(this.toString() + " has run out of hits!");
			}
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
		int aces = 0;
		for (Card i : this.hand.getCards()) {
			if (EnumCardNumber.isFace(i.number) || i.number == EnumCardNumber.TEN) {
				res += 10;
			} else if (i.number == EnumCardNumber.ACE) {
				aces++;
				//A player cannot count more than 1 ace as 11, or they will go bust.
				if (valuableAce && aces == 1) {
					res += 11;
				} else {
					res++;
				}
			}
			else {
				res += i.number.ordinal() + 1;
			}
		}
		
		return res;
	}
	
	public int getValue(boolean valuableAce) {
		int res = 0;
		int aces = 0;
		for (Card i : this.hand.getCards()) {
			if (EnumCardNumber.isFace(i.number) || i.number == EnumCardNumber.TEN) {
				res += 10;
			} else if (i.number == EnumCardNumber.ACE) {
				aces++;
				//A player cannot count more than 1 ace as 11, or they will go bust.
				if (valuableAce && aces == 1) {
					res += 11;
				} else {
					res++;
				}
			}
			else {
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
	
	public boolean isAceValuabe() {
		return valuableAce;
	}

}
