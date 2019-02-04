package cards.blackjack;

import cards.CardGame;
import cards.Deck;
import main.Tools;
import cards.CardPlayer;

/**
 * The game will act as the dealer.
 */

public class BlackjackGame extends CardGame {

	public BlackjackGame(Deck deck) {
		this.setDeck(deck);
	}

	public void printDescription() {
		System.out.println("Welcome to Blackjack!");
		if (Tools.Console.askBoolean("Would you like to hear the rules?", true)) {
			System.out.println(
					"Each player is dealt two face up cards. The dealer is dealt one face up and one face down card.");
			System.out.println("Each player calculates their total.");
			if (Tools.Console.askBoolean("Would you like to hear the counting rules?", true)) {
				System.out.println("Each card is worth it's pip value.");
				System.out.println("A face is worth 10.");
				System.out.println("An ace is worth either 1 or 11 (you decide)");
			}
			
		}
	}

	@Override
	public void addNewPlayer() {
		this.addPlayer(new BlackjackPlayer(this, this.getPlayers().size()));
	}

	@Override
	public void start() {
		this.getDeck().shuffle();
	}

	@Override
	public void makeBets(Double min, Double max) {
		System.out.println("It's time for bet setup!");

		for (CardPlayer i : this.getPlayers()) {
			i.makeBet(min, max);
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

}
