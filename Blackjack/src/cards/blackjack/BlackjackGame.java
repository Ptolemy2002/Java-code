package cards.blackjack;

import cards.Card;
import cards.CardGame;
import cards.Deck;
import cards.Player;

/**
 * The game will act as the dealer.
 */
@SuppressWarnings("rawtypes")
public class BlackjackGame extends CardGame<BlackjackPlayer> {

	private Deck dealerHand;

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

	public Deck getDealerHand() {
		return dealerHand;
	}

	public BlackjackGame setDealerHand(Deck hand) {
		this.dealerHand = hand;
		return this;
	}

	@Override
	public void makeBets(Double min, Double max) {
		for (Player i : this.getPlayers()) {
			i.makeBet(min, max);
		}

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

		for (Player i : this.getPlayers()) {
			// Each player gets two face up cards
			i.deal(this.getDeck().drawTop().setFaceUp(true)).deal(this.getDeck().drawTop().setFaceUp(true));
		}
	}

}
