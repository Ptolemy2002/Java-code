package cards.blackjack;

import cards.Card;
import cards.CardGame;
import cards.CardPlayer;
import cards.Deck;
import cards.EnumCardNumber;
import main.Tools;

/**
 * The game will act as the dealer.
 */

public class BlackjackGame extends CardGame {
	public Integer maxHits;

	public BlackjackGame(Deck deck) {
		this.setDeck(deck);
	}

	public static void printDescription() {
		System.out.println("");
		System.out.println(
				"Each player is dealt two face up cards. The dealer is dealt one face up and one face down card.");
		System.out.println("");
		System.out.println("Each player calculates their total.");
		if (Tools.Console.askBoolean("Would you like to hear the counting rules?", true)) {
			System.out.println("Each card is worth it's pip value.");
			System.out.println("A face is worth 10.");
			System.out.println("An ace is worth either 1 or 11 (you decide)");
		}
		System.out.println("");
		System.out.println("Each player tries to get as close as possible to 21.");
		System.out.println("When it is your turn, you can either stand, hit, or surrender.");
		System.out.println("Standing means that you don't draw anything.");
		System.out.println("Hitting means you draw a card.");
		System.out.println("If you surrender, you take back half your bet and stop playing.");
		System.out.println("You can hit as many times as you want (changable in properties).");
		System.out.println("If you go over 21, you lose!");
		System.out.println("This is called going 'bust'");
		System.out.println("You can change the value of your aces to avoid going bust.");

		System.out.println("The dealer goes after every player, and must hit until the total is over 17.");
		System.out.println("If the dealer goes bust, the user wins.");

		System.out.println("The dealer's play is automatic, so the computer handles his moves.");
		System.out.println("");

		System.out.println("If you are dealt a 21 (face and an ace) originally, you have a natural!");
		System.out.println("If you have a natural and the dealer does not, you get 1.5 times your bet!");
		System.out.println("If the dealer has a natural and you don't, you must pay the dealer your bet!");
		System.out.println("If both you and the dealer have naturals, you neither lose nor gain anything.");
		System.out.println("");

		System.out.println("Once the dealer goes over 17, the winner is determined.");
		System.out.println("If you are closer to 21 than the dealer, you win and take double your bet!");
		System.out.println("If you are farther from 21 than the dealer, you lose your bet.");
	}

	@Override
	public CardPlayer addNewPlayer(boolean ai) {
		CardPlayer player;
		if (ai) {
			player = new BlackjackPlayerAI(this, this.getPlayers().size());
		} else {
			player = new BlackjackPlayer(this, this.getPlayers().size());
		}

		this.addPlayer(player);
		return player;
	}

	@Override
	public void start() {
		System.out.println("It's time to play blackjack!");
		System.out.println("Shuffling deck...");
		this.getDeck().shuffle();
		System.out.println("The deck has been shuffled.");

		for (CardPlayer i : this.getPlayers()) {
			i.play();
		}
	}

	@Override
	public void makeBets(Double min, Double max) {
		System.out.println("It's time for bet setup!");

		for (CardPlayer i : this.getPlayers()) {
			i.makeBet(min, max);
			System.out.println("\"" + i.getName() + "\" is betting $" + i.getBet());
		}

		System.out.println("All bets have been made.");
	}

	public void makeBets(Double min, Double max, Double aiMin, Double aiMax) {
		System.out.println("It's time for bet setup!");

		for (CardPlayer i : this.getPlayers()) {
			if (i.isAI()) {
				i.makeBet(aiMin, aiMax);
			} else {
				i.makeBet(min, max);
			}
		}

		System.out.println("All bets have been made.");
	}

	@Override
	public void dealHands() {
		// Dealer gets one face up and one face down card.
		this.getDealerHand().putCardAtTop(this.getDeck().drawTop().setFaceUp(true))
				.putCardAtTop(this.getDeck().drawTop().setFaceUp(false));

		for (CardPlayer i : this.getPlayers()) {
			// Each player gets two face up cards
			i.deal(this.getDeck().drawTop().setFaceUp(true)).deal(this.getDeck().drawTop().setFaceUp(true));
		}
	}

	public BlackjackGame setMaxHits(Integer hits) {
		this.maxHits = hits;
		return this;
	}

	public int getMaxHits() {
		return maxHits;
	}

	public int getDealerValue() {
		int res = 0;
		for (Card i : this.dealerHand.getCards()) {
			if (EnumCardNumber.isFace(i.number) || i.number == EnumCardNumber.TEN) {
				res += 10;
			} else {
				res += i.number.ordinal() + 1;
			}
		}
		
		return res;
	}

	public int getVisibleValue() {
		int res = 0;
		for (Card i : this.dealerHand.getCards()) {
			if (i.faceUp) {
				if (EnumCardNumber.isFace(i.number) || i.number == EnumCardNumber.TEN) {
					res += 10;
				} else {
					res += i.number.ordinal() + 1;
				}
			}
		}
		
		return res;
	}

}
