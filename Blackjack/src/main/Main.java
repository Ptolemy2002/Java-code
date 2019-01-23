package main;

import java.util.ArrayList;

import blackjack.Card;
import blackjack.Deck;
import blackjack.EnumCardNumber;
import blackjack.EnumCardSuit;

public class Main {

	public static int players = 2;

	public static void testToString() {
		while (true) {
			if (Tools.Console.askBoolean("Would you like to cancel the toString test?", true))
				break;

			EnumCardNumber number = Tools.Console.askSelection("Card Numbers", EnumCardNumber.getValues(), true,
					"CANCEL");
			EnumCardSuit type = Tools.Console.askSelection("Card Types", EnumCardSuit.getValues(), true, "CANCEL");

			if (!(number == null || type == null)) {
				System.out.println(new Card(number, type).toString());
			}
		}
	}

	public static void play() {
		Deck deck = new Deck();
		deck.shuffle();

		ArrayList<Deck> playerDecks = new ArrayList<>();
		playerDecks.add(new Deck(new ArrayList<Card>())); // This will be the dealer's deck.
		for (int i = 0; i < players; i++) {
			playerDecks.add(new Deck(new ArrayList<Card>())
					.appendDeck(new Deck(deck.drawTop().setFaceUp(true), deck.drawTop().setFaceUp(true)))); // Deal
																											// players
																											// two
																											// face
																											// up
																											// cards
		}
		playerDecks.get(0).appendDeck(new Deck(deck.drawTop().setFaceUp(true), deck.drawTop())); // Deal
																									// dealer
																									// one
																									// face
																									// up
																									// card
																									// and
																									// one
																									// face
																									// down
																									// card.

	}

	public static void main(String[] args) {

	}

}
