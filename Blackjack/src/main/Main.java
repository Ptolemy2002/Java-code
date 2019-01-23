package main;

import java.util.ArrayList;

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
																									// card

		return playerDecks;
	}
	
	public static ArrayList<Double> setupBets(int players, ArrayList<Double> money) {
		System.out.println("It's time for bet setup!");
		System.out.println("You have $" + money.get(1));
		System.out.println("The minimum bet is $" + MIN_BET + ". The maximum bet is $" + MAX_BET);

		Double bet = Tools.Console.askDouble("How much do you bet?", true);
		while (!(bet <= money.get(1) && bet >= MIN_BET && bet <= MAX_BET)) {
			if (bet > money.get(1)) {
				System.out.println("You do not have enough money to make that bet!");
			} else if (bet < MIN_BET) {
				System.out.println("That bet is too low!");
			} else if (bet > MAX_BET) {
				System.out.println("That bet is too high!");
			}

			System.out.println("You have $" + money.get(1));
			System.out.println("The minimum bet is $" + MIN_BET + ". The maximum bet is $" + MAX_BET);
			bet = Tools.Console.askDouble("How much do you bet?", true);
		}

		Double minBet = Tools.Console.askDouble("What should the minimum AI bet be?", true);
		while (!(minBet <= money.get(0) && minBet >= MIN_BET && minBet <= MAX_BET)) {
			if (minBet > money.get(0)) {
				System.out.println("Players do not have enough money to make that bet!");
			} else if (minBet < MIN_BET) {
				System.out.println("That bet is too low!");
			} else if (minBet > MAX_BET) {
				System.out.println("That bet is too high!");
			}

			System.out.println("The minimum bet is $" + MIN_BET + ". The maximum bet is $" + MAX_BET);
			minBet = Tools.Console.askDouble("What should the minimum AI bet be?", true);
		}

		Double maxBet = Tools.Console.askDouble("What should the maximum AI bet be?", true);
		while (!(maxBet <= money.get(0) && maxBet >= MIN_BET && maxBet >= minBet && maxBet <= MAX_BET)) {
			if (maxBet > money.get(0)) {
				System.out.println("Players do not have enough money to make that bet!");
			} else if (maxBet < MIN_BET || maxBet < minBet) {
				System.out.println("That bet is too low!");
			} else if (maxBet > MAX_BET) {
				System.out.println("That bet is too high!");
			}

			System.out.println("The minimum bet is $" + minBet + ". The maximum bet is $" + MAX_BET);
			maxBet = Tools.Console.askDouble("What should the maximum AI bet be?", true);
		}

		ArrayList<Double> bets = new ArrayList<>();
		bets.add(Tools.Numbers.randomDouble(minBet, maxBet));
		System.out.println("Dealer is betting $" + bets.get(0));
		bets.add(bet);
		System.out.println("You (Player 1) are betting $" + bet);
		for (int i = 1; i <= players; i++) {
			Double temp = Tools.Numbers.randomDouble(minBet, maxBet);
			bets.add(temp);
			System.out.println("Player " + (i + 1) + " is betting $" + temp);
		}
		
		System.out.println("All bets have been setup.");
		return bets;
	}
	
	public static void play(int players, ArrayList<Double> money) {
		Deck deck = new Deck();
		deck.shuffle();
		
		setupBets(players, money);

		System.out.println("Dealing players...");
		ArrayList<Deck> playerDecks = deal(deck, players);
		System.out.println("Players have been dealt.");

		if (Tools.Console.askBoolean("Would you like to view the hands?", true)) {
			System.out.println("Player 1 is dealer. You are player 2.");
			Tools.Console.printList("Hands", playerDecks);
		}
		
		System.out.println("It is your turn.");
		if (Tools.Console.askBoolean("Would you like to view your hand?", true)) {
			System.out.println(playerDecks.get(1));
		}

	}

	public static void main(String[] args) {
		int players = Tools.Console.askInt("How many players (not including dealer)?", true);
		Double startMoney = Tools.Console.askDouble("How much money should players start with?", true);

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
