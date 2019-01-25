package main;

import java.util.ArrayList;
import java.util.Collections;

import blackjack.Card;
import blackjack.Deck;
import blackjack.EnumCardNumber;
import blackjack.EnumCardSuit;

public class Main {

	public static final Double MIN_BET = 2.0;
	public static final Double MAX_BET = 500.0;

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

	public static void printHands(ArrayList<Deck> hands) {
		System.out.println("Dealer got the cards " + hands.get(0));
		System.out.println("You (Player 1) got the cards " + hands.get(1));

		for (int i = 2; i < hands.size(); i++) {
			System.out.println("Player " + i + " got the cards " + hands.get(i).toString());
		}
	}

	/**
	 * Deal a hand to each player.
	 * 
	 * @param deck    Deck to deal from
	 * @param players Players to play with (Not including Dealer)
	 * @return
	 */
	public static ArrayList<Deck> deal(Deck deck, int players) {
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
		playerDecks.get(0).appendDeck(new Deck(deck.drawTop().setFaceUp(true), deck.drawTop().setFaceUp(false))); // Deal
																													// dealer
																													// one
																													// face
																													// up
																													// and
																													// one
																													// face
																													// down

		return playerDecks;
	}

	public static ArrayList<Double> setupBets(int players, ArrayList<Double> money) {
		System.out.println("");
		System.out.println("It's time for bet setup!");
		System.out.println("You have $" + money.get(1));
		System.out.println("The minimum bet is $" + MIN_BET + ". The maximum bet is $"
				+ (Collections.max(money) > MAX_BET ? MAX_BET : Collections.max(money)));

		Double bet = Tools.Console.askDouble("How much do you bet?", true,
				x -> x <= Collections.max(money) && x >= MIN_BET && x <= MAX_BET,
				"The minimum bet is $" + MIN_BET + ". The maximum bet is $"
						+ (Collections.max(money) > MAX_BET ? MAX_BET : Collections.max(money)));

		Double minBet = Tools.Console.askDouble("What should the minimum AI bet be?", true,
				x -> x <= Collections.max(money) && x >= MIN_BET && x <= MAX_BET,
				"The minimum bet is $" + MIN_BET + ". The maximum bet is $"
						+ (Collections.max(money) > MAX_BET ? MAX_BET : Collections.max(money)));

		Double maxBet = Tools.Console.askDouble("What should the maximum AI bet be?", true,
				x -> x <= Collections.max(money) && x >= MIN_BET && x <= MAX_BET,
				"The minimum bet is $" + MIN_BET + ". The maximum bet is $"
						+ (Collections.max(money) > MAX_BET ? MAX_BET : Collections.max(money)));

		System.out.println("");
		ArrayList<Double> bets = new ArrayList<>();
		bets.add(Tools.Numbers.roundDouble(Tools.Numbers.randomDouble(minBet, maxBet), 2));
		System.out.println("Dealer is betting $" + bets.get(0));
		bets.add(bet);
		System.out.println("You (Player 1) are betting $" + bet);
		for (int i = 2; i <= players; i++) {
			Double temp = Tools.Numbers.roundDouble(Tools.Numbers.randomDouble(minBet, maxBet), 2);
			bets.add(temp);
			System.out.println("Player " + i + " is betting $" + temp);
		}

		System.out.println("All bets have been setup.");
		System.out.println("");
		return bets;
	}

	public static void play(int players, ArrayList<Double> money) {
		Deck deck = new Deck();
		deck.shuffle();

		ArrayList<Double> bets = setupBets(players, money);

		System.out.println("");
		System.out.println("Dealing players...");
		ArrayList<Deck> playerDecks = deal(deck, players);
		System.out.println("Players have been dealt.");
		System.out.println("");

		if (Tools.Console.askBoolean("Would you like to view the hands?", true)) {
			printHands(playerDecks);
		}

		ArrayList<Integer> naturals = new ArrayList<Integer>();
		playerDecks.forEach(x -> {
			if ((x.getTop().isTenCard() && x.getBottom().number == EnumCardNumber.ACE)
					|| (x.getBottom().isTenCard() && x.getTop().number == EnumCardNumber.ACE)) {
				switch (playerDecks.indexOf(x)) {
				case 0:
					System.out.println("The Dealer has a natural!");
					break;
				case 1:
					System.out.println("You have a natural!");
					break;
				default:
					System.out.println("Player " + playerDecks.indexOf(x) + " has a natural!");
				}

				naturals.add(playerDecks.indexOf(x));
			}
		});
		
		System.out.println(naturals);
		if (!naturals.isEmpty()) {
			if (naturals.contains(0)) {
				for (int i = 0; i < naturals.size(); i++) {
					if (!naturals.contains(i)) {
						if (i == 1) {
							System.out.println("You must pay your bet (" + Tools.Numbers.roundDouble((bets.get(naturals.get(i))), 2) + ") to the dealer!");
						} else {
							System.out.println("Player " + i + " must pay their bet (" + Tools.Numbers.roundDouble((bets.get(naturals.get(i))), 2) + ")  to the dealer!");
						}

						money.set(naturals.get(i), money.get(naturals.get(i)) - bets.get(naturals.get(i)));
						money.set(0, money.get(0) + bets.get(naturals.get(i)));
					}
				}
			} else {
				for (int i = 0; i < naturals.size(); i++) {
					if (i == 1) {
						System.out.println("The dealer must pay you 1.5 times his bet (" + Tools.Numbers.roundDouble((bets.get(0) * 1.5), 2) + ")!");
					} else {
						System.out.println("The dealer must pay Player " + naturals.get(i) + " 1.5 times his bet (" + Tools.Numbers.roundDouble((bets.get(0) * 1.5), 2) + ")!");
					}

					money.set(0, money.get(0) - bets.get(0) * 1.5);
					money.set(naturals.get(i), money.get(naturals.get(i)) + bets.get(0) * 1.5);
				}
			}
		}

		System.out.println("It is your turn.");
		if (Tools.Console.askBoolean("Would you like to view your hand?", true)) {
			System.out.println("You got the cards " + playerDecks.get(1));
		}

	}

	public static void main(String[] args) {
		int players = Tools.Console.askInt("How many players (not including dealer)?", true, x -> x >= 1,
				"The minimum value is 1.");
		Double startMoney = Tools.Console.askDouble("How much money should players start with?", true,
				x -> x >= MIN_BET, "The minimum value is $" + MIN_BET);

		ArrayList<Double> money = new ArrayList<>();
		for (int i = 0; i < players + 1; i++) {
			money.add(startMoney);
		}

		play(players, money);
		while (Tools.Console.askBoolean("Would you like to play again?", true)) {
			play(players, money);
		}
	}

}
