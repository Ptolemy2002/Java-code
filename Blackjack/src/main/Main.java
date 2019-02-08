package main;

import java.util.ArrayList;

import cards.Card;
import cards.Deck;
import cards.EnumCardNumber;
import cards.EnumCardSuit;
import cards.blackjack.BlackjackGame;

public class Main {

	public static Double minBet = 2.0;
	public static Double maxBet = 500.0;
	public static Double minAIBet = 2.0;
	public static Double maxAIBet = 500.0;
	public static Integer maxHits = Integer.MAX_VALUE;

	public static BlackjackGame game;

	public static void testToString() {
		while (true) {
			if (Tools.Console.askBoolean("Would you like to cancel the toString test?", true))
				break;

			EnumCardNumber number = Tools.Console.askSelection("Card Numbers", EnumCardNumber.getValues(), true,
					"CANCEL", true);
			EnumCardSuit type = Tools.Console.askSelection("Card Types", EnumCardSuit.getValues(), true, "CANCEL",
					true);

			if (!(number == null || type == null)) {
				System.out.println(new Card(number, type).toString());
			}
		}
	}

	public static void properties() {
		@SuppressWarnings("serial")
		ArrayList<String> properties = new ArrayList<String>() {
			{
				add("minimum bet");
				add("maximum bet");
				add("minimum AI bet");
				add("maximum AI bet");
				add("maximum hits");
			}
		};

		switch (Tools.Console.askSelection("Properties", properties, true, "Pick a property to edit", "CANCEL", true)) {
		case "minumum bet":
			System.out.println("Description: the minimum bet a player can make.");
			System.out.println("\"minimum bet\" is currently $" + minBet);
			if (Tools.Console.askBoolean("Would you like to change it?", true)) {
				minBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= 0.01,
						"Bet must be at least 1 penny.");
				System.out.println("Changed \"minumum bet\" to " + minBet);
			}
			break;
		case "maximum bet":
			System.out.println("Description: the maximum bet a player can make.");
			System.out.println("\"maximum bet\" is currently $" + maxBet);
			if (Tools.Console.askBoolean("Would you like to change it?", true)) {
				maxBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= minBet,
						"Bet must be at least the value of \"minumum bet\" ($" + minBet + ")");
				System.out.println("Changed \"maximum bet\" to " + maxBet);
			}
			break;
		case "minumum AI bet":
			System.out.println("Description: the minimum bet an AI can make.");
			System.out.println("\"minimum AI bet\" is currently $" + minAIBet);
			if (Tools.Console.askBoolean("Would you like to change it?", true)) {
				minAIBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= 0.01,
						"Bet must be at least 1 penny.");
				System.out.println("Changed \"minumum AI bet\" to $" + minAIBet);
			}
			break;
		case "maximum AI bet":
			System.out.println("Description: the maximum bet an AI can make.");
			System.out.println("\"maximum AI bet\" is currently $" + maxAIBet);
			if (Tools.Console.askBoolean("Would you like to change it?", true)) {
				maxAIBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= minAIBet,
						"Bet must be at least the value of \"minumum AI bet\" ($" + minAIBet + ")");
				System.out.println("Changed \"maximum AI bet\" to " + maxAIBet);
			}
			break;
		case "maximum hits":
			System.out.println("Description: the maximum amount of hits a player can make in a turn.");
			System.out.println("\"maximum hits\" is currently " + maxHits);
			if (Tools.Console.askBoolean("Would you like to change it?", true)) {
				maxAIBet = Tools.Console.askDouble("What would you like to change it to?", true, x -> x >= 1,
						"Must be at least 1.");
				System.out.println("Changed \"maximum hits\" to " + maxHits);
			}
			break;
		}
	}

	public static void main(String[] args) {
		BlackjackGame.printDescription();
		game = new BlackjackGame(new Deck()).setMaxHits(maxHits);
		@SuppressWarnings("serial")
		ArrayList<String> choices = new ArrayList<String>() {
			{
				add("play");
				add("player setup");
				add("properties");
				add("quit");
			}
		};

		loop: while (true) {
			game.setMaxHits(maxHits);
			switch (Tools.Console.askSelection("Commands", choices, true, "What would you like to do?", null, true)
					.toLowerCase()) {
			case "play":
				game.start();
				break;
			case "quit":
				break loop;
			case "properties":
				properties();
				break;
			}
		}

	}

}
