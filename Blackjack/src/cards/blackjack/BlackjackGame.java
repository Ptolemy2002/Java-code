package cards.blackjack;

import cards.Card;
import cards.CardGame;
import cards.Deck;
import cards.CardPlayer;

/**
 * The game will act as the dealer.
 */

public class BlackjackGame extends CardGame {

	public BlackjackGame(Deck deck) {
		this.setDeck(deck);
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

	public BlackjackGame dealDealer(Card card) {
		this.getDealerHand().putCardAtTop(card);
		return this;
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
