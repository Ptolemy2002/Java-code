package main;

import java.util.ArrayList;

import cards.Card;
import cards.CardPlayer;
import cards.Deck;
import cards.EnumCardNumber;
import cards.EnumCardSuit;
import cards.blackjack.BlackjackGame;

@SuppressWarnings("serial")
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
					"CANCEL", true, true, true);
			EnumCardSuit type = Tools.Console.askSelection("Card Types", EnumCardSuit.getValues(), true, "CANCEL", true,
					true, true);

			if (!(number == null || type == null)) {
				System.out.println(new Card(number, type).toString());
			}
		}
	}

	public static void properties() {
		ArrayList<String> properties = new ArrayList<String>() {
			{
				add("minimum bet");
				add("maximum bet");
				add("minimum AI bet");
				add("maximum AI bet");
				add("maximum hits");
			}
		};

		String choice = Tools.Console.askSelection("Properties", properties, true, "Pick a property to edit", "CANCEL",
				true, true, true);
		if (choice != null) {
			switch (choice) {
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
			case "minimum AI bet":
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
	}
	
	public static void betSetup() {
		ArrayList<CardPlayer> players = game.getPlayers();
		if (players.isEmpty()) {
			System.out.println("There are no players registered.");
		} else {
			CardPlayer player = Tools.Console.askSelection("Players", players, true, "Choose a player.", "CANCEL", true, true, true);
			if (player != null) {
				player.setBet(Tools.Console.askDouble(player.toString() + "'s bet is $" + player.getBet() + ". What would you like to change it to?", true));
				System.out.println("Changed bet!");
			}
		}
	}

	public static void playerSetup() {
		ArrayList<CardPlayer> players = game.getPlayers();
		if (players.isEmpty()) {
			System.out.println("There are no players registered.");
		} else {
			if (Tools.Console.askBoolean(
					"There are " + players.size() + " players registered. Would you like to view the players?", true)) {
				Tools.Console.printList(players, true);
			}
		}

		ArrayList<String> choices = new ArrayList<String>() {
			{
				add("add");
			}
		};
		if (!players.isEmpty()) {
			choices.add("remove");
			choices.add("edit");
		}

		String choice = Tools.Console.askSelection("Choices", choices, true,
				"Choose an action (or the index of that action)", "CANCEL", true, true, true);
		if (choice != null) {
			switch (choice) {
			case "add":
				CardPlayer player = game
						.addNewPlayer(Tools.Console.askBoolean("Would you like your player to be an AI?", true));
				if (Tools.Console.askBoolean(
						"Your player's name is \"" + player.getName() + "\". Would you like to change it?", true)) {
					player.setName(Tools.Console.ask("What is the new name?"));
				}
				if (Tools.Console.askBoolean("Your player has $" + player.getMoney() + ". Would you like to change it?",
						true)) {
					if (player.isAI()) {
						player.setMoney(Tools.Console.askDouble("What is the new money amount?", true, x -> x >= minAIBet,
								"The minimum AI bet is $" + minAIBet + " (you can change it in properties)."));
					} else {
						player.setMoney(Tools.Console.askDouble("What is the new money amount?", true, x -> x >= minBet,
								"The minimum bet is $" + minBet + " (you can change it in properties)."));
					}
				}
				break;
			case "remove":
				players.remove(Tools.Console.askSelection("Players", players, true, "Pick a player", "CANCEL", true, true, true));
				break;
			case "edit":
				CardPlayer player1 = Tools.Console.askSelection("Players", players, true, "Pick a player", "CANCEL", true, true, true);
				if (Tools.Console.askBoolean(
						"Your player's name is \"" + player1.getName() + "\". Would you like to change it?", true)) {
					player1.setName(Tools.Console.ask("What is the new name?"));
				}
				if (Tools.Console.askBoolean("Your player has $" + player1.getMoney() + ". Would you like to change it?",
						true)) {
					if (player1.isAI()) {
						player1.setMoney(Tools.Console.askDouble("What is the new money amount?", true, x -> x >= minAIBet,
								"The minimum AI bet is $" + minAIBet + " (you can change it in properties)."));
					} else {
						player1.setMoney(Tools.Console.askDouble("What is the new money amount?", true, x -> x >= minBet,
								"The minimum bet is $" + minBet + " (you can change it in properties)."));
					}
					
				}
				break;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Welcome to Blackjack!");
		if (Tools.Console.askBoolean("Would you like to hear the rules?", true))
			BlackjackGame.printDescription();
		System.out.println("Okay! Let's go!");
		System.out.println("");

		game = new BlackjackGame(new Deck()).setMaxHits(maxHits);
		ArrayList<String> choices = new ArrayList<String>() {
			{
				add("play");
				add("player setup");
				add("properties");
				add("quit");
				add("help");
				add("rules");
				add("bet setup");
			}
		};

		loop: while (true) {
			game.setMaxHits(maxHits);
			String choice = Tools.Console.askSelection("Command Choices", choices, true,
					"What would you like to do (\"help\" for choices)?", null, true, false, false).toLowerCase();
			System.out.println("");
			switch (choice) {
			case "play":
				game.start();
				break;
			case "quit":
				System.out.println("Goodbye.");
				break loop;
			case "properties":
				properties();
				break;
			case "help":
				Tools.Console.printList("Command Choices", choices, false);
				break;
			case "rules":
				BlackjackGame.printDescription();
				break;
			case "player setup":
				playerSetup();
				break;
			case "bet setup":
				ArrayList<String> choices1 = new ArrayList<String>() {
					{
						add("automatic");
						add("manual");
					}
				};
				if (Tools.Console.askSelection("Choices", choices1, true, "Would you like to use automatic or manual mode?", "CANCEL", true, false, false).equalsIgnoreCase("automatic")) {
					game.makeBets(minBet, maxBet, minAIBet, maxAIBet);
				} else {
					betSetup();
				}
				break;
			}
			System.out.println("");
		}
	}

}
